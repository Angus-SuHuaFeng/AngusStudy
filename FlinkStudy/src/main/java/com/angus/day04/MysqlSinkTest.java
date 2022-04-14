package com.angus.day04;

import com.angus.day02.Event;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 21:36
 * @description：
 */
public class MysqlSinkTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // TODO 测试数据
        DataStreamSource<Event> eventStream = env.fromElements(
                new Event("Bob1", "./home1", 1000L),
                new Event("Bob2", "./home2", 2000L),
                new Event("Bob3", "./home3", 3000L),
                new Event("Bob4", "./home4", 4000L),
                new Event("Bob5", "./home5", 5000L),
                new Event("Bob5", "./home51", 6000L)
        );

        eventStream.addSink(JdbcSink.sink(
                "insert into clicks (user, url) values(?,?)",
                ((preparedStatement, event) -> {
                    preparedStatement.setString(1, event.user);
                    preparedStatement.setString(2, event.url);
                }),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl("jdbc:mysql://localhost:3306/test")
                        .withDriverName("com.mysql.jdbc.Driver")
                        .withUsername("root")
                        .withPassword("root")
                        .build()
        ));

        env.execute();
    }
}
