package com.vladkel.mono.cypher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;


public class MonoCypher implements ICypher {

	@Override
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
			System.out.println("Original message : " + originalMessage.substring(0, 50) + " ...");
			
			/**
			 * Read key
			 */
			
			keyStr = FileUtils.readFile(key);
			System.out.println("Key : " + keyStr);
			
			/**
			 * Fill the dico map to translate message
			 */
			
			for(int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
				dico.put(AlphabetUtils.alphabet.charAt(i), keyStr.charAt(i));
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
			System.out.println("Encoded message : " + encodedMessage.substring(0, 50) + " ...");
			
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

	@Override
	public Boolean decode(File crypted, File key, File decoded) {
		
		String cryptedMessage;
		String keyStr;
		String decodedMessage;
		Map<Character, Character> dico = new HashMap<>();
		
		try {
			
			/**
			 * Read crypted
			 */
			
			cryptedMessage = FileUtils.readFile(crypted);
			System.out.println("Crypted message : " + cryptedMessage.substring(0, 50) + " ...");
			
			/**
			 * Read key
			 */
			
			keyStr = FileUtils.readFile(key);
			System.out.println("Key : " + keyStr);
			
			/**
			 * Fill the dico map to translate message
			 */
			
			for(int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
				dico.put(keyStr.charAt(i), AlphabetUtils.alphabet.charAt(i));
			}
			
			/**
			 * Traitment
			 */
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < cryptedMessage.length(); i++) {
				sb.append(dico.get(cryptedMessage.charAt(i)));
			}
			
			decodedMessage = sb.toString();
			System.out.println("Decoded message : " + decodedMessage.substring(0, 50) + " ...");
			
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
		List<String> dictionnaire = new ArrayList<>();
		List<String> stay = new ArrayList<>(Arrays.asList(AlphabetUtils.alphabet.split("")));
		
		for(int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
			dictionnaire.add(shuffle(stay));
		}
		
		try {
			FileWriter fw = new FileWriter(key);
			BufferedWriter bw = new BufferedWriter(fw);
			
			StringBuilder sb = new StringBuilder();
			for(String str : dictionnaire) {
				sb.append(str);
			}
			
			bw.write(sb.toString());
			bw.flush();
			bw.close();
			
			System.out.println("Generation ok in file " + key.getAbsolutePath());
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String shuffle(List<String> list) {
		Random rd = new Random();
		int index = rd.nextInt(list.size());
		String toReturn = list.get(index);
		list.remove(index);
		return toReturn;
	}
	
}
