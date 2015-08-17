package cn.trinea.android.common.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * DigestUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-03-20
 */
public class DigestUtils {

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'                         };

    /**
     * encode By MD5
     * 
     * @param str
     * @return String
     */
    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return new String(encodeHex(messageDigest.digest()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encode file by md5
     * 
     * @param path file path
     * @return md5 value
     */
	public static String md5File(String path) {
		if (!FileUtils.isFileExist(path)) {
			return "";
		}
		InputStream stream = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			stream = new FileInputStream(path);
			byte[] buf = new byte[2 * 1024];
			while (true) {
				int cnt = stream.read(buf);
				if (cnt < 0) {
					break;
				}
				messageDigest.update(buf, 0, cnt);
			}
			return new String(encodeHex(messageDigest.digest()));
		} catch (Exception e) {
            throw new RuntimeException(e);
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (Exception e) {
	            throw new RuntimeException(e);
			}
		}
	}

    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     * 
     * @param data a byte[] to convert to Hex characters
     * @return A char[] containing hexadecimal characters
     */
    protected static char[] encodeHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }
}
