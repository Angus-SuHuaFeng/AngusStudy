package com.angus.day01_wordcount;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

 /**
 * @author ：Angus
 * @date ：Created in 2022/4/5 20:48
 * @description： *******************无界流处理********************
 */
public class StreamWordCount {
    public static void main(String[] args) throws Exception {
        // TODO 1. 创建流式执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.disableOperatorChaining();
        // 从参数中提取主机名和端口号
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        String host = parameterTool.get("host");
        int port = parameterTool.getInt("port");
        // TODO 2. 从文件中读取数据
        DataStreamSource<String> lineDataStreamSource = env.socketTextStream(host, port);

        // TODO 3. 将每行数据进行分隔，转换成二元组      Java中没有Tuple类型，所以Flink为我们准备了Java版本的Tuple（Scala中自带）
        // 当 Lambda 表达式使用 Java 泛型的时候, 由于泛型擦除的存在, 需要显示的声明类型信息
        SingleOutputStreamOperator<Tuple2<String, Long>> streamOperator = lineDataStreamSource.flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
            // 1. 切分line
            String[] words = line.split(" ");
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        }).setParallelism(2).returns(Types.TUPLE(Types.STRING, Types.LONG)).slotSharingGroup("1");    // 可以对算子使用setParallelism()方法设置并行度

        // TODO 4. 按照word进行分组
        KeyedStream<Tuple2<String, Long>, String> keyedStream = streamOperator.keyBy(data -> data.f0);
        // TODO 5. 分组内进行聚合累加
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = keyedStream.sum(1);

        // TODO 6. 打印输出
        sum.print();

        // TODO 7. 启动执行
        env.execute();

    }
}
