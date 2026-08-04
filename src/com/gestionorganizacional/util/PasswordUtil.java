package com.gestionorganizacional.util;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtil {
    private static final int LONGITUD_BITS = 256;

    private PasswordUtil() {}

    public static boolean verificar(char[] clave, String saltBase64,
            String hashBase64, int iteraciones) throws GeneralSecurityException {
        byte[] salt = Base64.getDecoder().decode(saltBase64);
        byte[] esperado = Base64.getDecoder().decode(hashBase64);
        PBEKeySpec especificacion = new PBEKeySpec(clave, salt, iteraciones, LONGITUD_BITS);
        try {
            byte[] calculado = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(especificacion).getEncoded();
            return MessageDigest.isEqual(esperado, calculado);
        } finally {
            especificacion.clearPassword();
        }
    }
}

