package com.vladkel.homophonic.cypher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;
import com.vladkel.common.crypto.utils.MapUtils;

public class HomophonicCypher {
	
	Map<Character, Integer> frequencyArray;
	
	List<Integer> values;

	public HomophonicCypher(File frequencySample) {
		frequencyArray = new HashMap<>();
		values = new ArrayList<>();
		
		for (int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
			frequencyArray.put(AlphabetUtils.alphabet.charAt(i), 0);
		}
		
		for(int i = 0; i < 128; i++) {
			values.add(i);
		}
		
		fillFrequencyArray(frequencySample);
	}
	
	public Boolean encode(File message, File key, File encoded) {
		
		Map<Character, List<Byte>> bytes = new HashMap<Character, List<Byte>>();
		
		/**
		 * Read key
		 */
		
		
		
		try {
			InputStream is = new FileInputStream(key);
			
			byte[] array = IOUtils.toByteArray(is);
			
//			for(byte b : bytes) {
//				System.out.print((char)b + " ==> ");
//				System.out.format("0x%x \n", b);
//			}
			
			int cpt = 0;
			
			for(int i = 0; i < array.length; i++) {
				int size = (int)((char) array[i]);
				bytes.put(AlphabetUtils.alphabet.charAt(cpt), new ArrayList<>());
				for(int j = i+1; j < size + i; j++) {
					bytes.get(AlphabetUtils.alphabet.charAt(cpt)).add(array[j]);
				}
				i += size;
				cpt++;
				if(cpt > AlphabetUtils.alphabet.length())
					break;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	
	public boolean generateKey(File keyFile) {
				
		Map<Character, List<Byte>> bytes = new HashMap<Character, List<Byte>>();
		
		try {
			int cpt = 0;
			for(Character key : frequencyArray.keySet()) {
				System.out.println(key);
				if(cpt < frequencyArray.size()/10) {
					bytes.put(key, getBytesForChar(5));
				} else if(cpt < frequencyArray.size()/8) {
					bytes.put(key, getBytesForChar(4));
				} else if(cpt < frequencyArray.size()/6) {
					bytes.put(key, getBytesForChar(3));
				} else if(cpt < frequencyArray.size()/4) {
					bytes.put(key, getBytesForChar(2));
				} else {
					bytes.put(key, getBytesForChar(1));
				}
				
				//System.out.println(key + " ==> "  + frequencyArray.get(key));
				cpt++;
			}
			
			List<Byte> list = new ArrayList<>();
			for(Character key : bytes.keySet()) {
				list.add( (byte)(char)bytes.get(key).size());
				for(byte b : bytes.get(key)) {
					list.add(b);
				}
			}
			list.add( (byte)(char)'0');
			
			
			FileOutputStream output = new FileOutputStream(keyFile);
			Byte [] content = list.toArray(new Byte[list.size()]);
			IOUtils.write(ArrayUtils.toPrimitive(content), output);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public List<Byte> getBytesForChar(int weight) {
		List<Byte> list = new ArrayList<>();
		for(int i = 0; i < weight; i++) {
			int value = getRandomInt();
			System.out.print(value + " ==> ");
			byte b = (byte) ((char)value);
			System.out.format("0x%x \n", b);
			list.add(b);
		}
		return list;
	}
	
	public int getRandomInt() {
		Random rd = new Random();
		return values.remove(rd.nextInt(values.size()));
	}
	
	
	public void fillFrequencyArray(File frequencySample) {
		try {
			String content = FileUtils.readFile(frequencySample);
			
			content = AlphabetUtils.formalize(content).trim();

			System.out.println("Clear content : " + content.substring(0, 50) + " ...");

			for (int i = 0; i < content.length(); i++) {
				char c = Character.toUpperCase(content.charAt(i));
				if (frequencyArray.get(c) != null) {
					frequencyArray.put(c, frequencyArray.get(c) + 1);
				}
			}
			
			frequencyArray = MapUtils.sortByRevertValues(frequencyArray);

			//displayFrequencyArray();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void displayFrequencyArray() {
		for (Character c : frequencyArray.keySet()) {
			System.out.println("\t" + c + " ==> " + frequencyArray.get(c));
		}
	}
	
}
