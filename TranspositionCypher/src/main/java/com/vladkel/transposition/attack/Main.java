package com.vladkel.transposition.attack;

import java.io.File;

import com.vladkel.common.crypto.interfaces.IAttack;

public class Main {

	private static String keyFile = "myKey.txt";
	private static String messageFile = "original.txt";
	private static String messageFileEncoded = "encoded.txt";
	private static String messageFileDecoded = "decoded.txt";
	
	private static String frequencySample = "frequencySample.txt";
	private static String foundKey = "foundKey.txt";
	private static String encodedArticle = "encodedArticle.txt";
	
	public static void main(String [] args) {
		
		IAttack attack = new TranspositionAttack(new File(frequencySample));
		
		attack.findKey(new File(messageFileEncoded), new File(foundKey));
	}
}
