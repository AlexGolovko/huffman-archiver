package com.gmail.golovkobalak.Decompressor;

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
