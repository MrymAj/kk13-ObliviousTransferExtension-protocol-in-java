package utils.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class implements the ElGamal encryption scheme 
 * (Based on Fairplay project <a>http://www.cs.huji.ac.il/project/Fairplay/</a>).
 * @author Ayman Jarrous
 * @version 1.0
 */

public class ElGamalEncryption {
	
	// p size is 1024
	private static final BigInteger p = new BigInteger(
			"4f26aae8704873bcebfbb7cf0c490575093dad0a60b575b7b025b746fca43dcd" +
			"983275e8f0b72cf92d403450e5b0bfad9f540e3c71de07724956028b4b378e91" +
			"98e8faed0541c79430cd24e8c1e414dfe7824335368993e22400117a85b7c50d" +
			"7f5cd557076cac3e0afaa26cf5dba7b5e043cbdb02eccba3ea1b41d2e5100c5",
			+16);
	
	private static final int Qsize = 160;
	private static final BigInteger q = new BigInteger(
			"e5dd551b16375da4f47aaba7f2272556ae0fcc47",
			16);
	
	private static final BigInteger g = new BigInteger(
			"12b49a27df027dab8492525e74e674702e50662b42ff4d8ac6adcbead05288cd" +
			"adcd6adeabb16f2cebe6cd6bd26ac0e52e21ca081ec70e2bc5be0c50cc81921e" +
			"219f5b23775cae3e55c841d2fb473faff4ebd1d586933f0e4c7778274068661d" +
			"faebe435fed927443e58d3e1b672b9000ca4921b1d493924606ff340080dc2e",
			16);
	
	private static final BigInteger C = new BigInteger(
			"10962444c6591427c4156e9c41d6b9e8b4f6c6b201657f49870671de2825d842" +
			"67ad7697b04f36e58701d944583119f401ca203eb70c130686b90ebf967f32b5" +
			"642e936c0332660aaabe5387d18376b651cb2a77d906537e8064e50976511ed5" +
			"621d0891b12642c86e5ed23eb3ac8802c45340163a10bfce9978473152a4b5e",
			16);
	
	public static SecureRandom RAND_GENERATOR=new SecureRandom();;    
    //---------------------------------------------------------------	
 	
	/**
	 * Creates two keys, first one contains ElGamal key and the second one is fake key.
	 * @param a to create g<sup>a</sup> mod p =  public key=h
	 * @return An array of size two, the first element is the encryption key
	 * and the second one is the fake key.
	 */
	public static BigInteger[] generatePublicAndFakeKeys(
			BigInteger a) {
    	if(a==null) {
    		System.err.println("ElGamalEncryption, generatePublicAndFakeKeys: Null input");
    		System.exit(-1);
    	}
		
		BigInteger[] pubKeys = new BigInteger[2];
        
    	// A real public key whose DL is known
    	pubKeys[0] = g.modPow(a, p); // g^a mod p=h=public key
        // A 'fake' public key whose DL is not known
    	pubKeys[1] = pubKeys[0].modInverse(p); // g^(-a) mod p
    	pubKeys[1] = C.multiply(pubKeys[1]).mod(p);

        return (pubKeys);
    }

    //---------------------------------------------------------------

	/**
	 * Creates an ElGamal encryption of two parts, the random part :g^r mod p  and the 
	 * encrypted message: (m*h^r)mod p.
	 * @param h g<sup>a</sup> mod p = public key
	 * a: private key
	 * @param r a random number
	 * @param m message to encrypt.
	 * @return An array of two elements, the first one is g<sup>r</sup> mod p, 
	 * and the second element is the encrypted message.
	 */
    public static BigInteger[] encrypt(
    		BigInteger h, BigInteger r, BigInteger m) {
    	if(h==null) {
    		System.err.println("(1.) ElGamalEncryption, encrypt: Null public key");
    		System.exit(-1);
    	}
    	if(r==null) {
    		System.err.println("(2.) ElGamalEncryption, encrypt: Null random value");
    		System.exit(-1);
    	}
    	if(m==null) {
    		System.err.println("(3.) ElGamalEncryption, encrypt: Null message");
    		System.exit(-1);
    	}
    	
    	
    	BigInteger enc[]=new BigInteger[2];
    	enc[0]=g.modPow(r, p);
    	enc[1]=h.modPow(r, p).multiply(m).mod(p);
    	return enc;
    }
    
    //---------------------------------------------------------------

    /**
     * Decrypts an ElGamal encryption.
     * @param gr g<sup>r</sup> mod p 
     * @param cipher An encrypted message:(m*h^r)mod p.
     * @param a  the private key.
     * @return The decryption of the ciphertext.
     */
    public static BigInteger decrypt(
    		BigInteger gr, BigInteger cipher, BigInteger a) {
    	if(gr==null) {
    		System.err.println("(1.) ElGamalEncryption, decrypt: Null generator");
    		System.exit(-1);
    	}
    	if(cipher==null) {
    		System.err.println("(2.) ElGamalEncryption, decrypt: Null ciphertext");
    		System.exit(-1);
    	}
    	if(a==null) {
    		System.err.println("(3.) ElGamalEncryption, decrypt: Null private key");
    		System.exit(-1);
    	}
    	
    	return cipher.multiply((gr.modPow(a, p)).modInverse(p)).mod(p); // [(m*h^r)*(g^r)^-a]mod p
    }
    
    //---------------------------------------------------------------
    
    /**
     *(new BigInteger(Qsize-1,RAND_GENERATOR)).mod(q)= one random in (0,2^qsize-1)mod q
     *
     * Returns a random number in the domain [1,q].
     * @return A random number.
     */
    public static BigInteger getRandom() {
    	return ((new BigInteger(Qsize-1,RAND_GENERATOR)).mod(q));
    }
}

