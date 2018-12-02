package modul.two.Compressor;

import java.util.ArrayList;

class CompressionResult {
	private final String fileName;
	private final ArrayList<Integer> bytes;

	private final int initialSize;

	 CompressionResult(String fileName, ArrayList<Integer> bytes,int bitCounter) {
		this.fileName = fileName;
		this.bytes = bytes;
		this.initialSize = bitCounter;
	}

	static Builder newBuilder() {
		return new Builder();
	}


	String getFileName() {
		return fileName;
	}

	int[] getBytes() {
		return bytes.stream().mapToInt(i -> i).toArray();
	}

	int getInitialSize() {
		return initialSize;
	}

}

