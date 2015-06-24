package com.vladkel.common.crypto.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUtils {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<Character, Integer> sortByRevertValues(Map<Character, Integer> frequencyArray2) {
		List<Character> list = new LinkedList(frequencyArray2.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator<Object>() {
			public int compare(final Object o1, final Object o2) {
				return -((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Integer> sortByStringRevertValues(Map<String, Integer> frequencyArray2) {
		List<Character> list = new LinkedList(frequencyArray2.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator<Object>() {
			public int compare(final Object o1, final Object o2) {
				return -((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}
	
}
