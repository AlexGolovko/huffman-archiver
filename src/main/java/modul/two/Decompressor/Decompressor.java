package modul.two.Decompressor;

import modul.two.Compressor.Bit;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static modul.two.Decompressor.DecompressionResult.newBuilder;

public class Decompressor {

	private static int initialSize;
	private static Map<String, Integer> table;


	public static void decompress(File file) {
		byte[] content = readCompressedFile(file);
		Map dictionary = readDictionary(file);
		reverseToTable(dictionary);
		Iterator<Bit> bitIterator = getIterator(content);
		Builder builder = newBuilder();
		builder.setFileName(file.getName().substring(0, file.getName().lastIndexOf('.')));
		builder.setTable(table);
		while (bitIterator.hasNext()) {
			builder.add(bitIterator.next());
		}
		DecompressionResult decompressionContent = builder.build();
		writeResult(decompressionContent);
	}


	private static byte[] readCompressedFile(File file) {
		try (FileInputStream fis = new FileInputStream(file)) {
			initialSize = fis.read();
			initialSize = (fis.read() << 8) + initialSize;
			initialSize = (fis.read() << 16) + initialSize;
			initialSize = (fis.read() << 24) + initialSize;
			byte[] content = new byte[fis.available()];
			fis.read(content);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static Map readDictionary(File file) {
		Map<Integer, String> dictionary = new HashMap<>();
		try (ObjectInputStream oisTable = new ObjectInputStream(new FileInputStream(file.getName() + ".table"))) {
			dictionary = (Map<Integer, String>) oisTable.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictionary;
	}

	private static void reverseToTable(Map<Integer, String> dictionary) {
		table = new HashMap<>();
		for (Map.Entry<Integer, String> entry : dictionary.entrySet()) {
			table.put(entry.getValue(), entry.getKey());
		}

	}

	private static void writeResult(DecompressionResult decompressionResult) {
		try {
			FileOutputStream fis = new FileOutputStream(decompressionResult.getFileName());
			fis.write(decompressionResult.getDecompressedBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Iterator<Bit> getIterator(byte[] bytes) {
		return new ByteIterator(bytes);
	}


	private static class ByteIterator implements Iterator<Bit> {
		private int[] initBytes;
		private int count = 0;


		ByteIterator(byte[] bytes) {
			initBytes = new int[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				initBytes[i] = bytes[i] & 0xFF;
			}
		}

		@Override
		public boolean hasNext() {
			return count < initialSize;
		}

		@Override
		public Bit next() {
			Bit bit = ((initBytes[count / 8] >> 7) & 1) == 1 ? Bit.ONE : Bit.ZERO;
			initBytes[count / 8] = (initBytes[count / 8] << 1);
			count++;
			return bit;
		}
	}


}

