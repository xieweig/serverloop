import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author : xieweig
 * Date : 18-8-13
 * <p>
 * Description:
 */
public class Client {

    public static void main(String[] args){
        WaitForNotice waitForNotice = new WaitForNotice();
        /**
        **
        * xieweig notes: 开个线程池。里面有两个做任务的线程，之后提交任务Runnable
        */
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(waitForNotice.husband());
        executorService.submit(waitForNotice.wife());
        System.out.println(Thread.currentThread().getName()+" == end == ");
        /**
        **
        * xieweig notes: 观察控制台的输出，主线程 main end 后，其他线程不一定end。而是变成了业务逻辑上的主线程。这是其一
         * 其二：业务逻辑两个Runnable执行完毕后，分别end，但是控制台并没有停止，说明线程池里的线程依旧存在，并没有销毁，只是完成了当前的任务
        */
    }
}
