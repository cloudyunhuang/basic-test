package com.github.tingliu.basic.test;

import org.junit.Test;

/**
 * @HuangY
 * @create 2017-12-19 11:11
 **/
public class PlusTest {

    @Test
    public void test() {
        String[] k = new String[2];
        int i = 0;
        while (i < 2) {
            k[i++] = "d";
            System.out.println(k);
        }
    }

    /**
     * i = i++ 等价于
     * temp = i;
     * i = i + 1;
     * i = temp
     * 总结：i++ 有中间缓存变量
     * <p>
     * //Warning: The assignment to variable j has no effect
     * j = ++j 相当于 j = j = j + 1
     * 所以编译器警告, 语句的赋值没有作用
     * <p>
     * ++i 没有中间缓存变量, 理论上比 i++ 更加高效
     */
    @Test
    public void test1() {
        int i = 0;
        i = i++;
        System.out.println(i);

        int j = 0;
        j = ++j;
        System.out.println(j);

        int k = 0;
        k++;
        System.out.println(k);
    }

    @Test
    public void test2() {
        int i = 0;
        for (int j = 0; j < 10; j++) {
            /**
             temp=i;
             i=temp+1;
             i=temp;
             */
            i = i++;
        }
        System.out.println("i的最后结果" + i);
    }

    @Test
    public void test3() {
        int i = 3;
        int count = (i++) + (i++) + (i++);//count=3+4+5,你可以用count=(i++)+(i++);这时会等于7
        System.out.println(i);//i=6
        System.out.println(count);//count=12
        int j = 3;
        count = (++j) + (++j) + (++j);//count=4+5+6
        System.out.println(j);//j=6
        System.out.println(count);//count=15
    }

    @Test
    public void test4() {
        /**
         * a++ 等价于
         * temp=a // temp =0
         * a=temp+1 // a=1
         *
         * ++a 等价于
         * a=a+1 // a=2
         *
         * 所以 a++ + ++a 的过程：
         * temp=a // temp =0
         * a=temp+1 // a=1
         * a+1   // a=2
         * 最终就如下
         *  temp+a=0+2=2
         */
        int a = 0, b = 0;
        b = a++ + ++a; //0+2=2
        System.out.println("a:" + a);
        System.out.println("b:" + b);

        int a1 = 0, b1 = 0;
        b1 = ++a1 + a1++; //1+1=2
        System.out.println("a1:" + a1);
        System.out.println("b1:" + b1);
    }

    @Test
    public void test5() {
        /**
         *  c=0
         *  temp=c // temp =0
         *  c=c+1 // c=1
         *  c=c+1 // c=2
         *
         *  temp+c=0-2=-2
         */
        int c = 0, d = 0;
        d = c++ - ++c; //0+2=2
        System.out.println("d:" + d);

        int i = 0;
        i = i++ + ++i; //0+1+1=2
        int j = 0;
        j = ++j + j++ + j++ + j++;// 1+1+2+3=7
        int k = 0;
        k = k++ + k++ + ++k;// 0+1+2+1=4
        int h = 0;
        h = ++h + ++h; // 1+2=3
        int p1 = 0, p2 = 0, q1 = 0, q2 = 0;
        q1 = ++p1;// p1=1 q1=1
        q2 = p2++; // p2=1 q2=0
        System.out.println("i:" + i);
        System.out.println("j:" + j);
        System.out.println("k:" + k);
        System.out.println("h:" + h);
        System.out.println("p1:" + p1);
        System.out.println("p2:" + p2);
        System.out.println("q1:" + q1);
        System.out.println("q2:" + q2);
    }
}
