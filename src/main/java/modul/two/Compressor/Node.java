package modul.two.Compressor;

public class Node {
	private final int value;
	private final int weight;
	private Node left;
	private Node right;


	Node(int value, int weight) {
		this.value = value;
		this.weight = weight;
	}

	int getValue() {
		return value;
	}


	Node getLeft() {
		return left;
	}

	void setLeft(Node left) {
		this.left = left;
	}


	Node getRight() {
		return right;
	}

	void setRight(Node right) {
		this.right = right;
	}

	int getWeight() {
		return weight;
	}
}
