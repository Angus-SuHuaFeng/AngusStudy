package com.angus.day04;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 20:43
 * @description：
 */
public class RedisSinkTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // TODO 创建数据源（键值对）
        DataStreamSource<Event> clickStream = env.addSource(new ClickSource());

        FlinkJedisPoolConfig jedisPoolConfig = new FlinkJedisPoolConfig.Builder().setHost("bigdata1").build();

        // TODO 不处理，直接输出到Redis中
        clickStream.addSink(new RedisSink<>(jedisPoolConfig, new RedisMapper<Event>() {
            @Override
            public RedisCommandDescription getCommandDescription() {
                return new RedisCommandDescription(RedisCommand.HSET, "click");
            }

            @Override
            public String getKeyFromData(Event event) {
                return event.user;
            }

            @Override
            public String getValueFromData(Event event) {
                return event.url;
            }
        }));

        env.execute();
    }
}
