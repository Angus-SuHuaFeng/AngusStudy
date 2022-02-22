package MRDemo;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author Angus
 */
public class WordCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //获取配置信息
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://BigData1:9000");
        Job job = Job.getInstance(conf);
        //设置此类（Driver）类jar加载路径
        job.setJarByClass(WordCountDriver.class);
        //设置Mapper的jar路径
        job.setMapperClass(WordCountMapper.class);
        //设置Reducer的jar路径
        job.setReducerClass(WordCountReducer.class);

        //设置Mapper的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //设置Reducer的输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //分区
        job.setPartitionerClass(WordCountPartitioner.class);
        job.setNumReduceTasks(27);

        //设置输入输出路径
        FileInputFormat.setInputPaths(job,new Path("E:\\Data1\\CET4.txt"));
        FileOutputFormat.setOutputPath(job,new Path("E:\\Data1\\out"));

        //提交任务
        boolean resout = job.waitForCompletion(true);
        System.exit(resout?0:1);

    }
}
