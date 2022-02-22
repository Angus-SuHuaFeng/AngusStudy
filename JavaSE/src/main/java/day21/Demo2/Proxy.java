package day21.Demo2;

public class Proxy implements Worker {

    //代理对象持有目标对象
    private RealWorker realWorker;
    private RealWorker2 realWorker2;

    public Proxy() {
        realWorker = new RealWorker();
        realWorker2 = new RealWorker2();
    }

    public void work() {
        System.out.println("招待，咨询业务");
        realWorker.work();
        realWorker2.work();
        System.out.println("售后");
    }


}
