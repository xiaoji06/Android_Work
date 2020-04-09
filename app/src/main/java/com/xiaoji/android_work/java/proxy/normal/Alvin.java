package cn.enjoyedu.proxy.normal;

import cn.enjoyedu.proxy.WomanToolsFactory;

public class Alvin implements WomanToolsFactory {

    private WomanToolsFactory womanToolsFactory;

    public Alvin(WomanToolsFactory womanToolsFactory) {
        this.womanToolsFactory = womanToolsFactory;
    }

    @Override
    public void saleWomanTools(float length) {
        doSthBefore();
        womanToolsFactory.saleWomanTools(length);
        doSthAfter();
    }


    /*前置处理器*/
    private void doSthAfter() {
        System.out.println("前置处理器");
    }

    /*后置处理器*/
    private void doSthBefore() {
        System.out.println("后置处理器");
    }
}
