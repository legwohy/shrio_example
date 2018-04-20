package com.shiro.test;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;

import java.util.Random;

/**
 * 编码与解码
 */
public class Encode {
    /**
     * 散列算法
     */
    @Test public void testHash(){
       String txt = "abc";
       String salt = String.valueOf(new Random().nextInt());

       // md5
       String md5 = new Md5Hash(txt,salt).toString();
       String md5Base = new Md5Hash(txt,salt).toBase64();
       String md5Hex = new Md5Hash(txt,salt).toHex();

       // sha265
        String sha265 = new Sha256Hash(txt,salt).toString();

       System.out.println("md5="+md5);
       System.out.println("md5Base="+md5Base);
       System.out.println("md5Hex="+md5Hex);
       System.out.println("sha265="+sha265);

    }
}
