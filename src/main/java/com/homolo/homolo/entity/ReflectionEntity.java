package com.homolo.homolo.entity;

import lombok.Data;

/**
 * 反射实验类.
 */
@Data
public class ReflectionEntity {

    private String name;
    public String password;
    public static String key = "BOOT";
    public static final String keyF = "SPRING";
    private static String lin = "YES";
    private static final String linF= "MOON";

    public void testpublic() {
        System.out.println("公有方法test----");
    }
    public static void testpublicstatic() {
        System.out.println("公有静态方法test----");
    }

    private void test() {
        System.out.println("私有方法test----");
    }

    private String test(String name, String don) {
        System.out.println("带参数有返回私有方法test----" + name + ":" + don);
        return name+don;
    }

    public ReflectionEntity(String name, String password) {
        this.name = name;
        this.password = password;
        System.out.println("公共构造方法----");
    }
    private ReflectionEntity(String name) {
        this.name = name;
        System.out.println("私有构造方法----");
    }

}
