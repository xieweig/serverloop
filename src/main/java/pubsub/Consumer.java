package pubsub;

import pubsub.observer.KettyListener;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Author : xieweig
 * Date : 18-8-13
 * <p>
 * Description:
 */
public class Consumer {



    public Callable consume() {
        return () -> {

            while (Global.looping) {
                System.out.println(Thread.currentThread().getName() + " == consuming  == ");
                System.out.println("服务器 开始等待新一次请求");
                /**
                 **
                 * xieweig notes: 重点take方法 试图取，取不出就会一直等待着，处于休眠，锁的wait状态，并不会进行下一次循环，不消耗cpu，
                 * 何时会被唤醒，take对应put 方法每次put进一个元素，不仅改变了queue的元素，而且还会notify 锁，下一次cpu不小心蹦到该线程时候就取出来了
                 */
                String product = Global.requests.take();
                Future<String> future = Global.pool.submit(this.asyncHandle(product));
                System.out.println("服务器 本次请求处理完毕 "+ product);
               // System.out.println("服务器 本次请求处理完毕" + future.get());
            }

            return null;
        };
    }

    private KettyListener kettyListener;

    public Callable<String> asyncHandle(String product){

        return ()->{
            System.out.println("服务端controller  我正在消费product，例如保存到数据库，发送对应的一封邮件，等等许多耗时操作。 "+product);

            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName()+" == actual handling == ");
            System.out.println(product.substring(product.lastIndexOf(".")+1));
            Integer number = Integer.parseInt(product.substring(product.lastIndexOf(".")+1));

            // if ((number&1) == 0 ){说明number不是以1结尾的二进制数字，也就是偶数} 不过为了代码可读性，有时候更愿意牺牲性能，不进行位运算。
            Class<?> clazz = kettyListener.getClass();

            /**
            **
            * xieweig notes: 此处代码虽然重复，但是if return这种代码结构思路非常清晰，值得拥有
             * 一边复习字符串驱动，一边练习if return。如果有大量的代码重复，那似乎说明应该单独截取if return的结构为一个方法，而不是放弃使用if return
            */
            if (number%2 == 0){
                Method method = clazz.getMethod("method2", Integer.class);
                method.invoke(kettyListener,number);
                return product.substring(12);
            }

            if (number%3 == 0){
                Method method = clazz.getMethod("method3", Integer.class, String.class);
                method.invoke(kettyListener,number," DatabaseName : KV");
                //反射的包括number databaseName 甚至是className methodName 都可以是从前端的数据 product中取，先找map做映射 然后反射驱动，这边product设计简陋 只是一个字符串，最好是复杂对象 另外没有map kv映射 而是 简单一个if选择
                return product.substring(12);
            }

            Method method = clazz.getMethod("method1");
            method.invoke(kettyListener);
            return product.substring(12);
        };
    }

    public Consumer(){
        /**
        **
        * xieweig notes: 纯粹的字符串反射，spring工程中只需要从容器中取就可以了。这里手写了类的无参数对象的反射
         * catch 子句 是备选方案，俗称 替补 备胎 ，在spring cloud 中 hystrix 断路器就是 远程接口调用的备胎
         * catch 子句 多个参数可以 用 或运算符 连接在一起，这样只需要写一句备胎代码就可以了 如下，可以把 InstanceE也移入
        */
        try {
           kettyListener= (KettyListener) Class.forName("pubsub.observer.KettyListener").newInstance();
        } catch (ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
            kettyListener= new KettyListener();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
