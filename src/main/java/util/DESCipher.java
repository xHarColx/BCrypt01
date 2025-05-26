package util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;

public class DESCipher {

    public static String cifrar(String texto, String clave) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(clave.getBytes()));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosCifrados = cipher.doFinal(texto.getBytes("UTF8"));
        return Base64.getEncoder().encodeToString(datosCifrados);
    }

    public static String descifrar(String textoCifrado, String clave) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(clave.getBytes()));
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] datosDescifrados = cipher.doFinal(Base64.getDecoder().decode(textoCifrado));
        return new String(datosDescifrados, "UTF8");
    }
}
