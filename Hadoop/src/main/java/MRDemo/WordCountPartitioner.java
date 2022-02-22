package MRDemo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Partitioner;

public class WordCountPartitioner extends Partitioner<Text, IntWritable> {
    @Override
    public int getPartition(Text text, IntWritable intWritable, int i) {
        String word = text.toString();
        String words = word.toLowerCase();
        if (words.startsWith("a")){
            return 0;
        }else if(words.startsWith("b")){
            return 1;
        }else if (words.startsWith("c")){
            return 2;
        }else if (words.startsWith("d")){
            return 3;
        }else if (words.startsWith("e")){
            return 4;
        }else if(words.startsWith("f")){
            return 5;
        }else if (words.startsWith("g")){
            return 6;
        } else if (words.startsWith("h")) {
            return 7;
        }else if (words.startsWith("i")){
            return 8;
        }else if (words.startsWith("j")){
            return 9;
        }else if (words.startsWith("k")){
            return 10;
        }else if (words.startsWith("l")){
            return 11;
        }else if (words.startsWith("m")){
            return 12;
        }else if (words.startsWith("n")){
            return 13;
        }else if(words.startsWith("o")){
            return 14;
        }else if (words.startsWith("p")){
            return 15;
        }else if (words.startsWith("q")){
            return 16;
        }else if(words.startsWith("r")){
            return 17;
        }else if (words.startsWith("s")){
            return 18;
        }else if (words.startsWith("t")){
            return 19;
        }else if (words.startsWith("u")){
            return 20;
        }else if (words.startsWith("v")){
            return 21;
        }else if (words.startsWith("w")){
            return 22;
        }else if (words.startsWith("x")){
            return 23;
        }else if (words.startsWith("y")){
            return 24;
        }else if (words.startsWith("z")){
            return 25;
        }else {
            return 26;
        }
    }
}
