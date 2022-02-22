package Test;

import java.io.PipedInputStream;

public class Test {
    public static void main(String[] args) {
        String[] net = new String[]{"/about",
                "/black-ip-list/",
                "/cassandra-clustor/",
                "/finance-rhive-repurchase/",
                "/hadoop-family-roadmap/",
                "/hadoop-hive-intro/",
                "/hadoop-zookeeper-intro/",
                "/hadoop-mahout-roadmap/"
        };
        String st = "118.195.70.197 - - [18/Sep/2013:10:27:33 +0000] \"GET /hadoop-hive-intro/?cf_action=sync_comments&post_id=1227 HTTP/1.1\" 200 48 \"http://blog.fens.me/hadoop-hive-intro/\" \"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36\"";

        boolean matches = st.matches("(.*)"+net[4]+"(.*)");
        System.out.println(matches);
    }
}
