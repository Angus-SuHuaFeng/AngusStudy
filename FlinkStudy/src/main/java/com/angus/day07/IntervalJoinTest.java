package com.angus.day07;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import com.angus.day02.Order;
import com.angus.day02.OrderSource;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/15 22:19
 * @description：
 *
 *          通过间隔连接IntervalJoin用户下单前的点击操作，来推测可能的动作   -10秒 到 0 秒
 */
public class IntervalJoinTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);


        SingleOutputStreamOperator<Order> orderStream = env.addSource(new OrderSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Order>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Order>() {
                            @Override
                            public long extractTimestamp(Order element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));


        SingleOutputStreamOperator<Event> clickStream = env.addSource(new ClickSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));


        orderStream.keyBy(data->data.user)
                .intervalJoin(clickStream.keyBy(data-> data.user))
                .between(Time.seconds(-10), Time.seconds(0))
                .process(new ProcessJoinFunction<Order, Event, String>() {
                    @Override
                    public void processElement(Order left, Event right, ProcessJoinFunction<Order, Event, String>.Context ctx, Collector<String> out) throws Exception {
                        out.collect(right + "=>" + left);
                    }
                }).print();

        env.execute();
    }
}
