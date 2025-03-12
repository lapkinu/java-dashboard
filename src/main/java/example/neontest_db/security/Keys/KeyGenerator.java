package example.neontest_db.security.Keys;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.security.Key;
import io.jsonwebtoken.SignatureAlgorithm;

public class KeyGenerator {

    public static void main(String[] args) {
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Encoded Secret Key: " + encodedKey);
    }
}