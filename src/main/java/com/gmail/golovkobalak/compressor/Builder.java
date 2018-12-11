package com.gmail.golovkobalak.Compressor;

import java.util.ArrayList;

class Builder {
	private String fileName;
	private StringBuilder bitsHolder;
	private ArrayList<Integer> bytes;
	private int bitCounter;




	Builder() {
		bitsHolder=new StringBuilder();
		bytes=new ArrayList<>();
	}

	Builder addBit(Bit bit) {
		if (bitsHolder.length() < 8) {
			bitsHolder.append(bit.ordinal());

		} else {
			bytes.add(Integer.parseInt(bitsHolder.toString(),2));
			bitsHolder.setLength(0);
			bitsHolder.append(bit.ordinal());
		}
		return this;
	}



	Builder setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	CompressionResult build() {
		bitCounter=bytes.size()*8+bitsHolder.length();
		if(bitsHolder.length()!=0){
			for (int i = bitsHolder.length(); i <8 ; i++) {
				bitsHolder.append(0);
			}
			bytes.add(Integer.parseInt(bitsHolder.toString(),2));
		}

		return new CompressionResult(fileName, bytes,bitCounter);
	}
}

