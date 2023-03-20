package ods;


import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class Encryption {

    private static byte[] shared = {0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11};

  	static String  key = "spiceussd"; 

    public static  String EncryptPass(String strRaw) {

        String strEnc = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        byte[] toEncryptArray = null;

        try {

            toEncryptArray = strRaw.getBytes("UTF-8");
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));

            if (temporaryKey.length < 24) {

                int index = 0;
                for (int i = temporaryKey.length; i < 24; i++) {
                    keyArray[i] = temporaryKey[index];
                }
            }

            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(shared));
            byte[] encrypted = c.doFinal(toEncryptArray);
            strEnc = new String(Base64.encodeBase64(encrypted));

        } catch (Exception e) {
            e.getMessage();
		e.printStackTrace();
        }

        return strEnc;

    }

 

    public static String DecryptPass(String strEnc) {

        String strRaw = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        try {

            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));
            if (temporaryKey.length < 24) {
                int index = 0;
                for (int i = temporaryKey.length; i < 24; i++) {
                    keyArray[i] = temporaryKey[index];
                }
            }

            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(shared));
            byte[] decrypted = c.doFinal(Base64.decodeBase64(strEnc.getBytes()));
            strRaw = new String(decrypted, "UTF-8");

        } catch (Exception e) {
            e.getMessage();
		e.printStackTrace();
        }
        return strRaw;
    }

}
