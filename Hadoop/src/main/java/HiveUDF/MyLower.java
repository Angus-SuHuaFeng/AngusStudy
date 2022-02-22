package HiveUDF;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author Angus
 */
public class MyLower extends UDF {
    public String evaluate(final String s){
        if (s==null){
            return null;
        }else {
            return s.toLowerCase();
        }
    }
}
