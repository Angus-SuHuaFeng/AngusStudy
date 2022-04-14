package com.angus.day03;

import com.angus.day02.Event;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 13:27
 * @description：
 */
public class TransformFlatMapTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // TODO 测试数据
        DataStreamSource<Event> fromElements = env.fromElements(
                new Event("Bob1", "./home", 1000L),
                new Event("Bob2", "./home", 1000L),
                new Event("Bob3", "./home", 1000L),
                new Event("Bob4", "./home", 1000L),
                new Event("Bob5", "./home", 1000L)
        );

        // TODO 1.自定义类
        SingleOutputStreamOperator<String> flatMap = fromElements.flatMap(new MyFlatMap());

        // TODO 2.使用匿名内部类
        SingleOutputStreamOperator<String> flatMap1 = fromElements.flatMap(new FlatMapFunction<Event, String>() {
            @Override
            public void flatMap(Event event, Collector<String> collector) throws Exception {
                collector.collect(event.user);
                collector.collect(event.url);
                collector.collect(String.valueOf(event.timestamp));
            }
        });

        // TODO 3.使用lambda表达式
        SingleOutputStreamOperator<String> flatMap2 = fromElements.flatMap((Event event, Collector<String> out) -> {
            if (event.user.equals("Bob1")) {
                out.collect(event.user);
            } else {
                out.collect(event.user);
                out.collect(event.url);
                out.collect(String.valueOf(event.timestamp));
            }
        }).returns(new TypeHint<String>() {
        });

        flatMap.print();

        flatMap1.print();

        flatMap2.print();

        env.execute();
    }

    private static class MyFlatMap implements FlatMapFunction<Event, String> {
        @Override
        public void flatMap(Event event, Collector<String> collector) throws Exception {
            collector.collect(event.user);
            collector.collect(event.url);
            collector.collect(String.valueOf(event.timestamp));
        }
    }
}
