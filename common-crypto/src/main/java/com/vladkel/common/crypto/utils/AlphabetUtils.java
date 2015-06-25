package com.vladkel.common.crypto.utils;

public class AlphabetUtils {

	public final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";// ,.:;\"'";
	
	public static String formalize(String str) {
		str = str.toUpperCase();
		return str.replaceAll("[^ABCDEFGHIJKLMNOPQRSTUVWXYZ ,.:;\"']", "");
	}
}
