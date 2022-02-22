package day21.Demo2;

/**
 * 可以再创建一个工人干不同的工作,同样需要继承Worker接口
 */
public class RealWorker2 extends RealWorker implements Worker{
    @Override
    public void work() {
        System.out.println("维修");
    }
}
