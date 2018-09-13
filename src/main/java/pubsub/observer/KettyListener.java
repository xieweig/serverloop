package pubsub.observer;


import pubsub.Global;

/**
 * Author : xieweig
 * Date : 18-8-13
 * <p>
 * Description:
 */
public class KettyListener {
    /**
    **
    * xieweig notes: consumer中用字符串反射调用以下三个方法
    */
    public void method1(){
        System.out.println("此处将数据存储到mysql with bean JpaRepository");
    }
    public void method2(Integer id){
        System.out.println("此处将数据存储到mongodb with bean MongodbRepository "+id);

    }
    public void method3(Integer id, String databaseName){
        System.out.println("此处将数据存储到redis with bean StringRedisTemplate "+ id +databaseName);
    }


}
