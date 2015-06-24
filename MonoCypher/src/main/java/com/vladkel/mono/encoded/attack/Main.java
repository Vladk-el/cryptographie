package com.vladkel.mono.encoded.attack;

import java.io.File;

public class Main {
	
	static String frequencySample = "frequencySample.txt";
	static String encoded = "encoded.txt";
	static String encodedArticle = "encodedArticle.txt";
	static String foundKey = "foundKey.txt";

	public static void main(String [] args) {
		
		MonoEncodedAttack attack = new MonoEncodedAttack(new File(frequencySample));
		
		attack.fillEncodedFrequencyArray(new File(encoded));
		
		attack.findKey(new File(encoded), new File(foundKey));
		
	}
}
