package com.gjz.util;

import org.springframework.util.DigestUtils;

public class MD5Utils {
    public static String code(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static void main(String[] args) {
        String str = "123456";
        System.out.println(code(str));
    }
}
