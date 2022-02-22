package MapjoinDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MJDriver{
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        args = new String[]{"C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\200111MR案例\\order\\order.txt",
                "C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\200111MR案例\\order\\output",
                "file:///C:/Users/Angus/Desktop/第二阶段：离线开发/第二阶段：离线开发/200111MR案例/order/pd.txt"};
//        获取配置信息
        Configuration conf = new Configuration();

//        添加job并指定Driver的jar加载路径
        Job job = Job.getInstance(conf);
        job.setJarByClass(MJDriver.class);
//        设定Mapper的jar加载路径和输出数据类型
        job.setMapperClass(MJMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
//        设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//        设置map端缓存文件的路径
        job.addCacheFile(new URI(args[2]));
        job.setNumReduceTasks(0);
//        提交任务
        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);
    }
}
