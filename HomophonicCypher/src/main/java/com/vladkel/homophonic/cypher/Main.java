package com.vladkel.homophonic.cypher;

import java.io.File;

public class Main {
	
	public static String frequencySample = "frequencySample.txt";
	public static String keyFile = "myKey.hex";

	public static void main(String[] args) {
		
		HomophonicCypher cypher = new HomophonicCypher(new File(frequencySample));
		
		cypher.generateKey(new File(keyFile));
		
		cypher.encode(null, new File(keyFile), null);
		
	}

}
