/*
implementation of the KK13 protocol written by mrym.aj
*/

package ote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

 /* This class implements recieving the wArray and sending the Y 
  in sender side  during the OT EXTENSION PHASE */
 

public class SenderTransfer {

	private DataInputStream in;
	private DataOutputStream out;
	private Socket clientSock1;
	
	 public SenderTransfer() throws IOException{
		 try {
			   Thread.sleep(5);
			} 
			catch (InterruptedException e) {
			   e.printStackTrace();
			}
		 
		clientSock1=new Socket("127.0.0.1", 4044);
		clientSock1.setTcpNoDelay(true);
		out=new DataOutputStream(new BufferedOutputStream(clientSock1.getOutputStream()));
		in=new DataInputStream(new BufferedInputStream(clientSock1.getInputStream()));
     
	}
	 
	  /* OT EXTENSION PHASE
       Method for Recieving the wArray during the OT EXTENSION PHASE  */
	
     public byte[][] W_TransferReceiver(int k) throws  IOException, ClassNotFoundException, InterruptedException {
	
       
       System.out.println("\n=========================================================================================");
       System.out.println("\t\t  Recieving WArray in Sender begins now ...");
       System.out.println("=========================================================================================\n");
    
       byte[][] wArray_R=new byte [k][];
       
          try{ 
 	  
            for (int i = 0; i < k; i++) { 
     	      int size= in.readInt();
     	      byte [] w=new byte[size];
     	      in.readFully(w,0,size);
     	      wArray_R[i]=w;
     	 
            }   
          }
          
          catch (IOException ioe) {
    		System.err.println("(0.) wArrayRransferReciever: "+ioe.getMessage());
    		System.exit(-1);
       }
 
      System.out.println("\n=========================================================================================");
      System.out.println("\t\t Recieving WArray in Sender ends now ...");
      System.out.println("=========================================================================================\n");
      
      
      return wArray_R;
    }
     
      /* OT EXTENSION PHASE
       Method for sending the Y during the OT EXTENSION PHASE */
     
     public void Y_TransferSender(int m, LinkedList<int []> y)  throws TimeoutException,IOException {

      
      System.out.println("\n=========================================================================================");
      System.out.println("\t\t Sending Y in Sender begins now ...");
      System.out.println("==========================================================================================\n");
    
          try{
 	          for(int j=0; j<m; j++){
 	        	 out.writeInt(y.get(j).length);
 	        	 for(int nn=0;nn<y.get(j).length;nn++){
 	        		 out.writeInt(y.get(j)[nn]);  
 	        		 out.flush();
 	        	  }
 	        	
 	            }
             }  
         
          catch (IOException ioe) {
  		    System.err.println("(1.) YLinkedListTransferSender: "+ioe.getMessage());
  		    System.exit(-1);
     }
 	  
      System.out.println("\n=========================================================================================");
      System.out.println("\t\t Sending Y in Sender ends now ...");
      System.out.println("=========================================================================================\n");
}

 
     
}
