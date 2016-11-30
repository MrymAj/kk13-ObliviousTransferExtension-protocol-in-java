/*
implementation of the KK13 protocol written by mrym.aj
*/
package ote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

/* This class implements sending the wArray and recieving the Y 
in reciever side during the OT EXTENSION PHASE */

 public class RecieverTransfer {

    private ServerSocket serverSock1;
    private Socket connection1;
    private DataInputStream in;
    private DataOutputStream out;
 

      public RecieverTransfer() throws IOException{

	   serverSock1=new ServerSocket(4044);
	   connection1=serverSock1.accept();
	   connection1.setTcpNoDelay(true);
	   out=new DataOutputStream(new BufferedOutputStream(connection1.getOutputStream()));
	   in=new DataInputStream(new BufferedInputStream(connection1.getInputStream()));

  }

      /* OT EXTENSION PHASE
      Method for Sending the wArray during the OT EXTENSION PHASE  */

      public void W_TransferSender(int k,byte[][]w) throws InvalidKeyException, NoSuchAlgorithmException, IOException  {
	
       
       System.out.println("\n=========================================================================================");
       System.out.println("\t\t Sending wArray in Reciever begins now ...");
       System.out.println("=========================================================================================\n");
  

        try{
            for(int i=0;i<k;i++){
            	out.writeInt(w[i].length);
             	out.write(w[i]);
             	out.flush();
            }
           
         }
        
	    catch (IOException ioe) {
	     System.err.println("(0.) wArrayTransferReciever: "+ioe.getMessage());
	     System.exit(-1);
      }

         System.out.println("\n=========================================================================================");
         System.out.println("\t\t Sending wArray in Reciever ends now...");
         System.out.println("=========================================================================================\n");   
  }
 
 
      /* OT EXTENSION PHASE
      Method for Recieving the Y during the OT EXTENSION PHASE  */
    
      public LinkedList<int[]> Y_TransferReceiver(int m){
       
        System.out.println("\n=========================================================================================");
        System.out.println("\t\t Recieving Y in Reciever begins now ...");
        System.out.println("=========================================================================================\n");
      
         LinkedList<int[]> Y_R=new LinkedList<int[]>();
    
            try{
             	for (int j = 0; j < m; j++) { 
					  int size= in.readInt();
             	      int [] y=new int[size];
             	      for(int nn=0;nn<size;nn++){
             	    	  y[nn]=in.readInt();  
             	       }
             	      Y_R.add(y);
			      }
            	
            } catch (IOException ioe) {
        		System.err.println("(1.) YLinkedLisTransferReciever: "+ioe.getMessage());
        		System.exit(-1);
           }
		
         System.out.println("\n=========================================================================================");
         System.out.println("\t\t Recieving Y in Reciever ends now ...");
         System.out.println("=========================================================================================\n");

	     return (Y_R);
	   
         } 
 
      } 
