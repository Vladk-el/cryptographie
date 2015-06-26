package com.vladkel.transposition.cypher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import com.vladkel.common.crypto.interfaces.ICypher;
import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;

public class TranspositionCypher implements ICypher {

	public TranspositionCypher() {
		super();
	}

	@Override
	public Boolean encode(File message, File key, File encoded) {

		try {
			/**
			 * Read message file
			 */

			String original = AlphabetUtils.formalize(FileUtils
					.readFile(message));

			/**
			 * Read key file
			 */

			FileInputStream fis = new FileInputStream(key);
			byte[] keyBites = FileUtils.readByte(fis);
			System.out.println("BeyBite size : " + keyBites.length);

			/**
			 * Traitment
			 */

			StringBuilder[] array = AlphabetUtils.splitByLenghtToStringBuilder(
					original, keyBites.length);
			StringBuilder sb = new StringBuilder();

			if (array[array.length - 1].length() < keyBites.length) {
				while (array[array.length - 1].length() < keyBites.length) {
					array[array.length - 1].append(" ");
				}
			}

			for (int i = 0; i < array.length; i++) {
				// System.out.println(array[i]);
				String temp = array[i].toString();
				for (int j = 0; j < keyBites.length; j++) {
					// System.out.println((int)keyBites[j]);
					// System.out.println(temp.charAt(j) + " ==> " +
					// temp.charAt((int)keyBites[j]));
					array[i].setCharAt(j, temp.charAt((int) keyBites[j]));
				}
				sb.append(array[i]);
			}

			/**
			 * Write encoded message
			 */

			FileUtils.writeFile(encoded, sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Boolean decode(File crypted, File key, File decoded) {

		try {

			/**
			 * Read crypted file
			 */

			String encodedMessage = FileUtils.readFile(crypted);

			/**
			 * Read key file
			 */

			FileInputStream fis = new FileInputStream(key);
			byte[] keyBites = FileUtils.readByte(fis);
			System.out.println("BeyBite size : " + keyBites.length);

			/**
			 * Traitment
			 */

			StringBuilder[] array = AlphabetUtils.splitByLenghtToStringBuilder(
					encodedMessage, keyBites.length);
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < array.length; i++) {
				System.out.println(array[i]);
				String temp = array[i].toString();
				for (int j = 0; j < keyBites.length; j++) {
					
					System.out.print("\t"
							+ j 
							+ " : "
							+ ((int) keyBites[j]) + " : ");
					
					System.out.println(
							temp.charAt((int) keyBites[j])
							+ " ==> "
							+ temp.charAt(j)
					);
					
					array[i].setCharAt(
							(int) keyBites[j],  
							temp.charAt(j)
					);
				}
				sb.append(array[i]);
			}

			/**
			 * Write encoded message
			 */

			FileUtils.writeFile(decoded, sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean generateKey(File key) {

		// Random rd = new Random();
		// int nb = rd.nextInt((10 - 5) + 1) + 5;
		// byte[] bytes = new byte[nb];
		//
		// for(int i = 0; i < nb; i++) {
		// bytes[i] = (byte) rd.nextInt(nb);
		// }

		byte[] bytes = { (byte) 2, (byte) 4, (byte) 0, (byte) 1, (byte) 3 };

		try {
			FileOutputStream fos = new FileOutputStream(key);
			FileUtils.writeByte(fos, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

}
