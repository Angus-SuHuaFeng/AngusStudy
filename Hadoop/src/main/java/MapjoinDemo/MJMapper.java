package MapjoinDemo;

import org.apache.commons.lang.StringUtils;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


/**
 *     将小表连接起来
 *     LongWritable:
 *     Text :
 *
 *     Text :
 *     NullWritable:
 */
public class MJMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    Map<String, String> pMap = new HashMap<>();
    @Override
    protected void setup(Context context) throws IOException {
//        获取缓存文件路径 !!!!!!!!! 此处为context.getCacheFiles()!!!!!!!!!!!
        URI[] cacheFiles = context.getCacheFiles();
        String Path = cacheFiles[0].getPath().toString();
//        获取分布式缓存
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(Path), "utf-8"));
//        处理数据
        String line;
        while (!StringUtils.isEmpty(line = reader.readLine())){
            String[] fields = line.split("\t");   // 分别储存 01  小米
            pMap.put(fields[0], fields[1]);     //循环将文件中的每一行数据以键值对存入HashMap
        }
    }

    /**
     *
     * @param key      文件名(表名)
     * @param value    文件内容(表中的数据)
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    Text k = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        获取一行数据
        String line = value.toString();
//        切分数据
        String[] fields = line.split("\t");
//        获取产品品牌p_id
        String p_id = fields[1];
//        获取品牌名称
        String p_name = pMap.get(p_id);
//        替换p_id为品牌并将fields数组转化成String
        String newFields = fields[1] + "\t" + p_name + "\t" + fields[2];
//        设置k值
        k.set(newFields);
//        写出数据
        context.write(k,NullWritable.get());
    }
}
