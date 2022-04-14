package com.angus.day02;

import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Calendar;
import java.util.Random;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/6 22:50
 * @description：
 */
public class ClickParallelSource implements ParallelSourceFunction<Event> {
    // TODO 声明一个标志位用来控制停止
    private boolean running = true;
    // 随机生成数据
    Random random = new Random();
    // 数据集
    String[] users = {"Mary", "Angus", "Bob", "Tom", "Candy"};
    String[] urls = {"./home", "./cart", "./prod?id=100", "./prod?id=1"};

    @Override
    public void run(SourceContext<Event> sc) throws Exception {
        // 循环生成数据
        while (running) {
            String user = users[random.nextInt(users.length)];
            String url = urls[random.nextInt(urls.length)];
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            sc.collect(new Event(user, url, timeInMillis));
            Thread.sleep(200);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
