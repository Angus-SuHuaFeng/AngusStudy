package com.angus.day03;

import com.angus.day02.Event;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 13:27
 * @description：
 */
public class TransformSimpleAggTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // TODO 测试数据
        DataStreamSource<Event> fromElements = env.fromElements(
                new Event("Bob1", "./home1", 1000L),
                new Event("Bob2", "./home2", 2000L),
                new Event("Bob3", "./home3", 3000L),
                new Event("Bob4", "./home4", 4000L),
                new Event("Bob5", "./home5", 5000L),
                new Event("Bob5", "./home51", 6000L)
        );

        // TODO 1.keyBy,自定义类
        KeyedStream<Event, String> keyedStream = fromElements.keyBy(new MyKeySelector());

        // TODO 2.匿名内部类
        KeyedStream<Event, String> keyedStream1 = fromElements.keyBy(new KeySelector<Event, String>() {
            @Override
            public String getKey(Event event) throws Exception {
                return event.user;
            }
        });

        // TODO 3.使用lambda表达式
        KeyedStream<Event, String> keyedStream2 = fromElements.keyBy(event -> event.user);

        // TODO 聚合算子测试

        /**
         *  max和maxBy的区别：
         *      max只是对应字段的最大值，maxBy会将整条数据拿到
         *      max: > Event{user='Bob5', url='./home5', timestamp=1970-01-01 08:00:06.0}
         *      maxBy: > Event{user='Bob5', url='./home51', timestamp=1970-01-01 08:00:06.0}
         */
        keyedStream2.max("timestamp").print("max: ");

        keyedStream2.maxBy("timestamp", false).print("maxBy: ");

        env.execute();
    }

    private static class MyKeySelector implements KeySelector<Event, String>{
        @Override
        public String getKey(Event event) throws Exception {
            return event.user;
        }
    }
}
