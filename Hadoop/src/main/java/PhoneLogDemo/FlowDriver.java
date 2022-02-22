package PhoneLogDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class FlowDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
//        获取配置信息
        Configuration conf = new Configuration( );
        Job job = Job.getInstance(conf);

//        设置Driver的jar路径
        job.setJarByClass(FlowBean.class);
//        设置Mapper的jar路径
        job.setMapperClass(FlowMapper.class);
//        设置Reducer的jar路径
        job.setReducerClass(FlowReducer.class);
//        设置Mapper的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowReducer.class);
//        设置Reducer的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
//        设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\191232MR编程及流程\\案例日志\\phone_data.txt"));
        FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\191232MR编程及流程\\案例日志\\Output"));

//        提交任务
        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);

    }
}
