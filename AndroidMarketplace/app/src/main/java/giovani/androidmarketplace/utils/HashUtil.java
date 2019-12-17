package giovani.androidmarketplace.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    private HashUtil() {

    }

    public static byte[] aplicarMD5(byte[] dados) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        return digest.digest(dados);
    }
}
