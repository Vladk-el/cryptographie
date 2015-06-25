package com.vladkel.vigenere.attack;

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

public class VigenereAttack implements IAttack {

	@Override
	public void findKey(File encoded, File foundKey) {
		// TODO Auto-generated method stub

		String encodedMessage = null;
		String key = null;
		StringBuilder sb = new StringBuilder();
		Map<String, List<Integer>> dico = new HashMap<>();

		try {

			System.out.println("Start decrypt Vigenere way ...");
			/**
			 * Read encoded file
			 */

			encodedMessage = AlphabetUtils.formalize(FileUtils
					.readFile(encoded));

			/**
			 * Traitment
			 * 
			 * parcourir la string à la recherche de mots récurrents (3 lettres
			 * pour commencer) déterminer la distance entre ces occurrences
			 * chercher le diviseur commun
			 * 
			 */

			for (int k = 3; k < 8; k++) {
				for (int i = 0; i < encodedMessage.length(); i++) {

					String str = getStr(encodedMessage, i, k);

					if (str != null) {

//						 System.out.println(i + " : Annalysing text for \"" +
//						 str + "\"");

						List<Integer> rep = getRepetitions(encodedMessage, str);

						if (rep.size() > 1) {
							dico.put(str, rep);
							System.out.println(str + " found !");
						}

					}
				}
			}

			/**
			 * Find the key lenght
			 */

			int keyLenght;

			// for(String str : dico.keySet()) {
			// System.out.println(str + " : " + dico.get(str));
			// }

			Map<Integer, Integer> numbers = new HashMap<>();

			for (int i = 1; i < 20; i++) {
				numbers.put(i, null);
			}

			for (int i : numbers.keySet()) {
				int occ = 0;

				for (String str : dico.keySet()) {
					int diff = dico.get(str).get(1) - dico.get(str).get(0);
					occ += diff % i == 0 ? 1 : 0;
				}

				numbers.put(i, occ);
			}

			 for(int i : numbers.keySet()) {
			 System.out.println(i + " ==> " + numbers.get(i));
			 }

			keyLenght = getKeySize(numbers);

			System.out.println("KEY SIZE FOUND : " + keyLenght);
			
			if(keyLenght == 0) {
				System.out.println("Impossible to find the key, sorry :)");
				return;
			}

			/**
			 * Find key
			 */

			StringBuilder[] array = new StringBuilder[keyLenght];

			for (int i = 0; i < encodedMessage.length(); i += keyLenght) {
				if (encodedMessage.length() > i + keyLenght) {
					for (int j = 0; j < keyLenght; j++) {
						if (i == 0) {
							array[j] = new StringBuilder();
						}
						array[j].append(encodedMessage.charAt(i + j));
					}
				}

			}

			Map<Character, Integer> frequencyArray = MapUtils.sortByValues(
					fillFrequencyArray(new File("frequencySample.txt")), false);

			List<Character> keys = new ArrayList<Character>(
					frequencyArray.keySet());

			StringBuilder keySb = new StringBuilder();
			
			for (int i = 0; i < keyLenght; i++) {
				Map<Character, Integer> encodedFrequencyArray = MapUtils
						.sortByValues(
								fillEncodedFrequencyArray(array[i].toString()),
								false);

				List<Character> encodedKeys = new ArrayList<Character>(
						encodedFrequencyArray.keySet());

				int position = AlphabetUtils.alphabet.indexOf(encodedKeys.get(0))
						- AlphabetUtils.alphabet.indexOf(keys.get(0));
				
				char c = position < 0 ? 
						AlphabetUtils.alphabet.charAt(position + AlphabetUtils.alphabet.length()) : 
							AlphabetUtils.alphabet.charAt(position);
						
//				System.out.println(position);
//				System.out.println(keys.get(0));
//				System.out.println(encodedKeys.get(0));
//				System.out.println();

				keySb.append(c);
			}
			
			key = keySb.toString();
			
			System.out.println("KEY FOUND : " + key);
			

			/**
			 * Write found key
			 */

			FileUtils.writeFile(foundKey, key);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getStr(String message, int position, int lenght) {
		if (position + lenght >= message.length()) {
			return null;
		}
		return message.substring(position, position + lenght);
	}

	private List<Integer> getRepetitions(String message, String searchFor) {
		int lastPosition = -1;
		List<Integer> positions = new ArrayList<>();

		while ((lastPosition = message.indexOf(searchFor, lastPosition)) != -1) {
			positions.add(lastPosition);
			lastPosition += searchFor.length();
			// System.out.println("\n" + lastPosition);
		}

		return positions;
	}

	private int getKeySize(Map<Integer, Integer> map) {

		map = MapUtils.sortByValues(map, false);
		map.remove(1);

		int value = 0;
		int index = 0;
		for (Integer i : map.keySet()) {
//			System.out.println("\t" + i + " ==> " + map.get(i));
//			System.out.println("\t index : " + index);
			if (value == 0) {
				value = map.get(i);
				index = i;
			} else if (i > index) {
				if (map.get(i) >= (value - (value * 0.2))) {
					value = map.get(i);
				} else {
					return index;
				}
				index = i;
			}
		}

		return index;
	}

	public Map<Character, Integer> fillFrequencyArray(File frequencySample) {

		Map<Character, Integer> frequencyArray = new HashMap<>();

		for (int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
			frequencyArray.put(AlphabetUtils.alphabet.charAt(i), 0);
		}

		try {
			String content = FileUtils.readFile(frequencySample);

			// System.out.println("Clear content : " + content.substring(0, 50)
			// + " ...");

			content = AlphabetUtils.formalize(content).trim();

			for (int i = 0; i < content.length(); i++) {
				char c = Character.toUpperCase(content.charAt(i));
				if (frequencyArray.get(c) != null) {
					frequencyArray.put(c, frequencyArray.get(c) + 1);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return frequencyArray;
	}

	public Map<Character, Integer> fillEncodedFrequencyArray(
			String encodedFrequencySample) {

		Map<Character, Integer> encodedFrequencyArray = new HashMap<>();

		for (int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
			encodedFrequencyArray.put(AlphabetUtils.alphabet.charAt(i), 0);
		}

		String content = encodedFrequencySample;

//		System.out.println("Encoded content : " + content.substring(0, 50)
//				+ " ...");

		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (encodedFrequencyArray.get(c) != null) {
				encodedFrequencyArray.put(c, encodedFrequencyArray.get(c) + 1);
			}
		}

		return encodedFrequencyArray;
	}

}
