package com.angus.day07;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import com.angus.day02.Order;
import com.angus.day02.OrderSource;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/15 22:53
 * @description：
 */
public class CoGroupTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        SingleOutputStreamOperator<Order> orderStream = env.addSource(new OrderSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Order>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Order>() {
                            @Override
                            public long extractTimestamp(Order element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));

        SingleOutputStreamOperator<Event> clickStream = env.addSource(new ClickSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));

        orderStream.coGroup(clickStream)
                        .where(data->data.user)
                                .equalTo(data-> data.user)
                                        .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                                                .apply(new CoGroupFunction<Order, Event, String>() {
                                                    @Override
                                                    public void coGroup(Iterable<Order> first, Iterable<Event> second, Collector<String> out) throws Exception {
                                                        out.collect(first + "=>" + second );
                                                    }
                                                }).print();

        env.execute();
    }
}
