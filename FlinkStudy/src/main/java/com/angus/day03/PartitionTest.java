package com.angus.day03;

import com.angus.day02.Event;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 18:05
 * @description：
 */
public class PartitionTest {
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
                new Event("Bob5", "./home51", 6000L),
                new Event("Bob5", "./home5", 5000L),
                new Event("Bob5", "./home51", 6000L),
                new Event("Bob5", "./home5", 5000L),
                new Event("Bob5", "./home51", 6000L)
        );
        // TODO 1.随机均匀分区
//        eventStream.shuffle().print().setParallelism(4);
        // TODO 2.轮询分区
//        eventStream.rebalance().print().setParallelism(5);
        // TODO 3.重缩放分区
//        eventStream.rescale().print();
        // TODO 4.广播
        eventStream.broadcast().print();
        // TODO 5.全局分区(会将所有的分区发送到第一个并行子任务)
        eventStream.global().print();
        // TODO 6.自定义分区器  : partitionCustom()  俩个参数， 1.Partitioner   2.KeySelector
        DataStream<Integer> integerDataStream = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .partitionCustom(new Partitioner<Integer>() {
            @Override
            public int partition(Integer key, int numPartitions) {
                return key % 100;
            }
        }, new KeySelector<Integer, Integer>() {
            @Override
            public Integer getKey(Integer value) throws Exception {
                return value.hashCode();
            }
        });



        DataStreamSource<Integer> streamSource = env.addSource(new RichParallelSourceFunction<Integer>() {
            @Override
            public void run(SourceContext<Integer> ctx) throws Exception {
                for (int i = 1; i <= 8; i++) {
                    if (i % 2 == getRuntimeContext().getIndexOfThisSubtask()) ctx.collect(i);
                }
            }

            @Override
            public void cancel() {

            }
        }).setParallelism(2);

        streamSource.rescale().print().setParallelism(4);

        env.execute();
    }
}
