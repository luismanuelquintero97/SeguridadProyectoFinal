package app;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Cifrador {
	    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
	    private static final int ITERATIONS = 10000;
	    // salt size at least 32 byte
	    private static final int SALT_SIZE = 32;
	    private static final int HASH_SIZE = 512;
	    
	    
	    
	    Cifrador() {
	    	
	    	
	    }
	    
	    // metodo que crea los has 
	    public static void hash(String contrasena) {
	        char[] password = contrasena.toCharArray();

	        try {
	            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
	            byte[] salt = generateSalt();

	            byte[] hash = calculateHash(skf, password, salt);
	            boolean correct = verifyPassword(skf, hash, password, salt);

	            System.out.println("Entered password is correct: "+ correct);
	        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
	        	System.out.println(ex.getMessage());
	        }
	    }

	    private static byte[] generateSalt() {
	        SecureRandom random = new SecureRandom();
	        byte[] salt = new byte[SALT_SIZE];
	        random.nextBytes(salt);

	        return salt;
	    }

	    private static byte[] calculateHash(SecretKeyFactory skf, char[] password, byte[] salt) throws InvalidKeySpecException {
	        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_SIZE);

	        return skf.generateSecret(spec).getEncoded();
	    }

	    private static boolean verifyPassword(SecretKeyFactory skf, byte[] originalHash, char[] password, byte[] salt) throws
	            InvalidKeySpecException {
	        byte[] comparisonHash = calculateHash(skf, password, salt);

	        return comparePasswords(originalHash, comparisonHash);
	    }

	    /**
	     * Compares the two byte arrays in length-constant time using XOR.
	     *
	     * @param originalHash   The original password hash
	     * @param comparisonHash The comparison password hash
	     * @return True if both match, false otherwise
	     */
	    private static boolean comparePasswords(byte[] originalHash, byte[] comparisonHash) {
	        int diff = originalHash.length ^ comparisonHash.length;
	        for (int i = 0; i < originalHash.length && i < comparisonHash.length; i++) {
	            diff |= originalHash[i] ^ comparisonHash[i];
	        }

	        return diff == 0;
	    }
}
