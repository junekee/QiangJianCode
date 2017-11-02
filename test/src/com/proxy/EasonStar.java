package com.proxy;

/*
 * 明星实现类
 */
public class EasonStar implements StarInterface {

    private final String STAR_NAME = "陈奕迅:";

    @Override
    public void sing() {
        System.out.println(STAR_NAME+"唱歌");
    }

    @Override
    public void finalPayment() {
        System.out.println(STAR_NAME+"收尾款");
    }

    @Override
    public void signContract() {
        System.out.println(STAR_NAME+"签合同");
    }

    @Override
    public void plan() {
        System.out.println(STAR_NAME+"安排行程");
    }

    @Override
    public void interview() {
        System.out.println(STAR_NAME+"面谈");

    }

    @Override
    public void firstPayment() {
        System.out.println(STAR_NAME+"预付款");
    }
}