package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author harol
 */
public class PasswordUtil {

    public static String hashPasword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPasword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public static void main(String[] args) {
        String clave1 = "1234";
        String clave2 = "1234";

        String resultado1 = PasswordUtil.hashPasword(clave1);
        String resultado2 = PasswordUtil.hashPasword(clave2);

        System.out.println(resultado1);
        System.out.println(resultado2);

        System.out.println("-------------------------------");

        boolean b = checkPasword("1234", resultado2);
        System.out.println(resultado1);
        System.out.println(b);

    }
}
