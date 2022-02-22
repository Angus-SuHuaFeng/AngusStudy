package PhoneLog;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
//1369850315766 13726230503	00-FD-07-A4-72-B8:CMCC	120.196.100.82	i02.c.aliimg.com 24	27	2481 24681	200
public class FlowCountMapper extends Mapper <LongWritable, Text,Text,FlowBean>{
    /**
     *     keyOut  :  Text:  封装手机号码
     *     valueOut:  FlowBean ：封装上下行流量和总流量
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    Text k = new Text();
    FlowBean v = new FlowBean();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取其中一行数据
        String line = value.toString();
        //分割字段
        String[] split = line.split("\t");
        //获取手机号码 :作为keyOut
        String phoneNum = split[1];
        //获取上行流量
        long upFlow = Long.parseLong(split[split.length-3]);
        //获取下行流量
        long downFlow = Long.parseLong(split[split.length-2]);

        //封装数据
        k.set(phoneNum);
        //封装FolwBean
        v.setUpFlow(upFlow);
        v.setDownFlow(downFlow);
        v.setPhoneNum(Long.parseLong(phoneNum));
        //写入数据
        context.write(k,v);

    }
}
