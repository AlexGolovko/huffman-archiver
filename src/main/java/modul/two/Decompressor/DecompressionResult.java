package modul.two.Decompressor;

import java.io.File;
import java.util.LinkedList;

class DecompressionResult {
	private final String fileName;

	private  byte[] result;

	 DecompressionResult(String fileName, byte[] result) {
		 this.fileName = fileName;
		 this.result=result;

	}

	static Builder newBuilder(){
	 	return new Builder();
	}
	byte[] getDecompressedBytes() {
	 	return result;
	}

	String getFileName() {
		return fileName;
	}

}
