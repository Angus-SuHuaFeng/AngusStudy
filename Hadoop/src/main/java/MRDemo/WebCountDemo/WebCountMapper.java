package MRDemo.WebCountDemo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 194.237.142.21 - - [18/Sep/2013:06:49:18 +0000] "GET /wp-content/uploads/2013/07/rstudio-git3.png HTTP/1.1" 304 0 "-" "Mozilla/4.0 (compatible;)"
 *
 *  统计资源访问量
 * @author Angus
 */
public class WebCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    Text k = new Text();
    IntWritable v = new IntWritable(1);
    String[] net = new String[]{"/about/",
                                "/black-ip-list/",
                                "/cassandra-clustor/",
                                "/finance-rhive-repurchase/",
                                "/hadoop-family-roadmap/",
                                "/hadoop-hive-intro/",
                                "/hadoop-zookeeper-intro/",
                                "/hadoop-mahout-roadmap/"
    };
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (line==null){
            return;
        }
        for (int i=0;i < net.length;i++){
            if (line.matches("(.*)"+net[i]+"(.*)")){
                System.out.println(line);
                k.set(net[i]);
                context.write(k,v);
                break;
            }
        }
    }
}
