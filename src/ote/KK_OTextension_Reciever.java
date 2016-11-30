/*
implementation of the KK13 protocol written by mrym.aj
*/

package ote;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JTextArea;
import ote.RandomOracles;


public class KK_OTextension_Reciever {
	
	private int m=128; // The number of OT1ooN Shoud be Divisible by 8
    private int k=128; // Security Parameter
    private int n=64; // The number of Sender input strings ( Note: length  of strings is L=1 bit  ) 
	private LinkedList<byte []> CodeWords=Hadamard.Walsh_Hadamard(k);// result of Hadamad function:one K*K mattix which contains the k strings of k-bit ; (Walsh-Hadamard Codes)
	private  byte[][] djArray=new  byte[m][];// The Array which contains CodeWords
	private  LinkedList <Integer> Selectionindexes=new LinkedList <Integer>(); // The Array which contains r = (r1, r2, r3, ..., rm) selection indexes for reciever 
	private  byte[][] t0Array=new  byte[k][]; // The Array which contains the results of the G(vi) xor diArray for the OT EXTENSION PHASE 
    private  byte[][] t1Array=new  byte[k][]; // The Array which contains the results of the G(vi) for the OT EXTENSION PHASE 
    private  byte[][] t0jArray=new  byte[m][]; //The  Array which contains the  Transposition result of t0Array for the OT EXTENSION PHASE 
    private  byte[][] diArray=new  byte[k][]; // The Array which contains the  Transposition result of djArray for the OT EXTENSION PHASE 
    private  byte[][] wArray=new  byte[k][]; // The Array which contains the results of the G(ui) xor t0Array for the OT EXTENSION PHASE 
    private  byte[][] uArray=new  byte[k][]; // The Array which contains the random ui keys made by the Receiver for the OT PHASE 
    private  byte[][] vArray=new  byte[k][]; // The Array which contains the random vi keys made by the Receiver for the OT PHASE 
    private  LinkedList<int[]> Y_Recieved=new LinkedList<int[]>(); //The List contains the result of  yj=xj xor H(qj xor(Cr and S) received from sender
    private LinkedList <Integer> Z=new LinkedList<Integer>(); //***contains the elements which reciever want*** Zj= yj xor H(t0jArray)
    private BufferedReader br;
	private BufferedWriter bw;
	JTextArea jtServer;
	String txt;
	private RecieverTransfer R_T;
	
         //Default constructor 
	
	    public KK_OTextension_Reciever(JTextArea jf,BufferedReader br, BufferedWriter bw){
    
		    if(br==null) {
    		System.err.println("KK_OTextension_Reciever, constructor: null BufferedReader.");
    		System.exit(-1);
          } 
    	    if(bw==null) {
    		System.err.println("KK_OTextension_Reciever, constructor: null BufferedWriter.");
    		System.exit(-1);
    	  }
         
    	    this.jtServer=jf;
    	    this.br=br;
    	    this.bw=bw; 
	 }

	    
	
        public void Preprocessing () throws  IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, ClassNotFoundException{
	
	     setSelectionindexes();
	     setUandVArray();
	     setDjArray();
	     setDiArray();
	     setTArray();
	     setT0JArray();
	     
    }



	    public void execteAfterpreprocessing() throws NoSuchAlgorithmException, NullPointerException, IOException, InvalidKeyException, InterruptedException{
		 br.close();
		 bw.close();
		 setwArray();
		 R_T=new RecieverTransfer();
		 R_T.W_TransferSender(k,wArray);
		 Y_Recieved=R_T.Y_TransferReceiver(m);
		 setZLinkedList();
	 }


       // Method to create the Random Selection indexes 
	 
        public void setSelectionindexes() {
    	
    	  for(int j=0; j<m; j++) {
    		Random rn = new Random();
    		Integer randomNum = rn.nextInt(n);
    		Selectionindexes.add(randomNum);
    		
    		// test: Selectionindexes.add(0); 
    		
    	  }

    }
    

        // Method to set the random ui and  vi
    
        public void setUandVArray() {
        	for (int i = 0; i < k; i++) {
        		Random rn = new Random();
        		int randomNum = rn.nextInt(255)+1;
		    	byte[] bytes = new byte[k/8];    
	        	Arrays.fill(bytes,(byte)randomNum);
	        	uArray[i]=bytes;
        	}
        	
            for (int i = 0; i < k; i++) {
        	    Random rn = new Random();
    		    int randomNum = rn.nextInt(255)+1;
                byte[] bytes = new byte[k/8];    
       	        Arrays.fill(bytes,(byte)randomNum);
      	        vArray[i]=bytes;
      	
        }
       
    }
    
    
    
	    // Method to set the djArray
    
        public void setDjArray() {
    
          for (int j= 0; j<m; j++) {
             djArray[j]= CodeWords.get(Selectionindexes.get(j));
       
             }
        
         }
	
    
     
