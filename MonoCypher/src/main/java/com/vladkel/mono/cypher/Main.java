package com.vladkel.mono.cypher;

import java.io.File;

import com.vladkel.common.crypto.interfaces.ICypher;

public class Main {
	
	private static String keyFile = "myKey.txt";
	private static String messageFile = "original.txt";
	private static String messageFileEncoded = "encoded.txt";
	private static String messageFileDecoded = "decoded.txt";
	private static String frequencySample = "frequencySample.txt";
	private static String foundKey = "foundKey.txt";
	private static String encodedArticle = "encodedArticle.txt";
	
	private static ICypher cypher;
	
	public static void main(String [] args) {
		
		cypher = new MonoCypher();
		
		//fullGeneration();
		
		separateGeneration();
	}
	
	
	public static void fullGeneration() {
		
		if(cypher.generateKey(new File(keyFile))) {
			if(cypher.encode(new File(messageFile), new File(keyFile), new File(messageFileEncoded))) {
				if(cypher.decode(new File(messageFileEncoded), new File(keyFile), new File(messageFileDecoded))) {
					System.out.println("Processus ended properly");
				}
			}
		}
	}
	
	public static void separateGeneration() {
		
//		cypher.generateKey(new File(keyFile));
//		
//		cypher.encode(new File(frequencySample), new File(keyFile), new File(messageFileEncoded));
		
		cypher.decode(new File(messageFileEncoded), new File(foundKey), new File(messageFileDecoded));
		
		
	}
	
}
