package ReduceJoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class RJDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        args = new String[]{"C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\200111MR案例\\order",
                "C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\200111MR案例\\order\\output"};
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(RJDriver.class);

        job.setMapperClass(RJMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TableBean.class);

        job.setReducerClass(RJReducer.class);
        job.setOutputKeyClass(TableBean.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);
    }
}
