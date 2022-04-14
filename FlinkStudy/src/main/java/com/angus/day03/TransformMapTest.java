package com.angus.day03;

import com.angus.day02.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 13:14
 * @description：
 */
public class TransformMapTest {
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
        // TODO 1.使用自定义类
        SingleOutputStreamOperator<String> map = fromElements.map(new MyMapper());

        // TODO 2.也可以使用匿名类实现MapFunction接口
        fromElements.map(new MapFunction<Event, String>() {
            @Override
            public String map(Event event) throws Exception {
                return event.user;
            }
        });

        //TODO 3.使用lambda表达式(存在泛型擦除)
        SingleOutputStreamOperator<String> map1 = fromElements.map(data -> data.user);

        map.print();
        env.execute();
    }

    // TODO 使用自定义类实现MapFunction接口
    public static class MyMapper implements MapFunction<Event, String> {
        @Override
        public String map(Event event) throws Exception {
            return event.user;
        }
    }
}
