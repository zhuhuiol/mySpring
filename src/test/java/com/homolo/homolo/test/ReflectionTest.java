package com.homolo.homolo.test;

import com.homolo.homolo.entity.ReflectionEntity;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.Arrays;

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
        ReflectionEntity o1 = (ReflectionEntity) o;
        System.out.println("entity getName:" + o1.getName());

    }
}
