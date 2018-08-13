import java.util.concurrent.TimeUnit;

/**
 * Author : xieweig
 * Date : 18-8-13
 * <p>
 * Description:
 */
public class WaitForNotice {
    //private String lock = "no matter what it is"; 如果全用lambda表达式则不需要全局化
    public static final String lock = "no matter what it is";


    public Runnable wife(){
        System.out.println("Wife :准备去参加个喝酒吹牛$party 准备上车了");
        return () -> {

            System.out.println("Wife :wait for me 化个妆先\n");

            try { TimeUnit.SECONDS.sleep(6); } catch (InterruptedException e) { e.printStackTrace(); }

            System.out.println("Wife :好了走吧，催催催，赶着去...");
            /**
            **
            * xieweig notes: 注意lambda表达式的 穿透效应 可以直接调用到lock this代表谁？
            */
            /*synchronized (this.lock){

                this.lock.notify();
            }*/
            synchronized (WaitForNotice.lock){
                WaitForNotice.lock.notify();
            }

            System.out.println(Thread.currentThread().getName()+" == end == ");

        };
    }

    public Runnable husband() {
        System.out.println("Husband :准备去参加个喝酒吹牛$party 准备开车了");


        return new Runnable() {


            @Override
            public void run() {

                    try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }


                    System.out.println("husband :好了没\n");


                    /**
                    **
                    * xieweig notes: 注意这里的this代表谁？ 此处不能直接调用到lock，所以只有把lock全局化，如果借助spring容器，可以有更多灵活处理
                    */
                    synchronized(WaitForNotice.lock) {

                        try {
                            WaitForNotice.lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    System.out.println("husband : go_go_go");
                System.out.println(Thread.currentThread().getName()+" == end == ");

            }
        };
    }
}
