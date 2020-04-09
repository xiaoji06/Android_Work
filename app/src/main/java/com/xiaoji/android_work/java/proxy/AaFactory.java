package cn.enjoyedu.proxy;

public class AaFactory implements ManToolsFactory {
    @Override
    public void saleManTools(String size) {
        System.out.println("A工厂生产："+size+"");
    }
}
