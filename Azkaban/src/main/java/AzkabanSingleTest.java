import lombok.*;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;



/**
 * @author Angus
 */
public class AzkabanSingleTest {
    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup FileOutputStream fileOutputStream = new FileOutputStream("/opt/data/azkaban/data");
        fileOutputStream.write("你好".getBytes(StandardCharsets.UTF_8));
    }
}
class LombokTest{
    int id;
    @Getter@Setter String name;
}
