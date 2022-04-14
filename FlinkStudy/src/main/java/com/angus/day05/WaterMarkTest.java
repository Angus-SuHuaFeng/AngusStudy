package com.angus.day05;

import com.angus.day02.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/10 16:28
 * @description：
 */
public class WaterMarkTest {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // TODO 1.测试数据
        env.fromElements(
                new Event("Bob1", "./home1", 1000L),
                new Event("Bob2", "./home2", 2000L),
                new Event("Bob3", "./home3", 3000L),
                new Event("Bob4", "./home4", 4000L),
                new Event("Bob5", "./home5", 5000L),
                new Event("Bob5", "./home51", 6000L))
          // TODO 有序流的wartermark生成(一般测试使用)
//        .assignTimestampsAndWatermarks(WatermarkStrategy
//                .<Event>forMonotonousTimestamps()
//                .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
//                    @Override
//                    public long extractTimestamp(Event element, long recordTimestamp) {
//                        return element.timestamp;
//                    }
//                }))
                .assignTimestampsAndWatermarks(WatermarkStrategy
                        .<Event>forBoundedOutOfOrderness(Duration.ofSeconds(2))
                        // 时间戳的获取
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        })
                );

    }
}
