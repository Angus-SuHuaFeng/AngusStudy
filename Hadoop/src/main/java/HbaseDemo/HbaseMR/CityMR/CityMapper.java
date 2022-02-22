package HbaseDemo.HbaseMR.CityMR;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Angus
 */
public class CityMapper extends TableMapper<ImmutableBytesWritable, Put> {
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Mapper<ImmutableBytesWritable, Result, ImmutableBytesWritable, Put>.Context context) throws IOException, InterruptedException {
        Put put = new Put(key.get());
        for (Cell cell: value.rawCells()){
            if ("info".equals(Bytes.toString(CellUtil.cloneFamily(cell)))){
                if ("name".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))){
                    put.add(cell);
                }else if ("addr".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))){
                    put.add(cell);
                }
            }
        }
        context.write(key, put);
    }
}