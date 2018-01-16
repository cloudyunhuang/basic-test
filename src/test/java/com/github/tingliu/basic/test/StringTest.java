package com.github.tingliu.basic.test;

import org.junit.Test;

/**
 * @author:TingLiu
 * @create 2018-01-13 17:13
 **/
public class StringTest {

    @Test
    public void test0(){
        String s1="ab";
        String s2 = new String("ab");
        System.out.println("s1:"+s1);
        System.out.println("s2:"+s2);
        System.out.println("s1==s2:"+s1.equals(s2));
    }
    @Test
    public void test1(){
        System.out.println(Integer.toHexString(240));
        System.out.println(240&15);
        int radix = 1 << 1;
        System.out.println(radix);
        int radix1 = 10 >> 1;
        System.out.println(radix1);
        int radix2 =240;
        radix2 >>= 4;
        System.out.println(radix2);
        System.out.println(Integer.toBinaryString(240));

    }
    @Test
    public void test2(){
//        System.out.println(10+"");
        System.out.println(Integer.toString(65537));
    }
}
