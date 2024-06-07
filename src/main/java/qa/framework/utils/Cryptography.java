package qa.framework.utils;

import java.util.Base64;

public class Cryptography {
	
	/**
	 * Base64 Encoding
	 * 
	 * @author sanjay
	 * @param originalInput
	 * @return
	 */

	public static String encodeBase64(String originalInput) {
		
		return Base64.getEncoder().encodeToString(originalInput.getBytes());
		
	}
	
	
	/**
	 * Base64 Decoding
	 * 
	 * @author sanjay
	 * @param encodedString
	 * @return
	 */
	
	public static String decodeBase64(String encodedString) {
		
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		return new String(decodedBytes);
		
	}
}
