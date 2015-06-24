package com.vladkel.common.crypto.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	public static String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String str;
		
		while((str = br.readLine()) != null) {
			sb.append(str);
		}
		
		br.close();
		
		return sb.toString();
	}
	
	public static void writeFile(File file, String content) throws IOException {
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.flush();
		bw.close();
		fw.close();
	}
}
