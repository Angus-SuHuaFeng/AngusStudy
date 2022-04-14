package com.angus.day04;

import com.angus.day02.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 20:11
 * @description：
 */
public class KafkaSinkTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // TODO 1.从Kafka中读取数据
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop1:9092");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        DataStreamSource<String> clicksStream = env.addSource(new FlinkKafkaConsumer<String>("clicks", new SimpleStringSchema(), prop));

        // TODO 2.使用Flink处理数据
        SingleOutputStreamOperator<String> streamOperator = clicksStream.map(new MapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                String[] split = value.split(",");
                // trim()去除空格
                return new Event(split[0].trim(), split[1].trim(), Long.valueOf(split[2].trim())).toString();
            }
        });

        // TODO 3.写入kafka中
        streamOperator.addSink(new FlinkKafkaProducer<String>("hadoop1:9092", "events", new SimpleStringSchema()));

        env.execute();
    }
}
