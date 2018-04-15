package com.swapnopuri.shopping.cart.my.tools;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;

/**
 * Created by JEWEL on 7/26/2016.
 */
public class EncryptionRSA {
    Cipher c;
    // Generate key pair for 1024-bit RSA encryption and decryption
    Key publicKey = null;
    Key privateKey = null;

    public EncryptionRSA() {
        try {
            c = Cipher.getInstance("RSA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyGenerator();
    }

    private void keyGenerator () {
        try{
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        }catch (Exception e){

        }

    }

    public byte[] encrypt(String data)  {

        try{
            c.init(Cipher.ENCRYPT_MODE, privateKey);
           return c.doFinal(data.getBytes());

        }catch (Exception e){
            MyLog.e("ENC","Enc :"+e.toString());
        }
    return null;
    }

    public String decode(byte[] encodedBytes) {
        try {
            c.init(Cipher.DECRYPT_MODE, publicKey);

            return new String(c.doFinal(encodedBytes));
        } catch (Exception e) {

        }
        return "";
    }
}
