package com.gjz.util;

import org.apache.commons.codec.digest.DigestUtils;

public class SHA1Utils {
    public static String code(String str) {
        return DigestUtils.sha1Hex(str);
    }

    public static void main(String[] args) {
        String str = "123456";
        System.out.println(code(str));
    }
}
