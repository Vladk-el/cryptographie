package com.vladkel.common.crypto.utils;

public class AlphabetUtils {

	public final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";// ,.:;\"'";
	
	public static String formalize(String str) {
		str = str.toUpperCase();
		return str.replaceAll("[^ABCDEFGHIJKLMNOPQRSTUVWXYZ ,.:;\"']", "");
	}
	
	public static String[] splitByLenght(String str, int lenght) {
		return str.split("(?<=\\G.{" + lenght + "})");
	}
	
	public static StringBuilder[] splitByLenghtToStringBuilder(String str, int lenght) {
		String [] array = str.split("(?<=\\G.{" + lenght + "})");
		StringBuilder [] builders = new StringBuilder[array.length];
		for(int i = 0; i < array.length; i++) {
			builders[i] = new StringBuilder();
			builders[i].append(array[i]);
		}
		return builders;
	}
	
}
