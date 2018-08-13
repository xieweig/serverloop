package pubsub;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Author : xieweig
 * Date : 18-8-13
 * <p>
 * Description:模拟spring容器取一些变量
 */
public class Global {
    /**
    **
    * xieweig notes: 第一步，声明一个全局容器Queue 先进先出，类似一个强化版的list 都是继承collection接口
     * 再声明 一个 全局锁 当然，不一定要是全局变量，能在consumer producer中 引用到了就行了
    */
    public static final BlockingQueue<String> requests = new LinkedBlockingDeque<>();

    //public static final String lock = "anyone"; 使用了queue 就不需要它了

    public static boolean looping = true;

    public static final ExecutorService pool =Executors.newFixedThreadPool(3);

    public static Random random = new Random();


}
