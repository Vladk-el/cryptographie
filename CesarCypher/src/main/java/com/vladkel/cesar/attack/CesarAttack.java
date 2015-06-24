package com.vladkel.cesar.attack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.vladkel.cesar.cypher.CesarCypher;
import com.vladkel.common.crypto.interfaces.IAttack;
import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;

public class CesarAttack{
	
	private String encoded = "encoded.txt";
	private String decoded = "temp_decoded.txt";
	private Map<String, Boolean> dico;
	

	public CesarAttack() {
		super();
		dico = new HashMap<>();	
	}
	
	public void findKey(File encoded, File foundKey, File dict) {
		
		String response = null;
		
		try {
			
			String read = AlphabetUtils.formalize(FileUtils.readFile(dict)).trim();
			
			for(String str : read.split(" ")) {
				dico.put(str, true);
			}
			
			CesarCypher cypher = new CesarCypher();
			
			for(int i = 0; i < AlphabetUtils.alphabet.length(); i++) {
				String decode = cypher.decode(encoded, AlphabetUtils.alphabet.charAt(i), new File(decoded));
				int cpt = 0;
				String [] tab = decode.trim().split(" ");
				for(String str : tab) {
					if(dico.get(str) != null) {
						cpt++;
					}
				}
				System.out.println(cpt + " words found");
				if(cpt >= (0.05 * tab.length)) {
					System.out.println("###### FOUND KEY ######");
					System.out.println(decode);
					System.out.println("###### FOUND KEY ######");
					response = AlphabetUtils.alphabet.charAt(i) + "";
					break;
				}
			}
			
			if(response != null)
				FileUtils.writeFile(foundKey, response);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
