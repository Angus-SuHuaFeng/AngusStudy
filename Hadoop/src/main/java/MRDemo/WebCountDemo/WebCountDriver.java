package MRDemo.WebCountDemo;

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
public class WebCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://BigData1:9000");
        Job job = null;
        try {
            job = Job.getInstance(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置此类（Driver）类jar加载路径
        job.setJarByClass(WebCountDriver.class);
        //设置Mapper的jar路径
        job.setMapperClass(WebCountMapper.class);
        //设置Reducer的jar路径
        job.setReducerClass(WebCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job,new Path("/input/journal.log"));
        FileOutputFormat.setOutputPath(job,new Path("/usr/pv"));
        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);

    }
}
