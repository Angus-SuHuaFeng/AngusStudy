package com.angus.day01_wordcount;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author ：Angus
 * @date ：Created in 2022/4/5 19:31
 * @description： Flink wordCount
 */
public class BatchWordCount {
    public static void main(String[] args) throws Exception {
        // TODO 1. 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // TODO 2. 从文件中读取数据
        DataSource<String> lineWords = env.readTextFile("FlinkStudy/input/words.txt");

        // TODO 3. 将每行数据进行分隔，转换成二元组      Java中没有Tuple类型，所以Flink为我们准备了Java版本的Tuple（Scala中自带）
        FlatMapOperator<String, Tuple2<String, Long>> flatMapOperator = lineWords.flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
            // 1. 将一行数据以空格切分
            String[] words = line.split(" ");
            // 2. 将每个单词转换成二元组输出
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.LONG));

        // TODO 4. 按照word进行分组
        UnsortedGrouping<Tuple2<String, Long>> wordGroup = flatMapOperator.groupBy(0);

        // TODO 5. 分组内进行聚合累加
        AggregateOperator<Tuple2<String, Long>> aggregateOperator = wordGroup.sum(1);

        // TODO 6. 打印输出
        aggregateOperator.print();
    }
}
