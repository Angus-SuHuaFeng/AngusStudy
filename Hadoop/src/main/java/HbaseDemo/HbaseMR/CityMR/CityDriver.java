package HbaseDemo.HbaseMR.CityMR;

import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


/**
 * @author Angus
 */
public class CityDriver extends Configured implements Tool {

    public static void main(String[] args) {
        Configuration conf = HBaseConfiguration.create();
        int status = 0;
        try {
            status = ToolRunner.run(conf,new CityDriver(),args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = this.getConf();
//        创建job
        Job job = Job.getInstance(conf, this.getClass().getSimpleName());
        job.setJarByClass(CityDriver.class);

//        配置job
        Scan scan = new Scan();
        scan.setCacheBlocks(false);
        scan.setCaching(500);

//        设置Mapper
        TableMapReduceUtil.initTableMapperJob(
                "city",
                scan,
                CityMapper.class,
                ImmutableBytesWritable.class,
                Put.class,
                job
        );
//        设置Reducer
        TableMapReduceUtil.initTableReducerJob(
                "city_mr",
                CityReducer.class,
                job

        );
        job.setNumReduceTasks(1);

        boolean success = job.waitForCompletion(true);
        if (success){
            System.out.println("Succeed");
        }else {
            System.out.println("failed");
        }
        return success?0:1;
    }
}
