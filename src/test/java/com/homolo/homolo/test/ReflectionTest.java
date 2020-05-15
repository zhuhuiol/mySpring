package com.homolo.homolo.test;

import com.homolo.homolo.entity.ReflectionEntity;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 关于反射的那些事...
 */
public class ReflectionTest {

    @Test
    public void test() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class clazz = ReflectionEntity.class;
        System.out.println(clazz.getName());
        Class aClass = Class.forName("com.homolo.homolo.entity.ReflectionEntity");
        System.out.println(aClass.getSuperclass().getName());
        //获取字段
        Field[] declaredFields = clazz.getDeclaredFields();
        Arrays.asList(declaredFields).forEach(x -> {
            System.out.println("field:" + x.getName());
        });
        //获取带这种类型参数的public构造方法
        Constructor constructor = clazz.getConstructor(String.class, String.class);
        //获取参数类型
        System.out.println(Modifier.toString(constructor.getModifiers()) + "参数:");
        Class[] parameterTypes = constructor.getParameterTypes();
        for (int j = 0; j < parameterTypes.length; j++) {
            System.out.println(parameterTypes[j].getName() + " ");
        }
        //执行构造方法，返回对象
//        constructor.setAccessible(true); //调用私有的设置下
        Object o = constructor.newInstance("qwe", "eee");
        System.out.println(" result:" + o);
        //调用私有方法，并且返回值
        Method test = clazz.getDeclaredMethod("test", String.class, String.class);
        test.setAccessible(true);
        Object testResult = test.invoke(o, "德玛", "西亚");
        System.out.println("test method result:" + testResult);
        //调用静态字段，获取值
        Field linF = clazz.getDeclaredField("linF");
        linF.setAccessible(true);
        System.out.println("test field static result:" + linF.get(o));
        //返回对象
        ReflectionEntity o1 = (ReflectionEntity) o;
        System.out.println("entity getName:" + o1.getName());

    }

    /**
     * jvm -- intern(). 将首次遇到的字符串复制到常量池中，7以上则是记录引用.
     */
    @Test
    public void test2() {
        /**
         * jdk6中：两个false
         * jdk7以上；则是一个true一个false
         * jdk6是因为 intern方法会将首次遇到的字符串复制到常量池中,
         * 所以str1存在常量池中，而后者实例存在堆中，地址引用不一样。
         * jdk7中 intern方法不再复制实例，而是在常量池中记录首次出现的实例引用,
         * 因此intern返回的引用和由StringBuilder创建的字符串实例是同一个，所以返回true，
         * 但是 “java” 字符串在StringBuilder.toStirng()之前出现过，常量池已经有他的引用了
         * 而new的却是一个新的对象，返回false.
         *
         */
        String str1 = new StringBuilder("计算机").append("技术").toString();
        System.out.println(str1.intern() == str1);
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

    @Test
    public void test3() throws BrokenBarrierException, InterruptedException {


    }


    /**
     * 垃圾收集机制.
     * 在当对象没有引用时，垃圾收集时 触发finalize方法，是否与外界进行引用，只触发一次。
     */
    public static class FinalizeEscapeCG {
        public static FinalizeEscapeCG SAVE_HOOk = null;

        public void isAlive() {
            System.out.println("爸爸还在！");
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("finalize executed!");
            FinalizeEscapeCG.SAVE_HOOk = this;
        }

        public static void main(String[] args) throws InterruptedException {
            SAVE_HOOk = new FinalizeEscapeCG();
            //第一次自我救赎
            SAVE_HOOk = null;
            System.gc();
            //finalize优先级低，等待会
            Thread.sleep(500);
            if (SAVE_HOOk != null) {
                SAVE_HOOk.isAlive();
            } else {
                System.out.println("爸爸不在了！");
            }

            SAVE_HOOk = null;
            System.gc();
            //finalize优先级低，等待会
            Thread.sleep(500);
            if (SAVE_HOOk != null) {
                SAVE_HOOk.isAlive();
            } else {
                System.out.println("爸爸不在了！");
            }
        }
    }


    /**
     * 类加载器测试.
     * 由两个类加载器加载同一个class文件， instanceof关键字检测对象类型。
     * --比较两个类是否“相等”，只有在这两个类是由同一个类加载器加载的前提下才
     * 意义 则，即使这两个类来源于同 Class 件，被同 个虚拟机加载，只 加载它们
     * 的类加载器不同，那这两个类就必定不相等。
     * --这里指的相等 包括代表类的Class对象的equals方法，isAssignableFrom()方法，
     * inIstance()方法返回的结果，也包括instanceof关键字的判断。如果不注意类加载器的影响，
     * 在某些情况下会产生迷惑性的结果.
     */
    public static class ClassLoaderTest {
        public static void main(String[] args) throws Exception {
            ClassLoader myClassLoader = new ClassLoader() {
                @Override
                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    try {
                        String fileName  = name.substring(name.lastIndexOf(".") + 1) + ".class";
                        InputStream is= getClass().getResourceAsStream(fileName);
                        if (is == null) {
                            return super.loadClass(name);
                        }
                        byte[] b = new byte[is.available()];
                        is.read(b);
                        return defineClass(name, b, 0, b.length);
                    } catch (IOException e) {
                       throw new ClassNotFoundException(name);
                    }
                }
            };
            Object obj = myClassLoader.loadClass("com.homolo.homolo.test.ReflectionTest").newInstance();
            System.out.println(obj.getClass());
            System.out.println(obj instanceof ReflectionTest);

        }
    }


    /**
     * Volatile 变量并发下并不安全，只是保证他的可见性，进行原子性操作还需要synchronized关键字 或者 原子类进行操作.
     * CyclicBarrier 和 countDownLatch 给初始值不一样,控制所有子线程结束。
     * countDownLatch和cyclicBarrier有什么区别呢，
     * 他们的区别：countDownLatch只能使用一次，而CyclicBarrier方法可以使用reset()方法重置，
     * 所以CyclicBarrier方法可以能处理更为复杂的业务场景。
     */
    public static class VolatileTest{
        public static VolatileTest volatileTest;
        public static VolatileTest getEntity() {
            if (volatileTest == null) {
                volatileTest = new VolatileTest();
            }
            return volatileTest;
        }
        private static final int RACE_TH_NUM = 20; //线程数量
        public static int race = 0;  //变量初始值
        public void raceAdd() {         //变量累加方法
            synchronized(this) {
                race++;
            }
        }
        public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
            Thread [] threads = new Thread[RACE_TH_NUM];
//            CyclicBarrier cyclicBarrier = new CyclicBarrier(RACE_TH_NUM + 1);
            for (int i = 0; i < RACE_TH_NUM; i++) {
                threads[i] = new Thread(new Runnable() {

                    @SneakyThrows
                    @Override
                    public void run() {
                        for(int j = 0; j < 10000; j++) {
                            VolatileTest.getEntity().raceAdd();
                        }
//                        System.out.println("code:" + race);
//                        cyclicBarrier.await();
                    }
                });
                threads[i].start();
                threads[i].join();
            }
            //等待所有线程结束
//            cyclicBarrier.await();
            System.out.println(race);
        }
    }


}
