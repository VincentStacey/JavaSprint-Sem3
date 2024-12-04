package src.main.java;

import org.mindrot.jbcrypt.BCrypt;

public class MyBCrypt {

    public static String hashpw(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkpw(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
