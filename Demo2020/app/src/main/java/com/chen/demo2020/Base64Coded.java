package com.chen.demo2020;

import android.util.Base64;

public class Base64Coded {

    public void test() {
        String xx = "ABC";
        String b64 = encode(xx.getBytes());
        System.out.println(b64);
        String b641 = decode(b64.getBytes());
        System.out.println(b641);
    }

    //base64 解码
    public static String decode(byte[] bytes) {
        return new String(Base64.decode(bytes, Base64.DEFAULT));
    }

    //base64 编码
    public static String encode(byte[] bytes) {
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }
}
