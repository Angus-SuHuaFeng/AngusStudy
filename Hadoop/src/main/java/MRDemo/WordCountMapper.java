package MRDemo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper <LongWritable, Text, Text, IntWritable> {
    //数据转换(转换成hadoop里的数据类型)String=>Text  int=>IntWritable
    Text k = new Text();
    IntWritable v = new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //打印行数
        System.out.println(key);
        //把一行里的内容拿来处理,拿到数据，进行数据转换，Text=>String,通过toString方法
        String line = value.toString();
        //切分
        String[] words = line.split("[\\pP\\pS\\pZ\\pN]");
        //处理拿到的每一个单词
        for (String word : words) {
            word = word.toLowerCase();
            k.set(word);
            context.write(k,v);
        }
    }
}