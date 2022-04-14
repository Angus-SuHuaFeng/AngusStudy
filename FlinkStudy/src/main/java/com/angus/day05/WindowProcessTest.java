package com.angus.day05;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashSet;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/11 21:49
 * @description：
 */
public class WindowProcessTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SingleOutputStreamOperator<Event> streamOperator = env.addSource(new ClickSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ofSeconds(2))
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));
        // 使用ProcessWindowFunction计算UV  (全窗口函数， 能拿到所有的窗口信息，上下文)
        SingleOutputStreamOperator<String> process = streamOperator.keyBy(data -> true)
                .window(TumblingEventTimeWindows.of(Time.hours(24), Time.seconds(10)))
                .process(new MyProcessWindowFunction());

        process.print();

        env.execute();
    }
    // TODO 实现自定义ProcessWindowFunction，输出统计信息
    private static class MyProcessWindowFunction extends ProcessWindowFunction<Event, String, Boolean, TimeWindow> {

        @Override
        public void process(Boolean aBoolean, ProcessWindowFunction<Event, String, Boolean, TimeWindow>.Context context, Iterable<Event> elements, Collector<String> out) throws Exception {
            // 用一个HashSet保存user
            HashSet<String> set = new HashSet<>();
            // 从element中遍历数据，放到set中去重
            for (Event event : elements) {
                set.add(event.user);
           }
            // 结合窗口信息输出
            out.collect("时间: "+ new Timestamp(context.window().getStart()) + "-->" + new Timestamp(context.window().getEnd()) + "当前的实时UV为: "+set.size());
        }
    }
}
