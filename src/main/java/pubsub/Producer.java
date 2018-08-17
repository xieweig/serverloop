package pubsub;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Author : xieweig
 * Date : 18-8-13
 * <p>
 * Description:
 */
public class Producer {


    public Callable<String> produce(){
        return ()->{

            TimeUnit.SECONDS.sleep(1);

            String product = "request from 192.168.10."+Global.random.nextInt(256);

            Global.requests.put(product);

            System.out.println(Thread.currentThread().getName()+" == producing == \n"+Global.requests);


            return product;
        };
    }
}
