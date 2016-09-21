package com.meeba.vituniversaldatabase.library;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class Hasher {
	/**
	 * Method to convert Byte to hex
	 * 
	 * @param data
	 * @return
	 * @throws java.io.IOException
	 */
	private static String convertToHex(byte[] data) throws java.io.IOException {
		String result = "";
		for (int i = 0; i < data.length; i++) {
			result += Integer.toString((data[i] & 0xff) + 0x100, 16).substring(
					1);
		}
		return result;
	}

	/**
	 * Method to convert input string to MD5
	 * 
	 * @param unhashed
	 *            String input string
	 * 
	 * @return String md5 hash of the input string
	 */
	public String toMD5(final String unhashed) {
		String _md5Hashed = null;

		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(unhashed.getBytes());

			byte messageDigest[] = digest.digest();

			StringBuffer MD5Hash = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				MD5Hash.append(h);
			}
			_md5Hashed = MD5Hash.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return _md5Hashed;
	}

	/**
	 * Method to convert input string to SHA-1
	 * 
	 * @param unhashed
	 *            String input string
	 * @return String SHA-1 Hash of input string
	 */
	public String toSHA1(final String unhashed) {
		String _sha1Hashed = null;
		MessageDigest mdSha1 = null;
		try {
			mdSha1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e1) {
			Log.e("myapp", "Error initializing SHA1 message digest");
		}
		try {
			mdSha1.update(unhashed.getBytes("ASCII"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] data = mdSha1.digest();

		try {
			_sha1Hashed = convertToHex(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return _sha1Hashed;
	}
}
