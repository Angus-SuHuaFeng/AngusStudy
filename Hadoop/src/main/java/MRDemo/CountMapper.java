package MRDemo;

import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Angus
 */
public class CountMapper extends Mapper<Text,Text,Text, Text> {
    Text k = new Text();
    Text v = new Text();
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        String str = value.toString();
        String[] split = str.split("[\\pP\\pS\\pZ\\pN]");
        k.set(split[split.length-1]);
        v.set(String.valueOf(1));
        context.write(k,v);
    }
}

