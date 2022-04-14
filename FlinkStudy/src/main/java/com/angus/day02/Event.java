package com.angus.day02;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/6 22:05
 * @description：
 */
public class Event implements Serializable {
    public String user;
    public String url;
    public Long timestamp;

    public Event() {
    }

    public Event(String user, String url, Long timestamp) {
        this.user = user;
        this.url = url;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "user='" + user + '\'' +
                ", url='" + url + '\'' +
                ", timestamp=" + new Timestamp(timestamp) +
                '}';
    }
}
