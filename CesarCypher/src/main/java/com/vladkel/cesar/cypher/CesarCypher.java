package com.vladkel.cesar.cypher;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;

public class CesarCypher {

	public CesarCypher() {
		super();
	}
	
	
	public Boolean encode(File message, File key, File encoded) {
		String originalMessage;
		String keyStr;
		String encodedMessage;
		Map<Character, Character> dico = new HashMap<>();
		
		try {
			
			/**
			 * Read messages
			 */
			
			originalMessage = FileUtils.readFile(message);
			originalMessage = AlphabetUtils.formalize(originalMessage);
			System.out.println("Original message : " + (originalMessage.length() > 50 ? originalMessage.substring(0, 50)  + " ..." : originalMessage));
			
			/**
			 * Read key
			 */
			
			keyStr = FileUtils.readFile(key);
			int keyPosition = AlphabetUtils.alphabet.indexOf(keyStr.charAt(0));
			System.out.println("Key : " + keyPosition);
			
			/**
			 * Fill the dico map to translate message
			 */
			
			for(int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
				char c;
				if((i + keyPosition) > AlphabetUtils.alphabet.length() - 1) {
					c = AlphabetUtils.alphabet.charAt((i + keyPosition) - AlphabetUtils.alphabet.length());
				} else {
					c = AlphabetUtils.alphabet.charAt(i + keyPosition);
				}
				
				dico.put(AlphabetUtils.alphabet.charAt(i), c);
			}
			
			/**
			 * Traitment
			 */
			
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < originalMessage.length(); i++) {
				Character c = dico.get(Character.toUpperCase(originalMessage.charAt(i)));
				if(c != null) {
					sb.append(c);
				}
			}
			
			encodedMessage = sb.toString();
			System.out.println("Encoded message : " + encodedMessage + " ...");
			
			/**
			 * Write encoded message
			 */
			
			FileUtils.writeFile(encoded, encodedMessage);
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public String decode(File crypted, char key, File decoded) {

		String cryptedMessage;
		String keyStr;
		String decodedMessage;
		Map<Character, Character> dico = new HashMap<>();
		
		try {
			
			/**
			 * Read crypted
			 */
			
			cryptedMessage = FileUtils.readFile(crypted);
			//System.out.println("Crypted message : " + cryptedMessage);
			
			/**
			 * Read key
			 */
			
			int keyPosition = AlphabetUtils.alphabet.indexOf(key);
			//System.out.println("Key : " + keyPosition);
			
			/**
			 * Fill the dico map to translate message
			 */
			
			for(int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
				char c;
				if((i - keyPosition) < 0) {
					c = AlphabetUtils.alphabet.charAt(AlphabetUtils.alphabet.length() + (i - keyPosition));
				} else {
					c = AlphabetUtils.alphabet.charAt(i - keyPosition);
				}
				
				dico.put(AlphabetUtils.alphabet.charAt(i), c);
			}
			
			/**
			 * Traitment
			 */
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < cryptedMessage.length(); i++) {
				sb.append(dico.get(cryptedMessage.charAt(i)));
			}
			
			decodedMessage = sb.toString();
			//System.out.println("Decoded message : " + decodedMessage);
			
			/**
			 * Write decoded message
			 */
			
			FileUtils.writeFile(decoded, decodedMessage);
			
			return decodedMessage;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	public String decode(File crypted, File key, File decoded) {
		
		String keyStr;
		
		try {
			
			/**
			 * Read key
			 */
			
			keyStr = FileUtils.readFile(key);
			char keyPosition = keyStr.charAt(0);
			return decode(crypted, keyPosition, decoded);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
