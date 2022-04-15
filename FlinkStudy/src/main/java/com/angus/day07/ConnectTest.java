package com.angus.day07;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/15 15:55
 * @description：
 *
 *
 *      connect: 连接的俩条流类型可以不同，更一般化
 *               缺点：太底层，都需要自己定义
 *                    只能俩个流连接
 */
public class ConnectTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<Integer> stream1 = env.fromElements(1, 2, 3, 4, 5);
        DataStreamSource<Long> stream2 = env.fromElements(1L, 2L, 3L, 4L, 5L);

        stream1.connect(stream2)
                        .map(new CoMapFunction<Integer, Long, String>() {
                            @Override
                            public String map1(Integer value) throws Exception {
                                return "Stream1 "+value.toString();
                            }

                            @Override
                            public String map2(Long value) throws Exception {
                                return "Stream2 "+value.toString();
                            }
                        }).print();


        env.execute();
    }
}
