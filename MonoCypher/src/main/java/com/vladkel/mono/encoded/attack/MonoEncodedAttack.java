package com.vladkel.mono.encoded.attack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;
import com.vladkel.common.crypto.utils.MapUtils;

public class MonoEncodedAttack implements IMonoEncodedAttack {

	Map<Character, Integer> frequencyArray;

	Map<Character, Integer> encodedFrequencyArray;

	public MonoEncodedAttack(File frequencySample) {
		super();
		frequencyArray = new HashMap<>();
		encodedFrequencyArray = new HashMap<>();

		for (int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
			frequencyArray.put(AlphabetUtils.alphabet.charAt(i), 0);
			encodedFrequencyArray.put(AlphabetUtils.alphabet.charAt(i), 0);
		}

		fillFrequencyArray(frequencySample);
	}

	@Override
	public void findKey(File encoded, File foundKey) {

		Map<Character, Integer> sortedFrequency = MapUtils.sortByRevertValues(frequencyArray);
		Map<Character, Integer> sortedEncodedFrequency = MapUtils.sortByRevertValues(encodedFrequencyArray);
		
		for (Character c : sortedFrequency.keySet()) {
			System.out.println("\t" + c + " ==> "
					+ sortedFrequency.get(c));
		}
		
		for (Character c : sortedEncodedFrequency.keySet()) {
			System.out.println("\t" + c + " ==> "
					+ sortedEncodedFrequency.get(c));
		}
		
		StringBuilder sb = new StringBuilder(AlphabetUtils.alphabet);
		
		List<Character> keys = new ArrayList<Character>(sortedFrequency.keySet());
		List<Character> encodedKeys = new ArrayList<Character>(sortedEncodedFrequency.keySet());
		
		System.out.println(sb.toString());
		
		for(int i = 0; i < keys.size(); i++) {
			System.out.println(AlphabetUtils.alphabet.indexOf(keys.get(i)) + " : " + keys.get(i) + " ==> " + encodedKeys.get(i));
			sb.setCharAt(
					AlphabetUtils.alphabet.indexOf(
						keys.get(i)
					), 
					encodedKeys.get(i)
				);
		}
		
		System.out.println("KEY : " + sb.toString());
		
		try {
			FileUtils.writeFile(foundKey, sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void fillFrequencyArray(File frequencySample) {
		try {
			String content = FileUtils.readFile(frequencySample);

			System.out.println("Clear content : " + content.substring(0, 50) + " ...");
			
			content = AlphabetUtils.formalize(content);

			for (int i = 0; i < content.length(); i++) {
				char c = Character.toUpperCase(content.charAt(i));
				if (frequencyArray.get(c) != null) {
					frequencyArray.put(c, frequencyArray.get(c) + 1);
				}
			}

			displayFrequencyArray();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fillEncodedFrequencyArray(File encodedFrequencySample) {
		try {
			String content = FileUtils.readFile(encodedFrequencySample);

			System.out.println("Encoded content : " + content.substring(0, 50) + " ...");

			for (int i = 0; i < content.length(); i++) {
				char c = content.charAt(i);
				if (encodedFrequencyArray.get(c) != null) {
					encodedFrequencyArray.put(c,
							encodedFrequencyArray.get(c) + 1);
				}
			}

			//displayEncodedFrequencyArray();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displayFrequencyArray() {
		for (Character c : frequencyArray.keySet()) {
			System.out.println("\t" + c + " ==> " + frequencyArray.get(c));
		}
	}

	private void displayEncodedFrequencyArray() {
		for (Character c : encodedFrequencyArray.keySet()) {
			System.out.println("\t" + c + " ==> "
					+ encodedFrequencyArray.get(c));
		}
	}

}
