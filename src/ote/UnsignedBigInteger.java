
 package ote;

     import java.math.BigInteger;
     import java.util.Arrays;

     public class UnsignedBigInteger {

         public static byte[] toUnsignedByteArray(BigInteger value) {
             byte[] signedValue = value.toByteArray();
             if(signedValue[0] != 0x00) {
                 throw new IllegalArgumentException("value must be a psoitive BigInteger");
             }
             return Arrays.copyOfRange(signedValue, 1, signedValue.length);
         }

         public static BigInteger fromUnsignedByteArray(byte[] value) {
             byte[] signedValue = new byte[value.length + 1];
             System.arraycopy(value,  0, signedValue, 1, value.length);
             return new BigInteger(signedValue);
         }
     }
  

