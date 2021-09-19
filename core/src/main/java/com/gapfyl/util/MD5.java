package com.gapfyl.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author vignesh
 * Created on 13/04/21
 **/

public class MD5 {
    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(str.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            String hashNext = no.toString(16);
            while (hashNext.length() < 32) {
                hashNext = "0" + hashNext;
            }
            return hashNext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
