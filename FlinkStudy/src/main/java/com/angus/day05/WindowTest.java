package com.angus.day05;

import com.angus.day02.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.*;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.sql.Timestamp;
import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/10 18:54
 * @description：
 */
public class WindowTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // TODO 1.测试数据
        SingleOutputStreamOperator<Event> streamOperator = env.fromElements(
                        new Event("Bob1", "./home1", 1000L),
                        new Event("Bob2", "./home2", 2000L),
                        new Event("Bob3", "./home3", 3000L),
                        new Event("Bob4", "./home4", 4000L),
                        new Event("Bob5", "./home5", 5000L),
                        new Event("Bob5", "./home51", 6000L))
                .assignTimestampsAndWatermarks(WatermarkStrategy
                        .<Event>forBoundedOutOfOrderness(Duration.ofSeconds(2))
                        // 时间戳的获取
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        })
                );

        streamOperator.map(new MapFunction<Event, Tuple2<String , Long>>() {
                    @Override
                    public Tuple2<String, Long> map(Event value) throws Exception {
                        return Tuple2.of(value.user, 1L);
                    }
                })
                .keyBy(data -> data.f0)
                // TODO 各种窗口类型
//                // 事件时间会话窗口
//                .window(EventTimeSessionWindows.withGap(Time.seconds(2)))
//                // 处理时间会话窗口
//                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
//                // 滑动事件时间窗口 EventTime
//                .window(SlidingEventTimeWindows.of(Time.minutes(2), Time.seconds(30)))
//                // 滑动处理时间窗口  ProcessingTime
//                .window(SlidingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(5)))
//                // 滚动事件时间窗口
                .window(TumblingEventTimeWindows.of(Time.hours(1)))
//                // 滚动处理时间窗口
//                .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
//
//                // 计数窗口
//                .countWindow(10)
//                // 滑动计数窗口
//                .countWindow(10, 3)
                .reduce(new ReduceFunction<Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> reduce(Tuple2<String, Long> value1, Tuple2<String, Long> value2) throws Exception {
                        return Tuple2.of(value1.f0, value1.f1 + value2.f1);
                    }
                }).print();

        streamOperator.keyBy(data -> data.user)
                        .window(TumblingEventTimeWindows.of(Time.minutes(2)))
                                .aggregate(new AggregateFunction<Event, Tuple2<Long, Integer>, String >() {
                                    @Override
                                    public Tuple2<Long, Integer> createAccumulator() {
                                        return Tuple2.of(0L, 0);
                                    }

                                    @Override
                                    public Tuple2<Long, Integer> add(Event value, Tuple2<Long, Integer> accumulator) {
                                        return Tuple2.of(accumulator.f0 + value.timestamp, accumulator.f1 + 1);
                                    }

                                    @Override
                                    public String getResult(Tuple2<Long, Integer> accumulator) {
                                        Timestamp timestamp = new Timestamp(accumulator.f0 / accumulator.f1);
                                        return timestamp.toString();
                                    }

                                    @Override
                                    public Tuple2<Long, Integer> merge(Tuple2<Long, Integer> a, Tuple2<Long, Integer> b) {
                                        return Tuple2.of(a.f0, a.f1 + b.f1);
                                    }
                                }).print();


        env.execute();

    }
}
