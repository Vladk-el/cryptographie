package com.vladkel.cesar.attack;

import java.io.File;

public class Main {
	
	static String frequencySample = "frequencySample.txt";
	static String encoded = "encoded.txt";
	static String encodedArticle = "encodedArticle.txt";
	static String foundKey = "foundKey.txt";
	static String dico = "dico.txt";

	public static void main(String[] args) {

		CesarAttack attack = new CesarAttack();
		
		attack.findKey(new File(encoded), new File(foundKey), new File(dico));
		
	}

}
