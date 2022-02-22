package PhoneLogDemo;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 *      *  keyOut : Text（封装手机号码）
 *      *  valueOut : FlowBean（FlowBean中封装了上下行流量等）
 */

public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    //k, v 分别为写出时的key, value
    Text k = new Text();                 //Text
    FlowBean v = new FlowBean();         //FlowBean
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        获取一行数据
//   1363157985066 	13726230503	00-FD-07-A4-72-B8:CMCC	120.196.100.82	i02.c.aliimg.com		24	27	2481	24681	200
        String line = value.toString();
//        切割字段
        String[] split = line.split("\t");
//        获取手机号码
        String phoneNum = split[1];
//        获取上行流量
        long upFlow = Long.parseLong(split[split.length - 3]);
//        获取下行流量
        long downFlow = Long.parseLong(split[split.length - 2]);
//        封装
        k.set(phoneNum);
        v.setUpFLow(upFlow);
        v.setDownFlow(downFlow);
        context.write(k, v);
    }
}
