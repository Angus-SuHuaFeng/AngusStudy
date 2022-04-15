package com.angus.day02;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/6 22:05
 * @description：
 */
public class Order implements Serializable {
    public String user;
    public Long timestamp;

    public Order() {
    }

    public Order(String user, Long timestamp) {
        this.user = user;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "user='" + user + '\'' +
                ", timestamp=" + new Timestamp(timestamp) +
                '}';
    }
}
