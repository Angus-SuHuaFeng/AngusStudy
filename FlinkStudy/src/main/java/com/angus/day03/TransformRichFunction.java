package com.angus.day03;

import com.angus.day02.Event;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 17:30
 * @description：  RichMapFunction ： 可以重写open和close方法，存在生命周期，例如在open里进行连接，close里关闭
 *                                   在调用map方法前，会先调用open方法
 */
public class TransformRichFunction {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);

        // TODO 测试数据
        DataStreamSource<Event> eventStream = env.fromElements(
                new Event("Bob1", "./home1", 1000L),
                new Event("Bob2", "./home2", 2000L),
                new Event("Bob3", "./home3", 3000L),
                new Event("Bob4", "./home4", 4000L),
                new Event("Bob5", "./home5", 5000L),
                new Event("Bob5", "./home51", 6000L)
        );

        SingleOutputStreamOperator<String> streamOperator = eventStream.map(new MyRichMap());

        // TODO 输出
        streamOperator.print();

        env.execute();
    }

    private static class MyRichMap extends RichMapFunction<Event, String> {
        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            System.out.println("Open被调用: " + getRuntimeContext().getIndexOfThisSubtask() + "号任务开始");
        }

        @Override
        public String  map(Event value) throws Exception {
            return value.user;
        }

        @Override
        public void close() throws Exception {
            super.close();
            System.out.println("close被调用: " + getRuntimeContext().getIndexOfThisSubtask() + "号任务结束");
        }
    }
}
