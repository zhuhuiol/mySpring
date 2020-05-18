package com.homolo.homolo.ThreadTest;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.scheduling.annotation.Async;

/**
 * wait（使线程等待）,notify（唤醒线程）,join（将子线程加入主线程，等待子线程执行完毕） ,yield（让其他线程执行，重新抢）
 * 测试wait 和 notify的用处.
 * https://blog.csdn.net/u010002184/article/details/82893175
 * https://blog.csdn.net/jiangbr/article/details/79337573
 */
public class ThreadWaitTest {

    static Object o = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new one();
        Thread t2 = new owt();
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

    public static class one extends Thread{
        @Override
        public void run() {
            synchronized (o) {
                System.out.println("T1开始...");
                System.out.println("T1等待...");
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1结束...");
            }
        }
    }

    public static class owt extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            synchronized (o) {
                System.out.println("T2开始...");
                System.out.println("开始唤醒一个线程...");
                o.notify();
                System.out.println("T2结束...");
            }
        }
    }

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            print("test-" + i);
        }
        System.out.println("结束循环！");
    }

    @Async
    public void print(String val) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("val:" + val);
    }
}
