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
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/13 9:43
 * @description：
 */
public class ProcessTimeTimerTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);


        DataStreamSource<Event> streamSource = env.addSource(new ClickSource());

        KeyedStream<Event, String> keyedStream = streamSource.keyBy(data -> data.user);

        keyedStream.process(new KeyedProcessFunction<String, Event, String>() {
            @Override
            public void processElement(Event value, KeyedProcessFunction<String, Event, String>.Context ctx, Collector<String> out) throws Exception {
                long currentProcessingTime = ctx.timerService().currentProcessingTime();
                out.collect(ctx.getCurrentKey() + "数据到达（处理）时间: " + new Timestamp(currentProcessingTime));

                // 注册一个10秒后的定时器
                ctx.timerService().registerProcessingTimeTimer(currentProcessingTime + 10 * 1000L);
            }

            @Override
            public void onTimer(long timestamp, KeyedProcessFunction<String, Event, String>.OnTimerContext ctx, Collector<String> out) throws Exception {
                out.collect(ctx.getCurrentKey() + "定时器触发，触发时间: " + new Timestamp(timestamp));
            }
        }).print();

        env.execute();
    }
}
