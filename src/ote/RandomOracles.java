
package ote;

import ote.ByteUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RandomOracles {

	// Method to create  Random Oracle G
	
    public static byte[] G (int offset,byte[] key) throws NoSuchAlgorithmException{
	
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(key); 
		byte[] encrypted = md.digest();
		return ByteUtils.subbytes(encrypted, 0, offset);
		
 }
    
    // Method to create Random Oracle H 
    
    public static int H (byte[] convertMe) throws NoSuchAlgorithmException {
    	
    	MessageDigest md = MessageDigest.getInstance("SHA-256");
    	md.update(convertMe); 
		byte[] encrypted = md.digest();
        return getBit(encrypted,0);
    }
    
    // Method to read the value of a bit (0 or 1) of a byte array
    
    private static int getBit(byte[] data, int pos) {
	      int posByte = pos/8; 
	      int posBit = pos%8;
	      byte valByte = data[posByte];
	      int valInt = valByte>>(8-(posBit+1)) & 0x0001;
	      return valInt;
	   }
}