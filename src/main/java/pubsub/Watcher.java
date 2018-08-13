package pubsub;

import pubsub.gui.GuiClient;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Author : xieweig
 * Date : 18-8-13
 * <p>
 * Description:
 */
public class Watcher {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
        **
        * xieweig notes: 先开服务器 consumer 再模拟请求
        */
        GuiClient guiClient = new GuiClient();

        Consumer consumer = new Consumer();

        Global.pool.submit(consumer.consume());

        Producer producer = new Producer();
        for (int i = 0; i <6 ; i++) {

           Integer j = Global.random.nextInt(5)+1;

           /*Future<String> future =*/ Global.pool.submit(producer.produce());

        }
        System.out.println(Thread.currentThread().getName()+" == end == ");

    }
}
