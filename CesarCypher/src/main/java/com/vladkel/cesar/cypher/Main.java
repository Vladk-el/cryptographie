package com.vladkel.cesar.cypher;

import java.io.File;

public class Main {
	
	private static String keyFile = "myKey.txt";
	private static String messageFile = "original.txt";
	private static String messageFileEncoded = "encoded.txt";
	private static String messageFileDecoded = "decoded.txt";
	private static String frequencySample = "frequencySample.txt";
	private static String foundKey = "foundKey.txt";
	private static String encodedArticle = "encodedArticle.txt";

	public static void main(String[] args) {
		
		CesarCypher cypher = new CesarCypher();
		
		cypher.encode(new File(messageFile), new File(keyFile), new File(messageFileEncoded));
		
		cypher.decode(new File(messageFileEncoded), new File(keyFile), new File(messageFileDecoded));
		
	}

}
