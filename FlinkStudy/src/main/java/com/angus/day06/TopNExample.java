package com.angus.day06;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import com.angus.day05.URLExample;
import com.angus.day05.URLPOJO;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/13 19:12
 * @description：
 */
public class TopNExample {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        SingleOutputStreamOperator<Event> streamOperator = env.addSource(new ClickSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));

        // TODO 1.按照URL分组
        SingleOutputStreamOperator<URLPOJO> urlCountStream = streamOperator.keyBy(data -> data.url)
                .window(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(5)))
                .aggregate(new URLAggregateFunction(), new URLProcessWindowFunction());

        urlCountStream.print("url");

        // TODO 2.对同一窗口统计出的访问量进行牌序



        env.execute();

    }

    public static class URLAggregateFunction implements AggregateFunction<Event, Long, Long>, com.angus.day05.UVAggregateFunction {

        @Override
        public Long createAccumulator() {
            return 0L;
        }

        @Override
        public Long add(Event value, Long accumulator) {
            return accumulator+1;
        }

        @Override
        public Long getResult(Long accumulator) {
            return accumulator;
        }

        @Override
        public Long merge(Long a, Long b) {
            return a+b;
        }


    }

    private static class URLProcessWindowFunction extends ProcessWindowFunction<Long, URLPOJO, String , TimeWindow> {
        @Override
        public void process(String url, ProcessWindowFunction<Long, URLPOJO, String, TimeWindow>.Context context, Iterable<Long> elements, Collector<URLPOJO> out) throws Exception {
            long start = context.window().getStart();
            long end = context.window().getEnd();
            long count = elements.iterator().next();
            out.collect(new URLPOJO(url, count, start, end));
        }
    }
}
