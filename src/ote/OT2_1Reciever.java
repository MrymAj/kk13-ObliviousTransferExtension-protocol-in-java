package ote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import utils.crypto.ElGamalEncryption;

public class OT2_1Reciever{
	
	private static BigInteger keys[]=null;
	private static BigInteger a=null;
	
	private static void randomKeys() {
		a=ElGamalEncryption.getRandom();
		keys=ElGamalEncryption.generatePublicAndFakeKeys(a);
		
	}
	
	public static BigInteger execute(int bit, BufferedReader br, BufferedWriter bw) {
		if(br==null) {
			System.err.println("(1.) OT2_1Client, execute: Null BufferedReader");
			System.exit(-1);
		}
		if(bw==null) {
			System.err.println("(2.) OT2_1Client, execute: Null BufferedWriter");
			System.exit(-1);
		}
		if(!(bit == 0 || bit==1)) {
			System.err.println("(3.) OT2_1Client, execute: The program get only 0 or 1");
			System.exit(-1);
		}
		randomKeys();
		BigInteger cipher0[]=new BigInteger[2];
		BigInteger cipher1[]=new BigInteger[2];
		
		try {
			bw.write(keys[bit].toString());
			bw.newLine();
			bw.write(keys[1-bit].toString());
			bw.newLine();
			bw.flush();
		} 
		
		catch (IOException ioe) {
			System.err.print("(4.) OT2_1Client, execute: "+ioe.getMessage());
			System.exit(-1);
		}
		
		try {
			
			cipher0[0]=new BigInteger(br.readLine());
			cipher0[1]=new BigInteger(br.readLine());
			cipher1[0]=new BigInteger(br.readLine());
			cipher1[1]=new BigInteger(br.readLine());
		} 
		
		catch(IOException ioe) {
			System.err.println("OT2_1Client, execute: "+ioe.getMessage());
			System.exit(-1);
		}
		
		BigInteger msg=null;
		if(bit==0) {
			msg=ElGamalEncryption.decrypt(cipher0[0], cipher0[1], a);
		} else {
			msg=ElGamalEncryption.decrypt(cipher1[0], cipher1[1], a);
		}
		return msg;
	}
}