        // Method to set the di array using In-place matrix transposition
      
        public void setDiArray() throws IOException {
            	
        	int height = m; 
            int width = k; 

            int[][] array = new int[width][height]; // Make a two-dimensional int array to put each bit of the byte arrays
             
            // Transposition of the byte array to the int array
            
            for (int j=0; j<width; j++) {
                for (int i = 0; i < height; i++) {
                    array[j][i] = getBit(djArray[i], j);
                    
                }
               
            }

                // Convert each int array to byte array and insert it in diArray
            
                for (int i = 0; i < k; i++) {
                    diArray[i] = intArrayToByteArray(array[i]);
                
                }
                
            }

        
        
       // Method to set the t1Array and t0Array
        
        public void setTArray() throws InvalidKeyException, NoSuchAlgorithmException   {
        	
          for (int i = 0; i < k; i++) {
        	  t1Array[i] = RandomOracles.G(m/8,vArray[i]);
              t0Array[i] =xorByteArrays(t1Array[i],diArray[i]);
          
        }
      
      }
    
    
    
       // Method to set the t0jArray using In-place matrix transposition
   
        public void setT0JArray() throws IOException {

        int width = m; 
        int height = k; 
        int[][] array = new int[width][height]; // Make a two-dimensional int array to put each bit of the byte arrays
      
        // Transposition of the byte array to the int array
        
        for (int i = 0; i <width; i++) {
            for (int j = 0; j < height; j++) {
                array[i][j] = getBit(t0Array[j], i);
            }
      
        }
        
        // Convert each int array to byte array and insert it in t0jArray
        
        for (int i = 0; i < m; i++) {
            t0jArray[i] = intArrayToByteArray(array[i]);
            
        }
       
    }
        
        
        
       // Method to set the wArray
       
       public void setwArray() throws NoSuchAlgorithmException,NullPointerException {
    	
    	    for (int i = 0; i < k; i++) {
    	        wArray[i]=(xorByteArrays(RandomOracles.G(m/8,uArray[i]),t0Array[i]));
    	      
              }
    	    
    }
       
       
       /* Method for the KK_OTextension_Reciever to use 1oo2 OT as a sender (OT PHASE). The method will run k times
         * OT PHASE
         * REMEMBER: During the OT PHASE the roles are inverted
         * The Sender acts as the Receiver and the Receiver acts as the Sender
        */
       
    
       public void obliviousTransferSender() throws IOException {

        
        System.out.println("\n=========================================================================================");
        System.out.println("\t\t Oblivious Transfer in Reciever for the OT phase begins now...");
        System.out.println("=========================================================================================\n");
       
       
        for (int i = 0; i < k; i++) {
        	 BigInteger x0,x1;
             x0 =UnsignedBigInteger.fromUnsignedByteArray(uArray[i]);
             x1 =UnsignedBigInteger.fromUnsignedByteArray(vArray[i]);
        	 OT2_1Sender.execute(x0, x1, br, bw);
           }
        
        System.out.println("\n=========================================================================================");
        System.out.println("\t\t Oblivious Transfer in Reciever for the OT phase ends now...");
        System.out.println("=========================================================================================\n");
      }
  
      
       // Method to set Final Result
    
       public void setZLinkedList() throws NoSuchAlgorithmException  {  
               int rj;
               int hash;
               int[]yj;
               for (int j = 0; j < m; j++) {
                   yj=Y_Recieved.get(j);
            	   rj=Selectionindexes.get(j);
            	   hash=RandomOracles.H(t0jArray[j]);
            	   Z.add(hash^ yj[rj]);
               
            	}   
               
               System.out.println(" \n Selectionindexes:" + Selectionindexes);
               System.out.println("\n final result in Reciever :" +Z);
         }
         
       
       
       // Method to get the XOR result between two byte arrays
       
      public static byte[] xorByteArrays(byte[] a, byte[] b) 
        {
            byte[] result = new byte[Math.min(a.length, b.length)];

            if (!(a.length == b.length)) {
                System.out.println("\n=================");
                System.out.println("Lengths NOT equal");
                System.out.println("=================\n");
                System.exit(3);
            }

            for (int i = 0; i < result.length; i++) {
                result[i] = (byte) (((int) a[i]) ^ ((int) b[i]));
            }
            return result;
        }

       
       // Method to convert an int array into a byte array
       
       public static byte[] intArrayToByteArray(int[] bits) {

            // If the condition isn't satisfied, an AssertionError will be thrown.
            // The length MUST be divisible by 8.
            assert bits.length % 8 == 0;
            byte[] bytes = new byte[bits.length / 8];

            for (int i = 0; i < bytes.length; i++) {
                int b = 0;
                for (int j = 0; j < 8; j++)
                    b = (b << 1) + bits[i * 8 + j];
                bytes[i] = (byte) b;
            }
            return bytes;
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
