package MRDemo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Angus
 */
public class WordCountReducer extends Reducer <Text, IntWritable,Text, LongWritable>{
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //初始化计数器
        int count = 0;
        //开始计数
        for (IntWritable value : values) {
//            int va = value.get();
//            count+=va;
            count = count + value.get();
        }
        context.write(key,new LongWritable(count));
    }
}

