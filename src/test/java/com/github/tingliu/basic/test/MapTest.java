package com.github.tingliu.basic.test;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author:HuangY
 * @create 2018-01-18 15:54
 **/
public class MapTest {


    @Test
    public void getTest() throws ExecutionException, InterruptedException {
        int nThread = 32;
        int mapSize = 100 * 1000;
        Map<String, String> hashMap = new HashMap<>();
        GetTest hashGetTest = new GetTest(hashMap, nThread, mapSize);
        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        GetTest concurrentGetTest = new GetTest(concurrentHashMap, nThread, mapSize);
        hashGetTest.test();
        concurrentGetTest.test();
    }

    class GetTest {
        private Map<String, String> hashMap;
        private int nThread;
        private final CountDownLatch countDownLatch;
        private AtomicLong atomicLong = new AtomicLong(0);
        private int mapSize;

        public GetTest(Map<String, String> hashMap, int nThread, int mapSize) {
            this.hashMap = hashMap;
            this.nThread = nThread;
            this.countDownLatch = new CountDownLatch(nThread);
            this.mapSize = mapSize;
        }

        public void test() throws ExecutionException, InterruptedException {
            String[] keyArr = new String[mapSize];
            for (int i = 0; i < mapSize; i++) {
                keyArr[i] = String.valueOf(i);
            }
            for (String k : keyArr) {
                hashMap.put(k, k);
            }
            ExecutorService executorService = Executors.newCachedThreadPool();
            List<Future<?>> futures = new ArrayList<>();

            long s = System.currentTimeMillis();
            for (int i = 0; i < nThread; i++) {
                Future<?> future = executorService.submit(new Task(hashMap, keyArr, countDownLatch, atomicLong));
                futures.add(future);
                countDownLatch.countDown();
            }
            for (Future<?> f : futures) {
                f.get();
            }
            long s1 = System.currentTimeMillis();
            System.out.println(hashMap.getClass().getName() + "=" + (s1 - s) + ",atomicLong=" + atomicLong.get() / 1000000);
            executorService.shutdownNow();
        }
    }

    class Task implements Runnable {
        private Map map;
        private String[] keyArr;
        private CountDownLatch countDownLatch;
        private AtomicLong atomicLong;


        public Task(Map map, String[] keyArr, CountDownLatch countDownLatch, AtomicLong atomicLong) {
            this.map = map;
            this.keyArr = keyArr;
            this.countDownLatch = countDownLatch;
            this.atomicLong = atomicLong;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long s = System.nanoTime();
            for (String key : keyArr) {
                map.get(key);
            }
            long s1 = System.nanoTime();
            atomicLong.addAndGet(s1 - s);
        }
    }

    @Test
    public void putTest() {
        HashMapPutTest hashMapPutTest = new HashMapPutTest(1000);
        ConcurrentHashMapPutTest concurrentHashMapPutTest = new ConcurrentHashMapPutTest(1000);
        hashMapPutTest.test();
        concurrentHashMapPutTest.test();

        HashMapPutTest hashMapPutTest1 = new HashMapPutTest(1000);
        ConcurrentHashMapPutTest concurrentHashMapPutTest1 = new ConcurrentHashMapPutTest(1000);
        concurrentHashMapPutTest1.test();
        hashMapPutTest1.test();

        HashMapPutTest hashMapPutTest2 = new HashMapPutTest(10000);
        ConcurrentHashMapPutTest concurrentHashMapPutTest2 = new ConcurrentHashMapPutTest(10000);
        concurrentHashMapPutTest2.test();
        hashMapPutTest2.test();


        HashMapPutTest hashMapPutTest3 = new HashMapPutTest(10000);
        ConcurrentHashMapPutTest concurrentHashMapPutTest3 = new ConcurrentHashMapPutTest(10000);
        hashMapPutTest3.test();
        concurrentHashMapPutTest3.test();

        HashMapPutTest hashMapPutTest4 = new HashMapPutTest(80000);
        ConcurrentHashMapPutTest concurrentHashMapPutTest4 = new ConcurrentHashMapPutTest(80000);
        hashMapPutTest4.test();
        concurrentHashMapPutTest4.test();
    }


    class HashMapPutTest {
        private int cnt = 0;

        public HashMapPutTest(int cnt) {
            this.cnt = cnt;
        }

        public void test() {
            Map<String, String> hashMap = new HashMap<>();
            long s = System.currentTimeMillis();
            for (int i = 0; i < cnt; i++) {
                String iStr = String.valueOf(i);
                hashMap.put("a".concat(iStr), iStr);
            }
            long s1 = System.currentTimeMillis();
            System.out.println("hashMap=" + (s1 - s) + ",cnt=" + cnt);
        }
    }

    class ConcurrentHashMapPutTest {
        private int cnt = 0;

        public ConcurrentHashMapPutTest(int cnt) {
            this.cnt = cnt;
        }

        public void test() {

            Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();
            long s2 = System.currentTimeMillis();
            for (int i = 0; i < cnt; i++) {
                String iStr = String.valueOf(i);
                concurrentHashMap.put("a".concat(iStr), iStr);
            }
            long s3 = System.currentTimeMillis();
            System.out.println("concurrentHashMap=" + (s3 - s2) + ",cnt=" + cnt);
        }
    }


    @Test
    public void initMapTest(){
        Map<String, String> m0 = new HashMap<>(0);// 不创建内部存储数组
        m0.put("k","v");// 内部空间开辟了 1 （数组长度=1）
        Map<String, String> m1 = new HashMap<>(1);// 不创建内部存储数组
        m1.put("k","v");// 内部空间开辟了 1 （数组长度=1）
        Map<String, String> m2 = new HashMap<>(2);// 不创建内部存储数组
        m2.put("k","v");// 内部空间开辟了 2 （数组长度=2）
        Map<String, String> m3 = new HashMap<>(3);// 不创建内部存储数组
        m3.put("k","v");// 内部空间开辟了 4 （数组长度=4）
    }

    @Test
    public void test() {
        Map<String, String> m = new HashMap<>(5);
        m.put("available-replicas", "available-replicas");
        m.put("registered-replicas", "registered-replicas");
        m.put("unavailable-replicas", "unavailable-replicas");

        for (Map.Entry<String, String> entry : m.entrySet()) {
            System.out.println("k=" + entry.getKey() + " ,v=" + entry.getValue());
        }
    }

    @Test
    public void roundUpToPowerOf2Test() {

        int number = 3;
        System.out.println(roundUpToPowerOf2(number));
    }

    public int roundUpToPowerOf2(int number) {
        return number >= 300
                ? 300
                : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }

    @Test
    public void indexFor() {
        int h = 10;
        int length = 1;
        System.out.println(h & (length - 1));
        int k = 6;
        k = k >> 1;
        System.out.println(k);
    }

    @Test
    public void hash(){
        String k ="a";
        int result;
        int h=2;
        int kh =  k.hashCode();
        System.out.println(kh);
        h ^= kh;
        System.out.println(h);
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        System.out.println(h);
        result = h ^ (h >>> 7) ^ (h >>> 4);
        System.out.println(result);
    }
}
