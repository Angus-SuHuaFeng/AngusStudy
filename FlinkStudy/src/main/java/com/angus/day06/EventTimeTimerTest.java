package com.angus.day06;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;

import java.sql.Timestamp;
import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/13 9:43
 * @description：
 */
public class EventTimeTimerTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);


        SingleOutputStreamOperator<Event> streamOperator = env.addSource(new CustomSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));

        KeyedStream<Event, String> keyedStream = streamOperator.keyBy(data -> data.user);

        keyedStream.process(new KeyedProcessFunction<String, Event, String>() {
            @Override
            public void processElement(Event value, KeyedProcessFunction<String, Event, String>.Context ctx, Collector<String> out) throws Exception {
                long currentProcessingTime = ctx.timestamp();
                out.collect(ctx.getCurrentKey() + "数据到达时间戳: " + new Timestamp(currentProcessingTime) + " watermark: " + new Timestamp(ctx.timerService().currentWatermark()));

                // 注册一个10秒后的定时器
                ctx.timerService().registerEventTimeTimer(currentProcessingTime + 10 * 1000L);
            }

            @Override
            public void onTimer(long timestamp, KeyedProcessFunction<String, Event, String>.OnTimerContext ctx, Collector<String> out) throws Exception {
                out.collect(ctx.getCurrentKey() + "定时器触发，触发时间: " + new Timestamp(timestamp) + " watermark： " + new Timestamp(ctx.timerService().currentWatermark()));
            }
        }).print();

        env.execute();
    }

    // 自定义测试数据源
    public static class CustomSource implements SourceFunction<Event> {

        @Override
        public void run(SourceContext<Event> ctx) throws Exception {
            ctx.collect(new Event("Mary","./home", 1000L));
            Thread.sleep(2000L);
            ctx.collect(new Event("Bob","./home", 11000L));
            Thread.sleep(2000L);
            ctx.collect(new Event("Angus","./home", 11001L));
            Thread.sleep(2000L);
            ctx.collect(new Event("Lola","./home", 11002L));
        }

        @Override
        public void cancel() {

        }
    }
}
