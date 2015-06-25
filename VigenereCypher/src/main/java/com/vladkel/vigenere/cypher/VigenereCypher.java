package com.vladkel.vigenere.cypher;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vladkel.common.crypto.interfaces.ICypher;
import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;

public class VigenereCypher implements ICypher {
	
	List<Character> robins;
	Iterator<Character> it;

	public VigenereCypher() {
		super();
		robins = new ArrayList<>();
	}
	
	@Override
	public Boolean encode(File message, File key, File encoded) {
		String originalMessage;
		String keyStr;
		String encodedMessage;
		
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
			
			keyStr =  AlphabetUtils.formalize(FileUtils.readFile(key));
			for(int i = 0; i < keyStr.length(); i++) {
				robins.add(keyStr.charAt(i));
			}
			it = robins.iterator();
			
			/**
			 * Traitment
			 */
			
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < originalMessage.length(); i++) {
				char c;
				char robin = (char) getRobin();
				int position = AlphabetUtils.alphabet.indexOf(originalMessage.charAt(i)) + AlphabetUtils.alphabet.indexOf(robin);
				
				c = position < AlphabetUtils.alphabet.length() ? 
						AlphabetUtils.alphabet.charAt(position) : 
							AlphabetUtils.alphabet.charAt(position - AlphabetUtils.alphabet.length());
				
//				System.out.println("robin : " + robin);
//				System.out.println("clear char : " + originalMessage.charAt(i));
//				System.out.println("crypted char : " + c);
//				System.out.println();
				sb.append(c);
			}
			
			encodedMessage = sb.toString();
			System.out.println("Encoded message : " + encodedMessage + " ...");
			
			/**
			 * Write encoded message
			 */
			
			FileUtils.writeFile(encoded, encodedMessage);
			
			System.out.println("Message encoded properly");
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public Boolean decode(File crypted, File key, File decoded) {
		
		String cryptedMessage;
		String keyStr;
		String decodedMessage;
		
		try {
			
			/**
			 * Read crypted
			 */
			
			cryptedMessage = FileUtils.readFile(crypted);
			System.out.println("Crypted message : " + cryptedMessage);
			
			/**
			 * Read key
			 */
			
			keyStr =  AlphabetUtils.formalize(FileUtils.readFile(key));
			for(int i = 0; i < keyStr.length(); i++) {
				robins.add(keyStr.charAt(i));
			}
			it = robins.iterator();
			
			/**
			 * Traitment
			 */
			
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < cryptedMessage.length(); i++) {
				char c;
				char robin = (char) getRobin();
				int position = AlphabetUtils.alphabet.indexOf(cryptedMessage.charAt(i)) - AlphabetUtils.alphabet.indexOf(robin);
				
				c = position >= 0 ? 
						AlphabetUtils.alphabet.charAt(position) : 
							AlphabetUtils.alphabet.charAt(AlphabetUtils.alphabet.length() + position);
				
//				System.out.println("robin : " + robin);
//				System.out.println("clear char : " + cryptedMessage.charAt(i));
//				System.out.println("crypted char : " + c);
//				System.out.println();
				sb.append(c);
			}
			
			decodedMessage = sb.toString();
			System.out.println("Decoded message : " + decodedMessage);
			
			/**
			 * Write decoded message
			 */
			
			FileUtils.writeFile(decoded, decodedMessage);
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public boolean generateKey(File key) {
		// TODO Auto-generated method stub
		return false;
	}

	private int getRobin() {
		if(!it.hasNext()) {
			it = robins.iterator();
		}
		return it.next();
	}
	
}
