package InputFormatTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;


public class FilesDriver{
    public static void main(String[] args) throws Exception {
//        获取配置信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
//        设置Driver的jar路径
        job.setJarByClass(FilesDriver.class);
//        设置Mapper的jar路径
        job.setMapperClass(FilesMapper.class);
//        设置Reducer的jar路径
        job.setReducerClass(FilesReducer.class);
//        设置Mapper返回的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
//        设置Reducer返回的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);
//        设置自己写的InputFormat
        job.setInputFormatClass(MyInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
//        输入输出
        FileInputFormat.setInputPaths(job, new Path("C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\200102MR编程解析\\代码及日志数据\\Phonedata"));
        FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\200102MR编程解析\\代码及日志数据\\Phonedata\\output"));
//        提交任务
        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);
    }
}
