package InputFormatTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class FileRecordReader extends RecordReader<Text, BytesWritable> {
    private Text k = new Text();
    private BytesWritable v = new BytesWritable();
    private FileSplit fileSplit;
    private Configuration conf;
    boolean isProcess = true;
    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext Context) throws IOException, InterruptedException {
//        设置切片方式
         this.fileSplit = (FileSplit) inputSplit;
//        获取配置文件
         conf = Context.getConfiguration();
    }
    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (isProcess){
//        获取文件路径
            Path path = fileSplit.getPath();
            FileSystem fileSystem = path.getFileSystem(conf);
//        获取文件流（输入流）
            FSDataInputStream fis = fileSystem.open(path);
//        拷贝
            byte[] buff = new byte[(int) fileSplit.getLength()];
//        将数据放入buff
            IOUtils.readFully(fis, buff, 0, buff.length);
//        封装数据到key, value里面
            k.set(path.toString());
            v.set(buff, 0, buff.length);
//        资源关闭
            IOUtils.closeStream(fis);
            isProcess = false;
            return true;
        }
        return false;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return k;
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return v;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
