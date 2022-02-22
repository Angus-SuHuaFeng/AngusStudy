package HbaseDemo.HbaseMR.HdfsToHbase;


import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class HDriver extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = this.getConf();
        conf.set("hbase.zookeeper.quorum", "BigData1");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.set("zookeeper.znode.parent", "/hbase");
        Job job = Job.getInstance(conf, this.getClass().getSimpleName());
//        设置驱动类
        job.setJarByClass(HDriver.class);
//        设置Mapper
        job.setMapperClass(HMapper.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(Put.class);
//        设置Reducer
        TableMapReduceUtil.initTableReducerJob("city",HReduce.class,job);
//        设置reduce个数
        job.setNumReduceTasks(1);
//        设置输入路径
        FileInputFormat.addInputPath(job, new Path("hdfs://BigData1:9000//opt//data//hbase//emp.txt"));
        FileOutputFormat.setOutputPath(job,new Path("hdfs://BigData1:9000//opt//data//hbase"));
        boolean success = job.waitForCompletion(true);
        return success?0:1;
    }

    @SneakyThrows
    public static void main(String[] args)  {
        Configuration conf = HBaseConfiguration.create();
        int result = ToolRunner.run(conf, new HDriver(), args);
        System.out.println("上传数据结果 " + result);
    }
}
