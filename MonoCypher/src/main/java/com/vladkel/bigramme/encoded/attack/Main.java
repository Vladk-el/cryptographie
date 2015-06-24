package com.vladkel.bigramme.encoded.attack;

import java.io.File;

public class Main {

	static String frequencySample = "frequencySample.txt";
	static String encoded = "encoded.txt";
	static String encodedArticle = "encodedArticle.txt";
	static String foundKey = "foundKey.txt";

	public static void main(String [] args) {
		
		BigramEncodedAttack attack = new BigramEncodedAttack(new File(frequencySample));
		
		attack.findKey(new File(encoded), new File(foundKey));
		
	}
}
