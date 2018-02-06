package com.github.tingliu.basic.test;


import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author:HuangY
 * @create 2018-01-31 17:53
 **/
public class Map2Test {

    @Test
    public void althashingTest() {
//        System.setProperty("jdk.map.althashing.threshold","5");
        Map<String, String> m = new HashMap<>(6);
        m.put("h", "h");
    }

    @Test
    public void initHashSeedAsNeededTest() {
        System.out.println(initHashSeedAsNeeded(4, 0, 5));
        System.out.println(initHashSeedAsNeeded(6, 0, 5));
        System.out.println(initHashSeedAsNeeded(4, 2, 5));
        System.out.println(initHashSeedAsNeeded(6, 2, 5));

    }

    final boolean initHashSeedAsNeeded(int capacity, int hashSeed, int threhold) {
        boolean currentAltHashing = hashSeed != 0;
        boolean useAltHashing = sun.misc.VM.isBooted() &&
                (capacity >= threhold);
        boolean switching = currentAltHashing ^ useAltHashing;
        if (switching) {
            hashSeed = useAltHashing
                    ? sun.misc.Hashing.randomHashSeed(this)
                    : 0;
        }
        return switching;
    }

    @Test
    public void test() {
        int cnt = 1000000;
        int lenght = Integer.highestOneBit((cnt - 1) << 1);
        int lenghtToCal = lenght - 1;
        System.out.println(Integer.toBinaryString(lenghtToCal));
        int hashSeed = 0;
        int randHashSeed = sun.misc.Hashing.randomHashSeed(this);
        Set<Integer> hashSet = new HashSet<>(cnt);
        Set<Integer> hashSet2 = new HashSet<>(cnt);
        for (int i = 0; i < cnt; i++) {
//            KeyObject key = new KeyObject();
            String key = i + "qwertyu";
            hashSet.add(hash(key, hashSeed) & lenghtToCal);
            hashSet2.add(hash(key, randHashSeed) & lenghtToCal);
        }
        System.out.println(cnt + " hashSeed=0 碰撞数" + (cnt - hashSet.size()));
        System.out.println(cnt + " hashSeed=" + randHashSeed + " 碰撞数" + (cnt - hashSet2.size()));
    }


    static int hash(Object k, int hashSeed) {
        int h = hashSeed;
        if (0 != h && k instanceof String) {
            return sun.misc.Hashing.stringHash32((String) k);
        }
        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private class KeyObject {

    }

}
