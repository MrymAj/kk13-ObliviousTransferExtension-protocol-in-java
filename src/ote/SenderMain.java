/*
implementation of the KK13 protocol written by mrym.aj
*/

package ote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.TimeoutException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JTextArea;

//ClientSide
public class SenderMain {
	
	 
	 Socket clientSock=null;
	 BufferedReader br=null;
	 BufferedWriter bw=null;
	 String ipAddress="127.0.0.1";  
	 int port=6086;
	 JTextArea jtServer=null;
	 KK_OTextension_Sender Client=null;
	
	 
	 
	 public void start() throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchProviderException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, TimeoutException, InterruptedException {
		 
		 startConnection();
		 Client= new  KK_OTextension_Sender(br,bw);
		 Timer totalTimer = Timer.start();
		 
		 
		//=====================================================
        //            INITIATION OT EXTENSION PHASE
        //=====================================================
		 
	     System.out.println("Start Client_Preprocessing");
         Client.Preprocessing();
         
         //=====================================================
         //                     Start OT PHASE
         //=====================================================
         
        System.out.println("Start Client_obliviousTransferReciever");  
         Client.obliviousTransferReceiver();
        
         //=====================================================
         //                     end OT PHASE
         //=====================================================
        
     
         //=====================================================
        //                    OT EXTENSION PHASE
        //=====================================================

        System.out.println("Start Client_executeAfterPreprocessing"); 
        
        Client.executeAfterPreprocessing();
        
         long totalTimeSeconds = totalTimer.nanoToSeconds();
         long totalTimeMilliseconds = totalTimer.nanoToMillis();
         System.out.println("\nTotal elapsed time: " + totalTimeSeconds + " seconds\nOr, " + totalTimeMilliseconds + " milliseconds");
        
	}
	
        private void startConnection() {
        	try {
    			
    		    clientSock=new Socket(ipAddress, port);
    			clientSock.setTcpNoDelay(true);
    			br=new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
    			bw=new BufferedWriter(new OutputStreamWriter(clientSock.getOutputStream()));
    			
    		}
    		catch(UnknownHostException uhe) {
    			uhe.printStackTrace();
    			return;
    		}
    		catch(IOException ioe) {
    			ioe.printStackTrace();
    			return;
    		}
        	
    }
	
        
	
    public static void main(String[] args) throws  IOException, InvalidKeyException, NoSuchProviderException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, TimeoutException, ClassNotFoundException, InterruptedException{


    	SenderMain cui=new SenderMain();
    	
        cui.start();
    
    }
	
}
