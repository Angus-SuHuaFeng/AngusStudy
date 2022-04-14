package com.angus.day02;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/6 22:08
 * @description：
 *
 *         socketText 从socket文本流中读取流式数据
 *
 */
public class SourceTest2 {
    public static void main(String[] args) throws Exception {
        // TODO 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // TODO 多种数据源的读取

        // 1. 从socket文本流中读取流式数据
        DataStreamSource<String> socketText = env.socketTextStream("hadoop1",7777);

        // TODO 输出
        socketText.print("textFile");

        // TODO 执行
        env.execute();

    }
}
