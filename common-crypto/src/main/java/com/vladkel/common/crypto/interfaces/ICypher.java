package com.vladkel.common.crypto.interfaces;

import java.io.File;

public interface ICypher {

	Boolean encode(File message, File key, File encoded);
	
	Boolean decode(File crypted, File key, File decoded);
	
	boolean generateKey(File key);
	
}
