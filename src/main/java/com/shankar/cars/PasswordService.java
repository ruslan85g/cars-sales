package com.shankar.cars;

//public class PasswordService {
//
//}
//import org.myorg.SystemUnavailableException;
import sun.misc.BASE64Encoder;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
//import com.sun.mail.util.BASE64DecoderStream;
//import com.sun.mail.util.BASE64EncoderStream;

@SuppressWarnings("restriction")
public final class PasswordService {

	public static BASE64Encoder BASE64DecoderStream = new BASE64Encoder();

	private PasswordService() {
	}

	public synchronized static String encrypt(String plaintext)
			throws Exception {
		byte[] bytesEncoded = null;
		try {
			// encode data on your side using BASE64
			bytesEncoded = Base64.encodeBase64(plaintext.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String(bytesEncoded); // step 6
	}

	public static String decrypt(String str) {

		byte[] valueDecoded = null;
		try {
			// Decode data on other side, by processing encoded data
			valueDecoded = Base64.decodeBase64(str.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(valueDecoded);
	}
}
// encode data on your side using BASE64
// byte[] bytesEncoded = Base64.encodeBase64(str .getBytes());
// System.out.println("ecncoded value is " + new String(bytesEncoded ));
//
// //Decode data on other side, by processing encoded data
// byte[] valueDecoded= Base64.decodeBase64(bytesEncoded );
// System.out.println("Decoded value is " + new String(valueDecoded));
// }
