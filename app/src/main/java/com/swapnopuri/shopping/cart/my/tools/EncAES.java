package com.swapnopuri.shopping.cart.my.tools;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by JEWEL on 7/27/2016.
 */
public class EncAES {
    // password for encryption and decryption
    byte[] iv = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    Cipher cipher;
    IvParameterSpec ivS;
    SecretKeySpec skeySpec;
    public EncAES(){
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ivS=new IvParameterSpec(iv);
            skeySpec = new SecretKeySpec(iv, "AES");
        } catch (NoSuchAlgorithmException ex) {
        } catch (NoSuchPaddingException ex) {
        }
    }





    public  byte[] encrypt( byte[] data)  {

        byte[] encrypted=null;

        try {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec,ivS);
            encrypted = cipher.doFinal(data);
        } catch (Exception ex) {

        }
        return encrypted;

    }
    public byte[] decrypt(byte[] encrypted)  {

        byte[] decrypted=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec,ivS);
            decrypted = cipher.doFinal(encrypted);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return decrypted;

    }
}
