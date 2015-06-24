package com.vladkel.common.crypto.interfaces;

import java.io.File;

public interface IAttack {

	void findKey(File encoded, File foundKey);
	
}
