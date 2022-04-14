package com.angus.day02;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/6 22:49
 * @description：
 */
public class UDFSource {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        DataStreamSource<Event> clickSource = env.addSource(new ClickSource());

        DataStreamSource<Event> ClickParallelSource = env.addSource(new ClickParallelSource()).setParallelism(2);

        ClickParallelSource.print();

        env.execute();
    }
}
