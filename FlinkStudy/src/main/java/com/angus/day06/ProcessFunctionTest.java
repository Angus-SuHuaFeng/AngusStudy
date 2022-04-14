package com.angus.day06;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.sql.Time;
import java.time.Duration;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/13 9:43
 * @description：
 */
public class ProcessFunctionTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        SingleOutputStreamOperator<Event> clickSource = env.addSource(new ClickSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));

        SingleOutputStreamOperator<String> process = clickSource.process(new ProcessFunction<Event, String>() {

            // 每来一个数据都会调用一次
            @Override
            public void processElement(Event value, ProcessFunction<Event, String>.Context ctx, Collector<String> out) throws Exception {
                if (value.user.equals("Bob")) {
                    out.collect("Bob:" + value);
                } else {
                    out.collect("other" + value);
                }

                System.out.println("timestamp" + new Time(ctx.timestamp()));
                System.out.println("waterMark" + new Time(ctx.timerService().currentWatermark()));
                System.out.println(getRuntimeContext().getIndexOfThisSubtask());
            }
        });

        process.print();

        env.execute();
    }
}
