/*
implementation of the KK13 protocol written by mrym.aj
*/

package ote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import ote.RandomOracles;


    public class KK_OTextension_Sender {
    	
    private int m=128; // The number of OT1ooN Shoud be Divisible by 8
    private int k=128; // Security Parameter
    private int n=64; // The number of Sender input strings ( Note: length  of strings is L=1 bit  ) 
    private byte[] InputSender=new byte [n/8];// each of Sender input strings
    private LinkedList<byte[]> CodeWords=Hadamard.Walsh_Hadamard(k);// result of Hadamad function: one K*K mattix which contains the k strings of k-bit ; (Walsh-Hadamard Codes)
    private LinkedList<byte[]> InputSList=new LinkedList<byte[]>();//contains the  Sender input strings (each of byte[] contains x1,x2,...,xn )
    private  byte[] sArray=new byte[k/8]; // The Array which contains Choice bits for OT PHASE 
    private  byte[][] wArray_Recieved=new byte[k][];//The Array which contains the results of the G(ui) xor t0Array for the OT EXTENSION PHASE (Array of byte[]) which recieved 
    private  byte[][] aArray=new  byte[k][]; // The array which contains the Ui or Vi received via the OT PHASE 
    private  byte[][] qArray=new  byte[k][]; //The Array which contains the result of [ wArray_Recieved[i] XOR G(aArray[i])] for si=0 or G(aArray[i]) for si=1 for the OT EXTENSION PHASE 
    private  byte[][] qjArray=new  byte[m][]; //The Array which contains the  Transposition result of qArray for the OT EXTENSION PHASE 
    private LinkedList<int[]> Y= new LinkedList<int[]>(); // The List which contains the result of  yj=xj xor H(qj xor(Cr and S)
    private BufferedReader br;
	private BufferedWriter bw;
	private SenderTransfer S_T=null;
	
	
	
         // Default Constructor
	
         public KK_OTextension_Sender(BufferedReader br,BufferedWriter bw ){

          if(br==null) {
     		System.err.println("KK_OTextension_Sender, constructor: null BufferedReader.");
     		System.exit(-1);
           } 
     	  if(bw==null) {
     		System.err.println("KK_OTextension_Sender, constructor: null BufferedWriter.");
     		System.exit(-1);
     	}
      
     	   this.br=br;
     	   this.bw=bw;
     
    	
    }

	     public void Preprocessing () throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchProviderException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, TimeoutException{
		   
		   setInputSender();
		   setSArray();
	}
	
	
	
		 public void executeAfterPreprocessing() throws IOException, ClassNotFoundException, InterruptedException, InvalidKeyException, NoSuchProviderException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, TimeoutException{
			
		   System.out.println("\n Start Sender_wArrayTransferReceiver"); 
		   br.close();
		   bw.close();
		   S_T=new SenderTransfer();
		   wArray_Recieved=S_T.W_TransferReceiver(k);
           System.out.println("\n Start Sender_SetQ"); 
           setQArray();
 		   setQjArray();
 		   System.out.println("\n Start Sender_Set and Sending Y");
 		   setYLinkedList();    
 	       S_T.Y_TransferSender(m,Y);
 	      
		}
		
		
	   // Method to create randomly the input strings for the Sender
  
         public void setInputSender() {
        	 
		    for(int j=0; j<m; j++) {
		    	
		    	new Random().nextBytes(InputSender);
				InputSList.add(InputSender);
				
		    	/*
		    	  //test : 
		    	  byte[] bytes = new byte[n/8];    
	        	  Arrays.fill( bytes, (byte)255);//0
		    	  InputSList.add(bytes);
		    	  String s=toBinary(InputSList.get(j));
		    	  System.out.println("\n InputSList:" +s);
		    	*/
			 }
			
		}	
		    
		
         // Method to create the sArray of size k
    
          public void setSArray() {
        	
           new Random().nextBytes(sArray);
           
           
           /*String s=toBinary(sArray);
            System.out.println(s);*/
           }
           
          
        /* Method for the KK_OTextension_Sender to use 1oo2 OT as a receiver (OT PHASE). The method will run k times
          * OT PHASE
          * REMEMBER: During the OT PHASE the roles are inverted
          * The Sender acts as the Receiver and the Receiver acts as the Sender
        */ 
          
        public void obliviousTransferReceiver() throws IOException, NoSuchAlgorithmException{

            
            System.out.println("\n=========================================================================================");
            System.out.println("\t\t Oblivious Transfer in Sender for the OT phase begins now ...");
            System.out.println("=========================================================================================\n");
           
            
            // Store the ui or vi to aArray
    
            for (int i = 0; i < k; i++) { 
            
            	BigInteger msg=OT2_1Reciever.execute(getBit(sArray, i),br,bw);
            	
            	aArray[i] =msg.toByteArray();
            	
            	if (aArray[i][0] == 0) {
            	     byte[] tmp = new byte[aArray[i].length - 1];
            	     System.arraycopy(aArray[i], 1, tmp, 0, tmp.length);
            	      aArray[i] = tmp; 
            	      }
            	 
            }  
            
            
            System.out.println("=========================================================================================");
            System.out.println("\t\t Oblivious Transfer in Sender for the OT phase ends now ...");
            System.out.println("=========================================================================================");
          
           
       }  
        
   	    // Method to set the qArray 
        
        public void setQArray() throws InvalidKeyException, NoSuchProviderException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {

           
           for (int i = 0; i < k; i++) {
        	   
                if (getBit(sArray, i) == 0)  {
                	
                   qArray[i] =xorByteArrays(RandomOracles.G(m/8,aArray[i]),wArray_Recieved[i]);
                
            } 
                 else if (getBit(sArray, i) == 1) {
                	 
                	  qArray[i] = RandomOracles.G(m/8,aArray[i]);
                	
               }
           } 
        
       }
     

        // Method to set the qjArray using In-place matrix transposition
      
        public void setQjArray() throws IOException {

            int width = m; 
            int height = k; 
            int[][] array = new int[width][height]; // Make a two-dimensional int array to put each bit of the byte arrays
           
            // Transposition of the byte array to the int array
            
            for (int i = 0; i<width ; i++) {
            	for (int j = 0; j < height; j++) {
                    array[i][j] = getBit(qArray[j], i);
                   
                } 
             
            }
            
            // Convert each int array to byte array and insert it in t0jArray
            
            for (int i = 0; i < m; i++) {
            	qjArray[i] = intArrayToByteArray(array[i]);
               
           }
        }

		// Method to set YLinkedList (y0Array, y1Array , ... ymArray)
  
		public void setYLinkedList() throws NoSuchAlgorithmException {
               
            	  for(int j=0;j<m;j++){
            		  
            		 int[] tmp=new int[n];
       	             byte[]and=new byte[k];
       	             byte[]xor=new byte[k];
       	             int bit;
            	   
                     for(int nn=0; nn<n;nn++) {      
                    
            	        and=andByteArrays(CodeWords.get(nn),sArray);
            	        xor= xorByteArrays(qjArray[j],and);
                        bit= RandomOracles.H(xor);
                        tmp[nn]= getBit(InputSList.get(j),nn)^bit;
                    }
                     Y.add(tmp);
            	  }
		}
             
            	
             
        // Method to get the XOR result between two byte arrays
          
        public static byte[] xorByteArrays(byte[] a, byte[] b) {

               byte[] result = new byte[Math.min(a.length, b.length)];

               if (!(a.length == b.length)) {
                   System.out.println("Lengths NOT equal");
                   System.exit(3);
               }

               for (int i = 0; i < result.length; i++) {
                   result[i] = (byte) (((int) a[i]) ^ ((int) b[i]));
               }
               return result;
           }

           
           
        // Method to get the and result between two byte arrays
         
        public static byte[] andByteArrays(byte[] a, byte[] b) {

               byte[] result = new byte[Math.min(a.length, b.length)];

               if (!(a.length == b.length)) {
                   System.out.println("Lengths NOT equal");
                   System.exit(3);
               }

               for (int i = 0; i < result.length; i++) {
                   result[i] = (byte) (((int) a[i]) & ((int) b[i]));
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
           
       
        //  Method to convert the byte array into binary strings
         
       public String toBinary( byte[] bytes )
        {
            StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
            for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
                sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
            return sb.toString();
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
    
