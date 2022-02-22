package MRDemo.IpCountDemo;

import MRDemo.WebCountDemo.WebCountDriver;
import MRDemo.WebCountDemo.WebCountMapper;
import MRDemo.WebCountDemo.WebCountReducer;
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
public class IpCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://101.200.78.254:9000");
        Job job = null;
        try {
            job = Job.getInstance(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置此类（Driver）类jar加载路径
        job.setJarByClass(IpCountDriver.class);
        //设置Mapper的jar路径
        job.setMapperClass(IpCountMapper.class);
        //设置Reducer的jar路径
        job.setReducerClass(IpCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job,new Path("/input/journal.log"));
        FileOutputFormat.setOutputPath(job,new Path("/usr/ip"));
        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);
    }
}
