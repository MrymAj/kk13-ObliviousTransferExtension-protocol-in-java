
package ote;

public class ByteUtils {

  /**
   * Return a new byte array containing a sub-portion of the source array
   * @param srcBegin
   * The beginning index (inclusive)
   * @return The new, populated byte array
   **/
  public static byte[] subbytes(byte[] source, int srcBegin) {
    return subbytes(source, srcBegin, source.length);
  }
  /**
   * Return a new byte array containing a sub-portion of the source array
   * 
   * @param srcBegin
   *          The beginning index (inclusive)
   * @param srcEnd
   *          The ending index (exclusive)
   * @return The new, populated byte array
   **/
  public static byte[] subbytes(byte[] source, int srcBegin, int srcEnd) {
    byte destination[];

    destination = new byte[srcEnd - srcBegin];
    getBytes(source, srcBegin, srcEnd, destination, 0);

    return destination;
  }


  /**
   * Copies bytes from the source byte array to the destination array
   * 
   * @param source
   *          The source array
   * @param srcBegin
   *          Index of the first source byte to copy
   * @param srcEnd
   *          Index after the last source byte to copy
   * @param destination
   *          The destination array
   * @param dstBegin
   *          The starting offset in the destination array
   */
  public static void getBytes(byte[] source, int srcBegin, int srcEnd, byte[] destination,
      int dstBegin) {
    System.arraycopy(source, srcBegin, destination, dstBegin, srcEnd - srcBegin);
  }
  
}
