package MRDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author Angus
 */
public class CountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://BigData1:9000");
        Job job = Job.getInstance(conf);
        //设置此类（Driver）类jar加载路径
        job.setJarByClass(CountDriver.class);
        //设置Mapper的jar路径
        job.setMapperClass(CountMapper.class);
        //设置Reducer的jar路径
        job.setReducerClass(CountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job,new Path("C:\\Users\\Angus\\Desktop\\words.txt"));
        FileOutputFormat.setOutputPath(job,new Path("C:\\Users\\Angus\\Desktop\\out"));
        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);
    }
}
