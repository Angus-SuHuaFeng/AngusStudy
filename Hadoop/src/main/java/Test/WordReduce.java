package Test;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * @author Angus
 */
public class WordReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
    /**
     * Map阶段产生:  (key,value)
     * @param key       word
     * @param values    同key的value都存在了迭代器里,{1,1,1,1,1,1,1,1,1,1}
     * @param context   传递
     * @throws IOException  IO
     * @throws InterruptedException In
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable value : values) {
            //value.get()方法可以得到int类型的value
            count += value.get();
        }
        //写入context,交给hadoop处理
        context.write(key,new IntWritable(count));
    }
}
