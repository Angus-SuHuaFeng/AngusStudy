package com.angus.day05;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;
import java.util.HashSet;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/10 20:57
 * @description：   电商场景PV,UV统计,计算PV/UV
 */
public class WindowAggregateTest_UV {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);
        SingleOutputStreamOperator<Event> streamOperator = env.addSource(new ClickSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));
        streamOperator.print("data");

        KeyedStream<Event, String> keyedStream = streamOperator.keyBy(data -> data.user);

        streamOperator.windowAll(SlidingEventTimeWindows.of(Time.seconds(10) , Time.seconds(2)))
                        .aggregate(new PvUvAvg()).print("PVAndUV");

        env.execute();

    }
    // TODO 自定义一个AggregateFunction, 用Long保存pv，HashSet保存uv
    public static class PvUvAvg implements AggregateFunction<Event, Tuple2<Long, HashSet<String>>, String > {

        @Override
        public Tuple2<Long, HashSet<String>> createAccumulator() {
            return Tuple2.of(0L, new HashSet<>());
        }

        @Override
        public Tuple2<Long, HashSet<String>> add(Event value, Tuple2<Long, HashSet<String>> accumulator) {
            // 每来一条数据，pv+1， uv放入hashSet中
            accumulator.f1.add(value.user);
            return Tuple2.of(accumulator.f0 + 1, accumulator.f1);
        }

        @Override
        public String getResult(Tuple2<Long, HashSet<String>> accumulator) {
            // 窗口触发时，将PV， UV，PV/UV 计算出来
            long res = accumulator.f0 / accumulator.f1.size();
            return "PV:"+accumulator.f0 + " UV:" + accumulator.f1.size() + " PV/UV:" + String.format("%.2f", (double)res);
        }

        @Override
        public Tuple2<Long, HashSet<String>> merge(Tuple2<Long, HashSet<String>> a, Tuple2<Long, HashSet<String>> b) {
            a.f1.addAll(b.f1);
            return Tuple2.of(a.f0 + b.f0, a.f1);
        }
    }
}
