package PhoneLogDemo;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    /**   结果： 手机号 : (上行总和， 下行总和， 总流量)
     *    keyOut :  Text (手机号)
     *    valueOut : FlowBean (流量数据)
     */

    /**
     *
     * @param key  Text   13726230503
     * @param values  {{上行, 下行},{上行, 下行}...}
     * @param context  结果
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        long sum_upFlow = 0;
        long sum_downFlow = 0;
        long sum_Flow = 0;
        long phoneNum = 0;
        for (FlowBean Flow :
                values) {
            phoneNum = Flow.getPhoneNum();
            sum_upFlow += Flow.getUpFLow();
            sum_downFlow += Flow.getDownFlow();
            sum_Flow += sum_upFlow + sum_downFlow;
        }
//        封装value (将数据封装进Bean)
        FlowBean value = new FlowBean(sum_upFlow, sum_downFlow, sum_Flow, phoneNum);
        context.write(key, value);
    }
}
