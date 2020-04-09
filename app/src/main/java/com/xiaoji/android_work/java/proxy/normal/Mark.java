package cn.enjoyedu.proxy.normal;

import cn.enjoyedu.proxy.ManToolsFactory;
import cn.enjoyedu.proxy.WomanToolsFactory;

public class Mark implements ManToolsFactory,WomanToolsFactory {

    /*包含真实的对象*/
    public ManToolsFactory factory;

    public Mark(ManToolsFactory factory) {
        this.factory = factory;
    }

    /*前置处理器*/
    private void doSthAfter() {
        System.out.println("前置处理器");
    }

    /*后置处理器*/
    private void doSthBefore() {
        System.out.println("后置处理器");
    }


    @Override
    public void saleManTools(String size) {
        doSthAfter();
        factory.saleManTools(size);
        doSthBefore();
    }

    @Override
    public void saleWomanTools(float length) {

    }
}
