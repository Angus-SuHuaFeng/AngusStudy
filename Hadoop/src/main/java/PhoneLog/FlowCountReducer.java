package PhoneLog;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowCountReducer extends Reducer<Text,FlowBean,Text,FlowBean> {
    //手机号 ：{上行总和，下行总和，总流量}
    /**
     *  keyout   : 手机号 -->13726230503
     *  valueout : 流量数据 ：{上行总和，下行总和，总流量}
     */

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        long sum_upFlow = 0;
        long sum_downFlow = 0;
        long sum = 0;
        long phone =0;
        FlowBean flowBean = new FlowBean();
        for (FlowBean value : values) {
            phone = value.getPhoneNum();
            sum_upFlow+=value.getUpFlow();
            sum_downFlow+=value.getDownFlow();
            sum = sum_upFlow+sum_downFlow;
        }
        FlowBean flowBean1 = new FlowBean(sum_upFlow,sum_downFlow,sum,phone);
        context.write(key,flowBean1);
    }
    
}
