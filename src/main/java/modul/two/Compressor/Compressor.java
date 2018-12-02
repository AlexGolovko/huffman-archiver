package modul.two.Compressor;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static modul.two.Compressor.CompressionResult.newBuilder;

public class Compressor {


	public static void compress(File file) {
		byte[] fileContent = readFile(file);
		int[] frequency = countFrequency(fileContent);
		Node root = growTree(frequency);
		Map<Integer, String> dictionary = createDictionary(root);
		Builder builder = newBuilder();
		builder.setFileName(file.getName() + ".hf");
		for (byte aFileContent : fileContent) {
			String temp = dictionary.get( aFileContent&0xFF);

			for (int j = 0; j < temp.length(); j++) {
				builder.addBit(temp.charAt(j) == '0' ? Bit.ZERO : Bit.ONE);
			}
		}
		CompressionResult compressionResult = builder.build();
		writeFile(dictionary, compressionResult);

	}

	private static byte[] readFile(File fileName) {
		byte[] content;
		try (FileInputStream fis = new FileInputStream(fileName)) {
			content = new byte[fis.available()];
			fis.read(content);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	private static int[] countFrequency(byte[] fileContent) {
		int[] frequency = new int[256];
		for (byte aFileContent : fileContent) {

			frequency[aFileContent&0xFF]++;
		}
		return frequency;
	}

	private static Node growTree(int[] frequency) {
		PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getWeight)
		);
		for (int i = 0; i < frequency.length; i++) {
			if (frequency[i] > 0)
				queue.add(new Node(i, frequency[i]));
		}
		while (queue.size() > 1) {
			Node a = queue.poll();
			Node b = queue.poll();
			Node temp = new Node(a.getValue() + b.getValue(), a.getWeight() + b.getWeight());
			temp.setLeft(a);
			temp.setRight(b);
			queue.offer(temp);
		}
		return queue.poll();
	}

	private static Map<Integer, String> createDictionary(Node root) {
		Map<Integer, String> dictionary = new HashMap<>(256);
		getPaths(dictionary, root, new StringBuilder());
		return dictionary;
	}

	private static void getPaths(Map<Integer, String> dictionary, Node node, StringBuilder line) {
		if (node.getLeft() == null && node.getRight() == null) {
			dictionary.put(node.getValue(), line.toString());
			line.setLength(line.length() - 1);
			return;
		}
		if (node.getRight() != null) {
			line.append("1");
			getPaths(dictionary, node.getRight(), line);
		}
		if (node.getLeft() != null) {
			line.append("0");
			getPaths(dictionary, node.getLeft(), line);
		}
		line.setLength(line.length() > 0 ? line.length() - 1 : 0);

	}

	private static void writeFile(Map<Integer, String> table, CompressionResult result) {
		int initialSize = result.getInitialSize();
		int[] content = result.getBytes();
		final File tree = new File(result.getFileName() + ".table");
		try (FileOutputStream compressedFos = new FileOutputStream(result.getFileName());
		     BufferedOutputStream compressedBOS=new BufferedOutputStream(compressedFos);
		     FileOutputStream treeFos = new FileOutputStream(tree)) {
			ObjectOutputStream oos = new ObjectOutputStream(treeFos);
			oos.writeObject(table);
			oos.close();
			compressedBOS.write(initialSize);
			compressedBOS.write(initialSize >> 8);
			compressedBOS.write(initialSize >> 16);
			compressedBOS.write(initialSize >> 24);
			for (int b : content) {
				compressedBOS.write(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


