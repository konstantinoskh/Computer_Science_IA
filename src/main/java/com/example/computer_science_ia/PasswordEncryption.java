package com.example.computer_science_ia;
import org.mindrot.jbcrypt.*;

public class PasswordEncryption {

    //Password Encryption
    public static String encrypt(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    //Password Verification
    public static boolean verify(String plainPassword, String encryptedPassword){
        return BCrypt.checkpw(plainPassword, encryptedPassword);
    }
}
