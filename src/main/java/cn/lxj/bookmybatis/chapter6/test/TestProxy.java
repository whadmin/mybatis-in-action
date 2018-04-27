package cn.lxj.bookmybatis.chapter6.test;

import cn.lxj.bookmybatis.chapter6.Service.HelloService;
import cn.lxj.bookmybatis.chapter6.Service.impl.HelloServiceImpl;
import cn.lxj.bookmybatis.chapter6.Service.proxy.HelloServiceProxy;

/**
 * TestProxy
 * description 测试helloservice的代理
 * create by lxj 2018/4/27
 **/
public class TestProxy {
    public static void main(String[] args) {
        HelloServiceProxy HelloHandler = new HelloServiceProxy();
        HelloService proxy = (HelloService) HelloHandler.bind(new HelloServiceImpl());
        proxy.sayHello("lisi");
    }
}
