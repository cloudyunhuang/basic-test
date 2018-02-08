package com.github.tingliu.basic.test;

import io.netty.util.internal.ReflectionUtil;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author:HuangY
 * @create 2018-02-08 10:33
 **/
public class UnsafeTest {

    public static Unsafe getUnsafe() {
        final Object maybeUnsafe = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                    Throwable cause = ReflectionUtil.trySetAccessible(unsafeField);
                    if (cause != null) {
                        return cause;
                    }
                    // the unsafe instance
                    return unsafeField.get(null);
                } catch (NoSuchFieldException e) {
                    return e;
                } catch (SecurityException e) {
                    return e;
                } catch (IllegalAccessException e) {
                    return e;
                }
            }
        });
        return (Unsafe) maybeUnsafe;
    }

    @Test
    public void test() throws NoSuchFieldException, InstantiationException {
        Unsafe unsafe = getUnsafe();

        UnsafeObject unSafeObject = new UnsafeObject();
        long fdStrOffset = unsafe.objectFieldOffset(UnsafeObject.class.getDeclaredField("fdStr"));
        unsafe.putOrderedObject(unSafeObject, fdStrOffset, "fdStrValue");
        System.out.println("fdStr = " + unsafe.getObject(unSafeObject, fdStrOffset));
//        unsafe.putObject();

        long fd_byteOffset = unsafe.objectFieldOffset(UnsafeObject.class.getDeclaredField("fd_byte"));
        unsafe.putByte(unSafeObject, fd_byteOffset, (byte) 32);
        System.out.println("fd_byte = " + unsafe.getByte(unSafeObject, fd_byteOffset));

        long fd_charOffset = unsafe.objectFieldOffset(UnsafeObject.class.getDeclaredField("fd_char"));
        unsafe.putChar(unSafeObject, fd_charOffset, 'A');
        System.out.println("fd_char = " + unsafe.getShort(unSafeObject, fd_charOffset));

        long fd_shortOffset = unsafe.objectFieldOffset(UnsafeObject.class.getDeclaredField("fd_short"));
        unsafe.putShort(unSafeObject, fd_shortOffset, (short) 5);
        System.out.println("fd_short = " + unsafe.getShort(unSafeObject, fd_shortOffset));

        long fd_intOffset = unsafe.objectFieldOffset(UnsafeObject.class.getDeclaredField("fd_int"));
        unsafe.putInt(unSafeObject, fd_intOffset, 55555);
        System.out.println("fd_int = " + unsafe.getInt(unSafeObject, fd_intOffset));

        long fd_longOffset = unsafe.objectFieldOffset(UnsafeObject.class.getDeclaredField("fd_long"));
        unsafe.putLong(unSafeObject, fd_longOffset, 12345678901234L);
        System.out.println("fd_long = " + unsafe.getLong(unSafeObject, fd_longOffset));

        long fd_floatOffset = unsafe.objectFieldOffset(UnsafeObject.class.getDeclaredField("fd_float"));
        unsafe.putFloat(unSafeObject, fd_floatOffset, 1333333.5f);
        System.out.println("fd_float = " + unsafe.getFloat(unSafeObject, fd_floatOffset));

        long fd_doubleOffset = unsafe.objectFieldOffset(UnsafeObject.class.getDeclaredField("fd_double"));
        unsafe.putDouble(unSafeObject, fd_doubleOffset, 52533566.555d);
        System.out.println("fd_double = " + unsafe.getDouble(unSafeObject, fd_doubleOffset));

        long fd_booleanOffset = unsafe.objectFieldOffset(UnsafeObject.class.getDeclaredField("fd_boolean"));
        unsafe.putBoolean(unSafeObject, fd_booleanOffset, true);
        System.out.println("fd_boolean = " + unsafe.getBoolean(unSafeObject, fd_booleanOffset));


    }

    @Test
    public void test2() throws InterruptedException {
        Unsafe unsafe = getUnsafe();
        long size = 5;
        long address = unsafe.allocateMemory(size); // 申请内存
        System.out.println(address);

        unsafe.setMemory(address, size, (byte) -1);// 设置内存
        System.out.println(unsafe.getByte(address)); // 根据地址获取数据
        for (int i = 0; i < size; i++) {
            unsafe.putByte(address + i, (byte) i);
        }
        for (int i = 0; i < size; i++) {
            System.out.println(unsafe.getByte(address + i));
        }
        unsafe.freeMemory(address); // 释放内存
// todo 待写测试例子
//        unsafe.getAddress();
//        unsafe.compareAndSwapInt();
//        unsafe.copyMemory();
//        unsafe.defineAnonymousClass();
//        unsafe.defineClass();
//        unsafe.ensureClassInitialized();
//        unsafe.reallocateMemory();
//        unsafe.shouldBeInitialized();
//        unsafe.staticFieldBase();
//        unsafe.staticFieldOffset();
//        unsafe.unpark();
//        unsafe.ensureClassInitialized();
//        unsafe.monitorEnter();
//        unsafe.monitorExit();
//        unsafe.park();
//        unsafe.putAddress();
//        unsafe.throwException();
//        unsafe.tryMonitorEnter();
//        unsafe.getLoadAverage();

    }

    @Test
    public void test3() {
        Unsafe unsafe = getUnsafe();
        System.out.println("addressSize = " + unsafe.addressSize());
        System.out.println("pageSize = " + unsafe.pageSize());
        try {
            UnsafeObject unsafeObject = (UnsafeObject) unsafe.allocateInstance(UnsafeObject.class);
            System.out.println("allocateInstance = " + unsafeObject);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test4() {
        Unsafe unsafe = getUnsafe();
        Class uoArrClass = UnsafeObject[].class;
        int uoBase = unsafe.arrayBaseOffset(uoArrClass);//  UnsafeObject[]数组基本偏移量
        int uoIndexScale = unsafe.arrayIndexScale(uoArrClass);//  UnsafeObject[]数组单位偏移量，比如：数组[2]的偏移量=基本偏移量+2*单元偏移量
        System.out.println("uoBase = " + uoBase);
        System.out.println("uoIndexScale = " + uoIndexScale);
        int arrSize = 8;
        int uoShift = 31 - Integer.numberOfLeadingZeros(uoIndexScale);

        UnsafeObject[] uoArr = new UnsafeObject[arrSize];
        int index = 3; // 数组索引
        long u = (index << uoShift) + uoBase;// 偏移量
        UnsafeObject unsafeObject = new UnsafeObject();
//        System.out.println("unsafeObject = " + unsafeObject);
        unsafe.compareAndSwapObject(uoArr, u, null, unsafeObject); // 把数据存放到数组索引=3 中，相当于uoArr[3]=unsafeObject;compareAndSwapObject 是原子操作
        Object object = unsafe.getObjectVolatile(uoArr, u);
        Object object2 = uoArr[index];
//        System.out.println("object = " + object);
//        System.out.println("object2 = " + object2);
        System.out.println("unsafeObject.equals(object) = " + unsafeObject.equals(object));
        System.out.println("unsafeObject.equals(object2) = " + unsafeObject.equals(object2));
    }


    private class UnsafeObject {
        private String fdStr;
        private byte fd_byte;
        private char fd_char;
        private short fd_short;
        private int fd_int;
        private long fd_long;
        private float fd_float;
        private double fd_double;
        private boolean fd_boolean;
    }
}
