
/*
implementation of the KK13 protocol written by mrym.aj
*/

package ote;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.TimeoutException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
 
public class ReceiverMain  extends JFrame  {
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JTextArea jTextArea = null;
	BufferedWriter bw=null;
    BufferedReader br=null;
	KK_OTextension_Reciever server=null;
	ServerSocket serverSock=null;
	static Socket connection=null;
	int port=6086;
	String text;
	
	
	
	
	/*
	 * This is the default constructor
	 */
	public ReceiverMain() {
		 super();
		 initialize();
	} 
	
	
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(297, 413);
		this.setContentPane(getJContentPane());
		this.setTitle("Server");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(-1);
        	}
        });
		
	}

	
	
	
		/**
		 * This method initializes jContentPane
		 * 
		 * @return javax.swing.JPanel
		 */
		private JPanel getJContentPane() {
			if (jContentPane == null) {
				jLabel = new JLabel();
				jLabel.setBounds(new Rectangle(12, 31, 70, 19));
				jLabel.setText("Status:");
				jContentPane = new JPanel();
				jContentPane.setLayout(null);
				jContentPane.add(jLabel, null);
				jContentPane.add(getJTextArea(), null);
			}
			return jContentPane;
		}
		
		
		
		/**
		 * This method initializes jTextArea	
		 * 	
		 * @return javax.swing.JTextArea	
		 */
		private JTextArea getJTextArea() {
			if (jTextArea == null) {
				jTextArea = new JTextArea();
				jTextArea.setBounds(new Rectangle(12, 62, 254, 271));
				
			}
			return jTextArea;
		}
	
		
		
		
	private void startConnection() {
		try {
			
			
			 serverSock=new ServerSocket(port);
			 connection=serverSock.accept();
			 connection.setTcpNoDelay(true);
			 br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			 bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			
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
	
	
	
	
	public void start() throws InvalidKeyException, NoSuchAlgorithmException, IOException, ClassNotFoundException, NoSuchProviderException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, TimeoutException, InterruptedException{ 
		
		    this.setVisible(true); 
		    text="Start connection and waiting for requests";
		    jTextArea.setText(text);
		    startConnection();
		    server= new  KK_OTextension_Reciever(this.jTextArea,br,bw);
		   
			//=====================================================
	        //            INITIATION OT EXTENSION PHASE
	        //=====================================================
		    
		    text=text+"\nStart Preprocessing\n";
		    jTextArea.setText(text);
			Timer totalTimer = Timer.start();
			server.Preprocessing();
		
			//=====================================================
	        //                     Start OT PHASE
	        //=====================================================
			
			text=text+"Start oblivious transfer\n";
			jTextArea.setText(text);
			server.obliviousTransferSender();
			
			//=====================================================
	        //                     end OT PHASE
	        //=====================================================
			
			//=====================================================
		    //                    OT EXTENSION PHASE
		    //=====================================================
			
			text=text+"Start executeAfterPreprocessing\n";
			jTextArea.setText(text);
			server.execteAfterpreprocessing();
			
			
			long totalTimeSeconds = totalTimer.nanoToSeconds();
	        long totalTimeMilliseconds = totalTimer.nanoToMillis();
	        System.out.println("\nTotal elapsed time: " + totalTimeSeconds + " seconds\nOr, " + totalTimeMilliseconds + " milliseconds");
			
		     }
	
			 
		
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, ShortBufferException, NoSuchProviderException, IOException, TimeoutException, ClassNotFoundException, InterruptedException {
    	
    	ReceiverMain sui=new ReceiverMain();
        sui.start();
        
    }
}
