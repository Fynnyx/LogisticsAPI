package ch.fwesterath.logisticsapi.auth.apikey;

import java.security.SecureRandom;
import java.util.Base64;

public class ApiKeyGenerator {
    private static final int KEY_LENGTH = 32;

    public static String generateApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[KEY_LENGTH];
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
}
