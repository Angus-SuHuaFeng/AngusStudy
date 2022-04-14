package com.angus.day05;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

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
public class URLExample {
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

        SingleOutputStreamOperator<URLPOJO> outputStreamOperator = streamOperator.keyBy(data -> data.url)
                .window(TumblingEventTimeWindows.of(Time.seconds(2)))
                .aggregate(new URLAggregateFunction(), new URLProcessWindowFunction());

        outputStreamOperator.print("URL");

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
