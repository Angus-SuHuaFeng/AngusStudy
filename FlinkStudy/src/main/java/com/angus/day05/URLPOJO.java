package com.angus.day05;

import java.sql.Timestamp;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/12 0:10
 * @description：
 */
public class URLPOJO {
    public String url;
    public Long count;
    public Long windowStart;
    public Long windowEnd;

    public URLPOJO() {
    }

    public URLPOJO(String url, Long count, Long windowStart, Long windowEnd) {
        this.url = url;
        this.count = count;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
    }


    @Override
    public String toString() {
        return "URLCount{" +
                "url='" + url + '\'' +
                ", count=" + count +
                ", windowStart=" + new Timestamp(windowStart) +
                ", windowEnd=" + new Timestamp(windowEnd) +
                '}';
    }
}
