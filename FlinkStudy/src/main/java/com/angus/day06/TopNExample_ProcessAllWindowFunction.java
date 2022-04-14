package com.angus.day06;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import com.angus.day05.URLPOJO;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/13 19:12
 * @description：
 */
public class TopNExample_ProcessAllWindowFunction {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        SingleOutputStreamOperator<Event> streamOperator = env.addSource(new ClickSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));

        streamOperator.print();

        // TODO 1.直接开窗，收集所有数据排序
        SingleOutputStreamOperator<String> urlCountStream = streamOperator.map(data -> data.user)
                .windowAll(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(5)))
                .aggregate(new UrlHashMapCountAgg(), new UrlAllWindowResult());

        urlCountStream.print();

        env.execute();

    }



    private static class UrlHashMapCountAgg implements AggregateFunction<String , HashMap<String, Long>, ArrayList<Tuple2<String , Long>>>{
        @Override
        public HashMap<String, Long> createAccumulator() {
            return new HashMap<>();
        }

        @Override
        public HashMap<String, Long> add(String value, HashMap<String, Long> accumulator) {
            if (accumulator.containsKey(value)){
                accumulator.put(value, accumulator.get(value)+1);
            }else {
                accumulator.put(value, 1L);
            }
            return accumulator;
        }

        @Override
        public ArrayList<Tuple2<String, Long>> getResult(HashMap<String, Long> accumulator) {
            ArrayList<Tuple2<String, Long>> arrayList = new ArrayList<>();
            for (String key : accumulator.keySet()) {
                arrayList.add(Tuple2.of(key, accumulator.get(key)));
            }
            arrayList.sort(new Comparator<Tuple2<String, Long>>() {
                @Override
                public int compare(Tuple2<String, Long> o1, Tuple2<String, Long> o2) {
                    return (int) (o2.f1- o1.f1) ;
                }
            });
            return arrayList;
        }

        @Override
        public HashMap<String, Long> merge(HashMap<String, Long> a, HashMap<String, Long> b) {
            return null;
        }
    }

    private static class UrlAllWindowResult extends ProcessAllWindowFunction<ArrayList<Tuple2<String, Long>>, String, TimeWindow> {
        @Override
        public void process(ProcessAllWindowFunction<ArrayList<Tuple2<String, Long>>, String, TimeWindow>.Context context, Iterable<ArrayList<Tuple2<String, Long>>> elements, Collector<String> out) throws Exception {
            ArrayList<Tuple2<String, Long>> list = elements.iterator().next();

            StringBuilder builder = new StringBuilder();
            builder.append("----------------------------------------").append("\n");
            builder.append("窗口结束时间: " + new Timestamp(context.window().getEnd()) + "\n");
            // 取List前俩个，包装输出
            for (int i = 0; i < 2; i++) {
                Tuple2<String, Long> tuple2 = list.get(i);
                String info = "No. " + (i+1) + " "
                        + "url: " + tuple2.f0 + " "
                        + "访问量: " + tuple2.f1 + "\n";
                builder.append(info);
                builder.append("-----------------------------------").append("\n");
            }
            out.collect(builder.toString());
        }
    }


}
