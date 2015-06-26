package com.vladkel.common.crypto.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

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
	
	public static byte[] readByte(FileInputStream fis) throws IOException {
		return IOUtils.toByteArray(fis);
	}
	
	public static void writeByte(FileOutputStream fos, byte[] bytes) throws IOException {
		IOUtils.write(bytes, fos);
	}
	
}
