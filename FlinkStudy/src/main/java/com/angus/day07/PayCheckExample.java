package com.angus.day07;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/15 16:21
 * @description：
 */
public class PayCheckExample {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // TODO 来自App的支付信息
        SingleOutputStreamOperator<Tuple3<String, String, Long>> appStream = env.fromElements(
                Tuple3.of("order-1", "app", 1000L),
                Tuple3.of("order-2", "app", 2000L),
                Tuple3.of("order-3", "app", 5000L)
        ).assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple3<String, String, Long>>forBoundedOutOfOrderness(Duration.ZERO)
                .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, String, Long>>() {
                    @Override
                    public long extractTimestamp(Tuple3<String, String, Long> element, long recordTimestamp) {
                        return element.f2;
                    }
                }));

        // TODO 来自第三方支付平台的支付信息
        SingleOutputStreamOperator<Tuple4<String , String , String ,Long>> payStream = env.fromElements(
                Tuple4.of( "order-1", "pay" ,"success",3000L),
                Tuple4.of( "order-3", "pay" ,"success",4000L)
        ).assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple4<String , String , String ,Long>>forBoundedOutOfOrderness(Duration.ZERO)
                .withTimestampAssigner(new SerializableTimestampAssigner<Tuple4<String, String, String, Long>>() {
                    @Override
                    public long extractTimestamp(Tuple4<String, String, String, Long> element, long recordTimestamp) {
                        return element.f3;
                    }
                }));

        // TODO 连接
        appStream.connect(payStream)
                .keyBy(data->data.f0, data->data.f0)
                .process(new OrderMathResult())
                .print();

        env.execute();
    }

    private static class OrderMathResult extends CoProcessFunction<Tuple3<String ,String ,Long>, Tuple4<String ,String ,String ,Long>, String> {

        // TODO 定义状态变量保存俩条流已经到达的事件, （ 从状态中获取另一条流来没来 ）
        private ValueState<Tuple3<String ,String ,Long>> appEventState;
        private ValueState<Tuple4<String ,String ,String ,Long>> payEventState;

        @Override
        public void open(Configuration parameters) throws Exception {
            // TODO 状态获取必须在运行时上下文环境中获取，所以需要在open生命周期里获取
            appEventState = getRuntimeContext().getState(
                    new ValueStateDescriptor<Tuple3<String, String, Long>>("app-event", Types.TUPLE(Types.STRING, Types.STRING, Types.LONG))
            );

            payEventState = getRuntimeContext().getState(
                    new ValueStateDescriptor<Tuple4<String, String, String, Long>>("pay-event", Types.TUPLE(Types.STRING, Types.STRING,Types.STRING,Types.LONG))
            );
        }

        @Override
        public void processElement1(Tuple3<String, String, Long> value, CoProcessFunction<Tuple3<String, String, Long>, Tuple4<String, String, String, Long>, String>.Context ctx, Collector<String> out) throws Exception {
            // TODO 检查第三方支付平台是否有app对应的流数据
            if (payEventState.value()!=null){
                out.collect("对账成功: " + value + " " + payEventState.value());
                // TODO 清空状态
                payEventState.clear();
            }else {
                // TODO 更新状态
                appEventState.update(value);
                // TODO 注册一个5秒的定时器，开始等待另一个流的数据
                ctx.timerService().registerEventTimeTimer(value.f2 + 5000L);
            }
        }

        @Override
        public void processElement2(Tuple4<String, String, String, Long> value, CoProcessFunction<Tuple3<String, String, Long>, Tuple4<String, String, String, Long>, String>.Context ctx, Collector<String> out) throws Exception {
            if (appEventState.value()!=null){
                out.collect("对账成功: " + appEventState.value() + " " + value);
                // TODO 清空状态
                appEventState.clear();
            }else {
                // TODO 更新状态
                payEventState.update(value);
                // TODO 注册一个5秒的定时器，开始等待另一条流的事件
                ctx.timerService().registerEventTimeTimer(value.f3);
            }
        }

        @Override
        public void onTimer(long timestamp, CoProcessFunction<Tuple3<String, String, Long>, Tuple4<String, String, String, Long>, String>.OnTimerContext ctx, Collector<String> out) throws Exception {
            // TODO 定时器触发，判断状态，如果某个状态不为空，说明另一条流中的事件没来
            if (appEventState.value()!=null){
                out.collect("对账失败: " + appEventState.value() + " " + "第三方支付信息没来");
            }
            if (payEventState.value()!=null){
                out.collect("对帐失败: " + payEventState.value() + " " + "app端支付信息没来");
            }
            appEventState.clear();
            payEventState.clear();
        }
    }
}
