package PhoneLog;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(FlowCountDriver.class);
        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        FileInputFormat.setInputPaths(job,new Path("C:\\Users\\86155\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\191232MR编程及流程\\案例日志\\phone_data.txt"));
        FileOutputFormat.setOutputPath(job,new Path("C:\\Users\\86155\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\191232MR编程及流程\\案例日志\\output"));
        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);
    }
}
