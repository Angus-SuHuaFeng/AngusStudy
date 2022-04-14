package com.angus.day05;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashSet;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/11 22:14
 * @description：
 *
 *      aggregate: 增量统计
 *      ProcessWindowFunction: 全窗口，只在最后窗口关闭的时候统计结果
 *
 *      那如果我们希望在每隔20分钟统计一次UV， 最终每天24点统计总量
 *      .aggregate(new UVAggregateFunction(), new UVProcessWindowFunction());
 *      aggregate的另一种用法来了，传入一个AggregateFunction和一个ProcessWindowFunction
 *      AggregateFunction负责增量统计，每隔
 */
public class UVExample {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SingleOutputStreamOperator<Event> streamOperator = env.addSource(new ClickSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));
        streamOperator.print("data");

        SingleOutputStreamOperator<String> outputStreamOperator = streamOperator.keyBy(data -> true)
                .window(SlidingEventTimeWindows.of(Time.hours(24), Time.seconds(2)))
                .aggregate(new UVAggregateFunction(), new UVProcessWindowFunction());

        outputStreamOperator.print("uv");

        env.execute();

    }

    private static class UVAggregateFunction implements AggregateFunction<Event, HashSet<String>, Long>, com.angus.day05.UVAggregateFunction {

        @Override
        public HashSet<String> createAccumulator() {
            return new HashSet<>();
        }

        @Override
        public HashSet<String> add(Event value, HashSet<String> accumulator) {
            accumulator.add(value.user);
            return accumulator;
        }

        @Override
        public Long getResult(HashSet<String> accumulator) {
            return (long) accumulator.size();
        }

        @Override
        public HashSet<String> merge(HashSet<String> a, HashSet<String> b) {
            a.addAll(b);
            return a;
        }

    }

    private static class UVProcessWindowFunction extends ProcessWindowFunction<Long, String , Boolean, TimeWindow> {
        @Override
        public void process(Boolean aBoolean, ProcessWindowFunction<Long, String, Boolean, TimeWindow>.Context context, Iterable<Long> elements, Collector<String> out) throws Exception {
            Long cnt = 0L;
            for (Long aLong : elements) {
                cnt+=aLong;
            }
            out.collect("时间: "+ new Timestamp(context.window().getStart()) + "-->" + new Timestamp(context.window().getEnd()) + "当前的实时UV为: "+ cnt);
        }
    }
}
