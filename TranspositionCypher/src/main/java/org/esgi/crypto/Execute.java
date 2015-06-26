package org.esgi.crypto;

import java.io.File;

import com.vladkel.common.crypto.utils.AlphabetUtils;
import com.vladkel.common.crypto.utils.FileUtils;

public class Execute implements IExecute {

	@Override
	public void execute(File encode, File key, File decoded) {
		
		String encodedContent, keyStr, decodedMessage;
		
		try {
			
			/**
			 * Read encoded file
			 */
			
			encodedContent = AlphabetUtils.formalize(FileUtils.readFile(encode));
			
			/**
			 * Traitment
			 */
			
			keyStr = "SUPERCLEDEOUF";
			
			/**
			 * Write key
			 */
			
			FileUtils.writeFile(key, keyStr);
			
			/**
			 * Decode message
			 */
			
			decodedMessage = "hahaha je vous ai bien eu, vous croyiez que ça marchait ? :D";
			
			/**
			 * Write decoded message
			 */
			
			FileUtils.writeFile(decoded, decodedMessage);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
