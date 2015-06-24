package com.vladkel.vigenere.cypher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;

public class VigenereCypher {
	
	List<Character> robins;
	Iterator<Character> it;

	public VigenereCypher() {
		super();
		robins = new ArrayList<>();
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
				
				System.out.println("robin : " + robin);
				
				c = (char) ((originalMessage.charAt(i) + robin) % AlphabetUtils.alphabet.length()-1);
				
				System.out.println("found char : " + c);
				sb.append(c);
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
	
	private int getRobin() {
		if(!it.hasNext()) {
			it = robins.iterator();
		}
		return it.next();
	}
	
}
