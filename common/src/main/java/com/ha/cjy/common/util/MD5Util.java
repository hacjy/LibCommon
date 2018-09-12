package com.ha.cjy.common.util;

import java.security.MessageDigest;

/**
 * Created by cjy on 2018/7/23.
 */

public class MD5Util {
    public MD5Util() {
    }

    public static final String MD5(String s) {
        if(s == null) {
            return null;
        } else {
            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

            try {
                byte[] btInput = s.getBytes();
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                mdInst.update(btInput);
                byte[] md = mdInst.digest();
                int j = md.length;
                char[] str = new char[j * 2];
                int k = 0;

                for(int i = 0; i < j; ++i) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 15];
                    str[k++] = hexDigits[byte0 & 15];
                }

                return new String(str);
            } catch (Exception var10) {
                var10.printStackTrace();
                return null;
            }
        }
    }
}
