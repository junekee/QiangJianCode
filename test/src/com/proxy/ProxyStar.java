package com.proxy;

public class ProxyStar implements StarInterface {
    //私有化被代理角色
    private StarInterface star;
    private final String PROXY_NAME = "代理人:";

    /*
     * 使用接口的方式来指向真实角色(多态特性)
     */
    public ProxyStar(StarInterface star) {
        super();
        this.star = star;
    }

    @Override
    public void sing() {
        // 唱歌是明星的特有方法,代理是没有的,因此需要明星自己来
        // 调用陈奕迅的唱歌方法...
        star.sing();
    }

    @Override
    public void finalPayment() {
        System.out.println(PROXY_NAME+"签尾款");
    }

    @Override
    public void signContract() {
        System.out.println(PROXY_NAME+"签合同");
    }

    @Override
    public void plan() {
        System.out.println(PROXY_NAME+"安排行程");
    }

    @Override
    public void interview() {
        System.out.println(PROXY_NAME+"面谈");
    }

    @Override
    public void firstPayment() {
        System.out.println(PROXY_NAME+"预付款");
    }
}
