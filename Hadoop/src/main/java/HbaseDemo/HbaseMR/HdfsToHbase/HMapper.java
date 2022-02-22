package HbaseDemo.HbaseMR.HdfsToHbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Angus
 *  将emp表从 HDFS 导入到 HBase
 *
 *  HMapper继承的是Hadoop的Mapper
 */
public class HMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, ImmutableBytesWritable, Put>.Context context) throws IOException, InterruptedException {
//        从HDFS文件中读数据
        String line = value.toString();
//        切割
        String[] fields = line.split("\t");
//        取值
        String rowKey = fields[0];
        String name = fields[1];
        String city = fields[2];
//        封装rowKey数据到ImmutableBytesWritable中
        ImmutableBytesWritable rowKeyWritable = new ImmutableBytesWritable(rowKey.getBytes());
//        封装Put类型数据到put对象中
        Put put = new Put(rowKey.getBytes());
//        封装第一个字段值
        put.addColumn(
                "INFO".getBytes(StandardCharsets.UTF_8),
                "name".getBytes(StandardCharsets.UTF_8),
                name.getBytes(StandardCharsets.UTF_8)
        );
//        封装第二个字段值
        put.addColumn(
                "INFO".getBytes(StandardCharsets.UTF_8),
                "city".getBytes(StandardCharsets.UTF_8),
                city.getBytes(StandardCharsets.UTF_8)
        );
//        将数据写入reduce端
        context.write(rowKeyWritable,put);
    }
}
