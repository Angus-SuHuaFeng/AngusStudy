package com.angus.day03;

import com.angus.day02.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 16:25
 * @description：
 */
public class TransformReduceTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);

        // TODO 测试数据
        DataStreamSource<Event> eventStream = env.fromElements(
                new Event("Bob", "./home", 3000L),
                new Event("Andy", "./home", 1040L),
                new Event("Alice", "./home", 1200L),
                new Event("Angus", "./care", 2000L),
                new Event("Angus", "./prod?id=100", 1000L),
                new Event("Andy", "./exit", 1040L)
        );

        // TODO 1.统计每个用户的访问频数
        SingleOutputStreamOperator<Tuple2<String, Long>> clicksByUser = eventStream.map(new MapFunction<Event, Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> map(Event value) throws Exception {
                        return Tuple2.of(value.user, 1L);
                    }
                }).keyBy(data -> data.f0)
                .reduce(new ReduceFunction<Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> reduce(Tuple2<String, Long> value1, Tuple2<String, Long> value2) throws Exception {
                        return Tuple2.of(value1.f0, value1.f1 + value2.f1);
                    }
                });
        clicksByUser.print("用户实时访问");
        // TODO 2.选出来当前最活跃的用户
        clicksByUser.keyBy(data -> data.f0).maxBy(1,false).print("实时最活跃用户");

        env.execute();
    }
}
