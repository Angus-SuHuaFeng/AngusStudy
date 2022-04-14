package com.angus.day04;

import com.angus.day02.Event;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkBase;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/9 21:02
 * @description：
 */
public class ElasticSearchSinkTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // TODO 1.测试数据
        DataStreamSource<Event> eventStream = env.fromElements(
                new Event("Bob1", "./home1", 1000L),
                new Event("Bob2", "./home2", 2000L),
                new Event("Bob3", "./home3", 3000L),
                new Event("Bob4", "./home4", 4000L),
                new Event("Bob5", "./home5", 5000L),
                new Event("Bob5", "./home51", 6000L)
        );

        // TODO 2.HttpHost
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("bigdata1",9200));
        // TODO 3.定义elasticsearchSinkFunction
        ElasticsearchSinkFunction<Event> sinkFunction = new ElasticsearchSinkFunction<Event>() {
            @Override
            public void process(Event element, RuntimeContext ctx, RequestIndexer indexer) {
                HashMap<String, String> map = new HashMap<>();
                map.put(element.user, element.url);

                IndexRequest request = Requests.indexRequest()
                        .index("clicks")
                        .type("type")
                        .source(map);
                indexer.add(request);
            }
        };
        eventStream.addSink(new ElasticsearchSink.Builder<>(httpHosts, sinkFunction).build());

        env.execute();
    }
}
