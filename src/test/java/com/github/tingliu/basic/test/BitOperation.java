package com.github.tingliu.basic.test;

import org.junit.Test;

/**
 * @author:HuangY
 * @create 2018-02-07 15:37
 **/
public class BitOperation {

    @Test
    public void bitAndTest() {
        System.out.println(-1 & 1);
        System.out.println(0 & 1);
        System.out.println(1 & 1);
    }

    @Test
    public void bitAndTest2() {
        int k = 4;
        System.out.println(k & (k - 1));
    }
}
