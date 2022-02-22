package ReduceJoin;

import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

public class RJReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {
    @SneakyThrows
    @Override
    protected void  reduce(Text key, Iterable<TableBean> values, Context context) throws IOException, InterruptedException {
//        创建集合存放多个order表中的数据（p_id相同的）
        ArrayList<TableBean> orderBeans = new ArrayList<TableBean>();
//        创建一个对象存放单个pd表中的数据（根据p_id区分）
        TableBean pdBean = new TableBean();
        for (TableBean value: values) {
            TableBean bean = new TableBean();
//            筛选orderBean数据到集合中
            if (value.getTable_source().equals("order.txt")){
                BeanUtils.copyProperties(bean, value);
                orderBeans.add(bean);   //存放到集合里
            }else {

                BeanUtils.copyProperties(pdBean, value);
            }
        }
        for (TableBean orders : orderBeans) {
            orders.setP_Name(pdBean.getP_Name());
            context.write(orders, NullWritable.get());
        }
    }
}
