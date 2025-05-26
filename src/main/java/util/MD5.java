/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author harol
 */
public class MD5 {

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String textoplano = "1234";
        String textoCifrado = MD5.getMD5(textoplano);
        System.out.println("texto plano: " + textoplano);
        System.out.println("texto cifrado: " + textoCifrado);

        String textoplano1 = "1234";
        String textoCifrado11 = MD5.getMD5(textoplano1);
        System.out.println("texto plano: " + textoplano);
        System.out.println("texto cifrado: " + textoCifrado);
        String textoPlano = "Hoy te vi por la calle, acompanado, sneeti que mi"
                + "corazon se rompia en mil pedazo";
        String textoCifrado1 = MD5.getMD5(textoPlano);
        System.out.println("///////////////////////////////////////////////");
        System.out.println("texto plano: " + textoPlano);
        System.out.println("texto cifrado: " + textoCifrado1);
    }

}
