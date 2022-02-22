package com.angus.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/**
 * @author Angus
 */
public class EtlInterceptor implements Interceptor {

    /**
     * 连接外部组件时需要初始化
     */
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
//        取数据,然后进行校验
//        1.取数据
        byte[] body = event.getBody();
        String log = new String(body, StandardCharsets.UTF_8);
//        2.校验
        if(JsonUtils.isValidate(log)){
            return event;
        }
        return null;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        for (Event event : list){
            byte[] body = event.getBody();
            String log = new String(body, StandardCharsets.UTF_8);
            if (JsonUtils.isValidate(log)){

            }else {
                list.remove(event);
            }
        }

        Iterator<Event> iterator = list.iterator();
        while (iterator.hasNext()){
            Event next = iterator.next();
            if (intercept(next)==null){
                iterator.remove();
            }
        }
        return list;
    }
    /**
     * 连接的外部组件使用完毕后关闭
     */
    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{


        @Override
        public Interceptor build() {
            return new EtlInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
