package com.angus.day02;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/6 22:08
 * @description：
 *
 *         readTextFile     从文件中读取数据
 *         fromCollection   从集合中读取数据
 *         fromElements     从元素中读取数据
 *
 */
public class SourceTest1 {
    public static void main(String[] args) throws Exception {
        // TODO 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // TODO 多种数据源的读取

        // 1. 从文件中读取数据
        DataStreamSource<String> textFile = env.readTextFile("FlinkStudy/input/log.txt");

        // 2. 从集合中读取数据
        ArrayList<Event> list = new ArrayList<>();
        list.add(new Event("Bob", "./home", 1000L));
        DataStreamSource<Event> fromCollection = env.fromCollection(list);

        // 3. 从元素中读取数据
        DataStreamSource<Event> fromElements = env.fromElements(
                new Event("Bob", "./home", 1000L)
        );

        // TODO 输出
        textFile.print("textFile");
        fromCollection.print("fromCollection");
        fromElements.print("fromElements");

        // TODO 执行
        env.execute();

    }
}
