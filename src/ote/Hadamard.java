package ote;

import java.util.LinkedList;

   public class Hadamard {

		public static LinkedList<byte []> Walsh_Hadamard(int k) {
	     	
		LinkedList<byte []> C= new LinkedList<byte []>();
		int N = k;
		int[] res=new int[N];
		boolean[][] H = new boolean[N][N];
		H[0][0] = true;
		for(int n = 1; n < N; n += n)
		    {
			for(int i = 0; i < n; i++)
			    for(int j = 0; j < n; j++)
				{
				    H[i+n][j] = H[i][j];
				    H[i][j+n] = H[i][j];
				    H[i+n][j+n] = !H[i][j];
				}
		    }
		
		for(int i = 0; i < N; i++)
		    {
			for(int j = 0; j < N; j++)
			    {
				if(H[i][j]) res[j]=1;
				
				else        res[j]=0;
					 
			    }
            
			C.add(intArrayToByteArray(res));
			
		      }
		
		return C;
		
	 }
		
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
	   
}