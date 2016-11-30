package ote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import utils.crypto.ElGamalEncryption;

public class OT2_1Sender{
	
	public static void execute(final BigInteger m0, final BigInteger m1, BufferedReader br, BufferedWriter bw) {
		if(br==null) {
			System.err.println("(1.) OT2_1Server, execute: Null BufferedReader");
			System.exit(-1);
		}
		if(bw==null) {
			System.err.println("(2.) OT2_1Server, execute: Null BufferedWriter");
			System.exit(-1);
		}
		if(m0==null || m1==null){
			System.err.println("(3.) OT2_1Server, execute: One of the messages is null");
			System.exit(-1);
		} 
		
		BigInteger keys[]=new BigInteger[2];
		try {
			
			keys[0]=new BigInteger(br.readLine());
			keys[1]=new BigInteger(br.readLine());
			}
		
		catch(IOException ioe) {
			System.err.println("(4.) OT2_1Server, execute: " + ioe.getMessage());
			System.exit(-1);
		}
		
		BigInteger[] cipher0=new BigInteger[2];
		BigInteger[] cipher1=new BigInteger[2];
		BigInteger r=ElGamalEncryption.getRandom();
		
		cipher0=ElGamalEncryption.encrypt(keys[0], r, m0);
		cipher1=ElGamalEncryption.encrypt(keys[1], r, m1);
		
		try {
			bw.write(cipher0[0].toString());// g<sup>r</sup> mod p
			bw.newLine();
			bw.write(cipher0[1].toString());//encrypted message
			bw.newLine();
			bw.write(cipher1[0].toString());// g<sup>r</sup> mod p
			bw.newLine();
			bw.write(cipher1[1].toString());//encrypted message
			bw.newLine();
			bw.flush();
		
		} 
		catch (IOException ioe) {
			System.err.println("(4.) OT2_1Server, execute: "+ioe.getMessage());
			System.exit(-1);
		}
	}

}
