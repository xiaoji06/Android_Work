package cn.enjoyedu.proxy;


import cn.enjoyedu.proxy.dynamic.MarkCompany;
import cn.enjoyedu.proxy.dynamic.ProxyUtils;

import java.lang.reflect.Method;

public class Client {

    public static void main(String[] args) {
        /*静态代理模式---------------*/
//        ManToolsFactory factory = new AaFactory();
//        Mark mark = new Mark(factory);
//        mark.saleManTools("D");
//
//        WomanToolsFactory womanToolsFactory = new BbFactory();
//        Alvin av = new Alvin(womanToolsFactory);
//        av.saleWomanTools(1.8f);

        /*动态代理模式---------------*/
        ManToolsFactory aafactory = new AaFactory();
        MarkCompany markCompany = new MarkCompany();
        markCompany.setFactory(aafactory);

        //A来了
        ManToolsFactory employee1
                = (ManToolsFactory)markCompany.getProxyInstance();
        employee1.saleManTools("E");

        //B来了
        WomanToolsFactory bbToolsFactory = new BbFactory();
        markCompany.setFactory(bbToolsFactory);
        WomanToolsFactory employee2
                = (WomanToolsFactory)markCompany.getProxyInstance();
        employee2.saleWomanTools(1.9f);


        ProxyUtils.generateClassFile(aafactory.getClass(),
                employee1.getClass().getSimpleName());
        ProxyUtils.generateClassFile(bbToolsFactory.getClass(),
                employee2.getClass().getSimpleName());
        Method[] methods = aafactory.getClass().getMethods();
        for(Method method:methods) {
            System.out.println(method.getName());//打印方法名称
        }

    }

}
