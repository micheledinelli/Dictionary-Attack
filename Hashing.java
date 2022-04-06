import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    /**
     * 
     * @param plaintext
     * @return digest of the plaintext
     * @throws NoSuchAlgorithmException
     */
    protected String hash(String plaintext) throws NoSuchAlgorithmException {
        
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        
        return convertToHex(md.digest(plaintext.getBytes(StandardCharsets.UTF_8)));
    }

    private String convertToHex(byte[] hash) {
        BigInteger number = new BigInteger(1, hash); 

        // Convert message digest into hex value 
        StringBuilder hexString = new StringBuilder(number.toString(16)); 

        // Pad with leading zeros
        while (hexString.length() < 32) 
        { 
            hexString.insert(0, '0'); 
        } 

        return hexString.toString(); 
    }
}
