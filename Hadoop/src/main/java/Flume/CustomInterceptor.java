package Flume;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Angus
 */
public class CustomInterceptor implements Interceptor {

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
//        获取Event数据
        byte[] body = event.getBody();
        String s = new String(body).toUpperCase();
        event.setBody(s.getBytes(StandardCharsets.UTF_8));
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
//        创建一个集合用于接收处理好的Event，然后返回
        ArrayList<Event> reEvents = new ArrayList<>();
//        遍历event
        for (Event event : events){
            Event intercepted = intercept(event);
            reEvents.add(intercepted);
        }
        return reEvents;
    }

    @Override
    public void close() {

    }
    public static class Builder implements org.apache.flume.interceptor.Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new CustomInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
