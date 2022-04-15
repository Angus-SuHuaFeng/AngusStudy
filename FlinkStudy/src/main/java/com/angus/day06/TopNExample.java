package com.angus.day06;

import com.angus.day02.ClickSource;
import com.angus.day02.Event;
import com.angus.day05.URLExample;
import com.angus.day05.URLPOJO;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/13 19:12
 * @description：
 */
public class TopNExample {
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
        streamOperator.print("data");

        // TODO 1.按照URL分组
        SingleOutputStreamOperator<URLPOJO> urlCountStream = streamOperator.keyBy(data -> data.url)
                .window(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(5)))
                .aggregate(new URLAggregateFunction(), new URLProcessWindowFunction());

        urlCountStream.print("url");

        // TODO 2.对同一窗口统计出的访问量进行牌序
        urlCountStream.keyBy(data->data.windowEnd)
                        .process(new TopNProcessResultFunction(2))
                                .print();


        env.execute();

    }

    public static class URLAggregateFunction implements AggregateFunction<Event, Long, Long>, com.angus.day05.UVAggregateFunction {

        @Override
        public Long createAccumulator() {
            return 0L;
        }

        @Override
        public Long add(Event value, Long accumulator) {
            return accumulator+1;
        }

        @Override
        public Long getResult(Long accumulator) {
            return accumulator;
        }

        @Override
        public Long merge(Long a, Long b) {
            return a+b;
        }


    }

    private static class URLProcessWindowFunction extends ProcessWindowFunction<Long, URLPOJO, String , TimeWindow> {
        @Override
        public void process(String url, ProcessWindowFunction<Long, URLPOJO, String, TimeWindow>.Context context, Iterable<Long> elements, Collector<URLPOJO> out) throws Exception {
            long start = context.window().getStart();
            long end = context.window().getEnd();
            long count = elements.iterator().next();
            out.collect(new URLPOJO(url, count, start, end));
        }
    }

    private static class TopNProcessResultFunction extends KeyedProcessFunction<Long, URLPOJO, String> {
        private int n;

        public TopNProcessResultFunction(int i) {
            this.n = i;
        }

        private ListState<URLPOJO> urlListState;

        // 在环境中获取状态


        @Override
        public void open(Configuration parameters) throws Exception {
            urlListState = getRuntimeContext().getListState(
                    new ListStateDescriptor<URLPOJO>("url-count-list", Types.POJO(URLPOJO.class))
            );
        }

        @Override
        public void processElement(URLPOJO value, KeyedProcessFunction<Long, URLPOJO, String>.Context ctx, Collector<String> out) throws Exception {
            // 将数据保存到状态中
            urlListState.add(value);
            // 注册windowEnd + 1ms的定时器
            ctx.timerService().registerEventTimeTimer(ctx.getCurrentKey() + 1);
        }

        @Override
        public void onTimer(long timestamp, KeyedProcessFunction<Long, URLPOJO, String>.OnTimerContext ctx, Collector<String> out) throws Exception {
            ArrayList<URLPOJO> urlpojos = new ArrayList<>();
            // 从状态中提取数据
            for (URLPOJO url : urlListState.get()){
                urlpojos.add(url);
            }
            // 排序
            urlpojos.sort(new Comparator<URLPOJO>() {
                @Override
                public int compare(URLPOJO o1, URLPOJO o2) {
                    return (int) (o2.count-o1.count);
                }
            });

            // 打印输出
            StringBuilder builder = new StringBuilder();
            builder.append("----------------------------------------").append("\n");
            builder.append("窗口结束时间: " + new Timestamp(ctx.getCurrentKey()) + "\n");
            // 取List前俩个，包装输出
            for (int i = 0; i < 2; i++) {
                URLPOJO urlpojo = urlpojos.get(i);
                String info = "No. " + (i+1) + " "
                        + "url: " + urlpojo.url + " "
                        + "访问量: " + urlpojo.count + "\n";
                builder.append(info);
                builder.append("-----------------------------------").append("\n");
            }
            out.collect(builder.toString());
        }
    }
}
