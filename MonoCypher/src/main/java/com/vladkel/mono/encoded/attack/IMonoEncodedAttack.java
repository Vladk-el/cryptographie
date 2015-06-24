package com.vladkel.mono.encoded.attack;

import java.io.File;

public interface IMonoEncodedAttack {

	void findKey(File encoded, File foundKey);
	
}
