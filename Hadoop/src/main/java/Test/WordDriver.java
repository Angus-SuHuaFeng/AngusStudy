package Test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author Angus
 */
public class WordDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://bigdata1:8020");
        Job job = Job.getInstance(conf);
        //设置Driver(本类)的jar加载路径
        job.setJarByClass(WordDriver.class);
        //设置Mapper的jar加载路径
        job.setMapperClass(WordMapper.class);
        //设置Reducer的jar加载路径
        job.setReducerClass(WordReduce.class);
        //设置Map的输出Key,value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置Reduce的输出Key,value的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置输入输出路径(hdfs路径),如果是本地运行则需要将windows的hadoop开启
        FileInputFormat.setInputPaths(job,new Path("/input/test"));
        FileOutputFormat.setOutputPath(job, new Path("/out"));
        //提交任务
        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);
    }
}
