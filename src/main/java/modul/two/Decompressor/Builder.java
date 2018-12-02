package modul.two.Decompressor;

import modul.two.Compressor.Bit;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

class Builder {
	private StringBuilder temp;
	private StringBuilder result;
	private LinkedList<Byte> decompressedContent;
	private String fileName;
	private Map<String, Integer> table;


	Builder() {
		temp = new StringBuilder();
		result = new StringBuilder();
		decompressedContent = new LinkedList<>();
	}


	DecompressionResult build() {
		byte[] contentBytes = new byte[decompressedContent.size()];
		Iterator<Byte> byteIterator = decompressedContent.iterator();
		for (int i = 0; byteIterator.hasNext(); i++) {
			contentBytes[i]=byteIterator.next();
		}
		return new DecompressionResult(fileName, contentBytes);
	}

	void setFileName(String nameOfDecompressedFile) {
		fileName = nameOfDecompressedFile;
	}

	void add(Bit next) {
		temp.append(next.ordinal());
		if (table.containsKey(temp.toString())) {
			decompressedContent.add(table.get(temp.toString()).byteValue());
			result.append((char) table.get(temp.toString()).intValue());
			temp.setLength(0);
		}
	}


	void setTable(Map<String, Integer> tableForDecompression) {
		table = tableForDecompression;
	}

	;
}
