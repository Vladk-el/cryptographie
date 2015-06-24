package com.vladkel.bigramme.encoded.attack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vladkel.common.crypto.interfaces.IAttack;
import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;
import com.vladkel.common.crypto.utils.MapUtils;

public class BigramEncodedAttack implements IAttack{

	Map<String, Integer> frequencyBigramArray;

	Map<String, Integer> encodedFrequencyBigramArray;

	public BigramEncodedAttack(File frequencySample) {
		super();
		frequencyBigramArray = new HashMap<>();
		encodedFrequencyBigramArray = new HashMap<>();
		
		try {
			String sample = AlphabetUtils.formalize(FileUtils.readFile(frequencySample));
			
			for (int i = 0; i < sample.length(); i++) {
				if(i+1 < sample.length()) {
					String temp = String.valueOf(sample.charAt(i)) + String.valueOf(sample.charAt(i+1));
					
					if(frequencyBigramArray.get(temp) == null) {
						frequencyBigramArray.put(temp, 1);
					} else {
						frequencyBigramArray.put(temp, frequencyBigramArray.get(temp) + 1);
					}
					
				}
			}
			
			
			frequencyBigramArray = MapUtils.sortByStringRevertValues(frequencyBigramArray);
			
//			for(String str : frequencyBigramArray.keySet()) {
//				System.out.println(str + " ==> " + frequencyBigramArray.get(str));
//			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void findKey(File encoded, File keyFile) {
		
		try {
			String sample = AlphabetUtils.formalize(FileUtils.readFile(encoded));
			
			for (int i = 0; i < sample.length(); i++) {
				if(i+1 < sample.length()) {
					String temp = String.valueOf(sample.charAt(i)) + String.valueOf(sample.charAt(i+1));
					
					if(encodedFrequencyBigramArray.get(temp) == null) {
						encodedFrequencyBigramArray.put(temp, 1);
					} else {
						encodedFrequencyBigramArray.put(temp, encodedFrequencyBigramArray.get(temp) + 1);
					}
					
				}
			}
			
			encodedFrequencyBigramArray = MapUtils.sortByStringRevertValues(encodedFrequencyBigramArray);
			
//			for(String str : encodedFrequencyBigramArray.keySet()) {
//				System.out.println(str + " ==> " + encodedFrequencyBigramArray.get(str));
//			}
						
			List<String> keys = new ArrayList<>(frequencyBigramArray.keySet());
			List<String> encodedKeys = new ArrayList<>(encodedFrequencyBigramArray.keySet());
						
			
			String key = (keys.size() > encodedKeys.size()) ? fillKey(keys.subList(0, encodedKeys.size()), encodedKeys) : fillKey(keys, encodedKeys.subList(0, keys.size()));
			
			System.out.println("KEY : " + key);
			
			FileUtils.writeFile(keyFile, key);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String fillKey(List<String> list, List<String> list2) {
		
		Map<Character, Boolean> alreadyExist = new HashMap<>();
		Map<Character, Boolean> removed = new HashMap<>();
		
		StringBuilder sb = new StringBuilder(AlphabetUtils.alphabet);

		for(int i = 0; i < list.size(); i++) {
			
			System.out.println(list.get(i) + " => " + list2.get(i));
									
			if(alreadyExist.get(list2.get(i).charAt(0)) == null) {
				System.out.println("\t" + AlphabetUtils.alphabet.indexOf(list.get(i).charAt(0)) + " : Remplacement de " + list.get(i).charAt(0) + " par " + list2.get(i).charAt(0));
				
				sb.setCharAt(
						AlphabetUtils.alphabet.indexOf(
								list.get(i).charAt(0)
						), 
						list2.get(i).charAt(0)
					);
				System.out.println("PUT " + list2.get(i).charAt(0) + " IN MAP");
				alreadyExist.put(list2.get(i).charAt(0), true);
				removed.put(list.get(i).charAt(0), true);
			}
			
			if(alreadyExist.get(list2.get(i).charAt(1)) == null) {
				System.out.println("\t" + AlphabetUtils.alphabet.indexOf(list.get(i).charAt(1)) + " : Remplacement de " + list.get(i).charAt(1) + " par " + list2.get(i).charAt(1));

				sb.setCharAt(
					AlphabetUtils.alphabet.indexOf(
							list.get(i).charAt(1)
					), 
					list2.get(i).charAt(1)
				);
				System.out.println("PUT " + list2.get(i).charAt(1) + " IN MAP");
				alreadyExist.put(list2.get(i).charAt(1), true);
				removed.put(list.get(i).charAt(1), true);
			}
		}
		/*
		System.out.println("Mapped chars : " + alreadyExist.size());
		System.out.println("Mapped chars : " + AlphabetUtils.alphabet.length());
		
		for(int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
			//System.out.println(AlphabetUtils.alphabet.charAt(i) + " ==> " + defined.get(AlphabetUtils.alphabet.charAt(i)));
			if(removed.get(AlphabetUtils.alphabet.charAt(i)) == null) {
				System.out.println("not defined : " + AlphabetUtils.alphabet.charAt(i));
			}
		}
		*/
		return sb.toString();
	}



}
