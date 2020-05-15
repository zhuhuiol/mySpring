package com.homolo.homolo.ThreadTest;

/**
 * 测试wait 和 notify的用处.
 * https://blog.csdn.net/u010002184/article/details/82893175
 */
public class ThreadWaitTest {

    static Object o = new Object();
    public static void main(String[] args) throws InterruptedException {
        synchronized (o) {
            System.out.println("start...");
            o.wait();
            System.out.println("wait...");
            o.notify();
            System.out.println("notify...");
        }

    }
}
