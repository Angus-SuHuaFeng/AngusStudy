package com.angus.day05;

import com.angus.day02.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/12 0:55
 * @description：
 */
public class LateDataTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SingleOutputStreamOperator<Event> stream = env.socketTextStream("bigdata2", 7777)
                .map(new MapFunction<String, Event>() {
                    @Override
                    public Event map(String value) throws Exception {
                        String[] split = value.split(",");
                        return new Event(split[0].trim(), split[1].trim(), Long.valueOf(split[2].trim()));
                    }
                })
                .assignTimestampsAndWatermarks(WatermarkStrategy
                        .<Event>forBoundedOutOfOrderness(Duration.ofSeconds(2))
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));

       stream.print("data");

       // 侧输出流
        OutputTag<Event> late = new OutputTag<Event>("late"){};

        SingleOutputStreamOperator<URLPOJO> result = stream.keyBy(data -> data.url)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .allowedLateness(Time.minutes(1))
                .sideOutputLateData(late)
                .aggregate(new URLAggregateFunction(), new URLProcessWindowFunction());

        result.print("result");
        result.getSideOutput(late).print("late");

        env.execute();
    }

    private static class URLAggregateFunction implements AggregateFunction<Event, Long, Long>, com.angus.day05.UVAggregateFunction {

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
