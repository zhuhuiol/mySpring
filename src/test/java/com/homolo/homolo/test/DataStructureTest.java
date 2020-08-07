package com.homolo.homolo.test;

import org.junit.Test;

/**
 * --数据结构测试--.
 */
public class DataStructureTest {

    /**
     * 二维数组和稀疏数组.五子棋实例.
     */
    @Test
    public void test1() {
        //创建一个原始的二维数组 11*11
        // 0表示没有棋子，1表示白棋，2表示黑棋
        int chessArr[][] = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        chessArr[4][5] = 2;
        System.out.println("----原始二维数组----");
        for (int row[] : chessArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
        // 将二维数组转稀疏数组
        // 先遍历二维数组  得到非0数据个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] > 0) {
                    sum++;
                }
            }
        }
        //创建对应的稀疏数组
        int sparseArr[][] = new int[sum+1][3];
        // 稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;
        //遍历二维数组，将非0的值放到sparseArr中
        int count = 0; // 就是第几个非0数据
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                }
            }
        }
        System.out.println();
        System.out.println("得到稀疏数组为~~~~");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }
        System.out.println();
        System.out.println("根据稀疏数组还原二维数组~~~~");

    }






















}
