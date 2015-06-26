package com.vladkel.transposition.attack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vladkel.common.crypto.interfaces.IAttack;
import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;

public class TranspositionAttack implements IAttack {
	
	Map<Character, Integer> refFrequency;
	Map<Character, Integer> encodedFrequency;
	
	public TranspositionAttack(File frequencySample) {
		refFrequency = new HashMap<>();
		encodedFrequency = new HashMap<>();
		
		for(int i = 0; i < AlphabetUtils.alphabet.length(); i++){
			refFrequency.put(AlphabetUtils.alphabet.charAt(i), 0);
			encodedFrequency.put(AlphabetUtils.alphabet.charAt(i), 0);
		}
		
		fillFrequencyArray(frequencySample);
	}

	@Override
	public void findKey(File encoded, File foundKey) {
		
		try {
			
			/**
			 * Read encoded file
			 */
			
			String encodedContent = AlphabetUtils.formalize(FileUtils.readFile(encoded));
			
			/**
			 * Traitment
			 */
			
			fillEncodedFrequency(encodedContent);
			List<Integer> list = new ArrayList<>();
			
			for(int i = 1; i < 10; i ++) {
				if((encodedContent.length() % i) == 0) {
					list.add(i);
				}
			}
			
			for(Integer size : list) {
				System.out.println(size);
				
				String [] array = AlphabetUtils.splitByLenght(encodedContent, size);
				
				Map<String, Integer> map = new HashMap<>();
				
				for(int i = 0; i < array.length; i++) {
					// abandon
				}
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Utils
	 */
	public void fillFrequencyArray(File frequencySample) {
		try {
			String content = AlphabetUtils.formalize(FileUtils.readFile(frequencySample)).trim();
			for (int i = 0; i < content.length(); i++) {
				char c = Character.toUpperCase(content.charAt(i));
				if (refFrequency.get(c) != null) {
					refFrequency.put(c, refFrequency.get(c) + 1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fillEncodedFrequency(String encodedContent) {
		String content = encodedContent;
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (encodedFrequency.get(c) != null) {
				encodedFrequency.put(c, encodedFrequency.get(c) + 1);
			}
		}
	}

}
