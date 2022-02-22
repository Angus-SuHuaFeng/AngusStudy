package ReduceJoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class RJMapper extends Mapper<LongWritable, Text, Text, TableBean> {
//    创建封装key对象
    Text k = new Text();
//    创建封装value对象
    TableBean bean = new TableBean();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        通过上下文获取切片信息(获取文件名称)
        FileSplit Split = (FileSplit) context.getInputSplit();
//        由切片信息获取文件名称
        String name = Split.getPath().getName();
//        获取输入文本数据
        String line = value.toString();
//        根据不同的文件分别处理数据
        if (name.startsWith("order")){
//            分割字段
            String[] fields = line.split("\t");
//            封装TableBean对象
            bean.setOrder_id(fields[0]);
            bean.setP_id(fields[1]);
            bean.setAmount(Integer.parseInt(fields[2]));
            bean.setP_Name("");
            bean.setTable_source("order.txt");
//            封装输出key
            k.set(fields[1]);
        }
        else {
            String[] fields = line.split("\t");
            bean.setOrder_id("");
            bean.setP_id(fields[0]);
            bean.setAmount(0);
            bean.setP_Name(fields[1]);
            bean.setTable_source("pd.txt");
            k.set(fields[0]);
        }
        context.write(k, bean);
    }
}
