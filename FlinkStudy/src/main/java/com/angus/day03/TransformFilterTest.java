package com.angus.day03;

import com.angus.day02.Event;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 13:27
 * @description：
 */
public class TransformFilterTest {
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
        SingleOutputStreamOperator<Event> filter = fromElements.filter(new MyFilter());

        // TODO 2.使用匿名内部类
        SingleOutputStreamOperator<Event> filter1 = fromElements.filter(new FilterFunction<Event>() {
            @Override
            public boolean filter(Event event) throws Exception {
                return event.user.equals("Bob1");
            }
        });

        // TODO 3.使用lambda表达式
        SingleOutputStreamOperator<Event> filter2 = fromElements.filter(data -> data.user.equals("Bob1"));

        filter.print();

        filter1.print();

        filter2.print();
        env.execute();
    }

    private static class MyFilter implements FilterFunction<Event> {
        @Override
        public boolean filter(Event event) throws Exception {
            return event.user.equals("Bob1");
        }
    }
}
