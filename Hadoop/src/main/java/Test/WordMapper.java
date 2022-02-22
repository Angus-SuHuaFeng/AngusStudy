package Test;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Angus
 */
public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    Text k = new Text();
    IntWritable v = new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //打印行数
        System.out.println(key);
        //将value值的类型(原类型为Text)转化为String方可使用
        String line = value.toString();
        //将每一行的单词进行分割,split里的参数根据情况而定
        String[] words = line.split("[\\pP\\pS\\pZ\\pN]");
        //对一行分割好的每个单词进行处理,写入context,由context传递到reduce阶段
        //context是用来传递数据以及其他运行状态信息，map中的key、value写入context，
        // 让它传递给Reducer进行reduce，而reduce进行处理之后数据继续写入context，继续交给Hadoop写入hdfs系统。
        for (String word : words) {
            k.set(word);
            context.write(k,v);
        }
    }
}
