package com.proxy;

public class Client {
	  public static void main(String[] args) {
	        //找到陈奕迅
	        EasonStar realStar = new EasonStar();
	        //找到代理人,专门为陈奕迅代理
	        ProxyStar proxyStar = new ProxyStar(realStar);
	        //代理人来完成
	        proxyStar.interview();
	        proxyStar.signContract();
	        proxyStar.firstPayment();
	        proxyStar.plan();
	        //这里调用的是 EasonStar的sing方法
	        proxyStar.sing();
	        proxyStar.finalPayment();
	    }
}
