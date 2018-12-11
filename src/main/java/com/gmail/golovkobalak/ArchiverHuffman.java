package modul.two;

import java.io.File;
import java.io.FileNotFoundException;

import static modul.two.Compressor.Compressor.*;
import static modul.two.Decompressor.Decompressor.*;


public class ArchiverHuffman {
	public String getFileName() {
		return fileName;
	}


	private static String fileName;

	private ArchiverHuffman() {

	}

	public  static void getResult(String fileName) throws FileNotFoundException {
		ArchiverHuffman.fileName=fileName;
		File file = new File(fileName);
		getResult(file);

	}
	public static void getResult(File file) throws FileNotFoundException {
		fileName=file.getName();
		if (file.isDirectory()||!file.exists()) {
			throw new FileNotFoundException();
		}
		if(file.getName().substring(file.getName().lastIndexOf('.')).equals(".hf")) {
			decompress(file);
		} else {
			compress(file);
		}
	}


	public static boolean isArchive() {
		File file=new File(fileName);
		if(file.exists()&&file.isFile()&&fileName.substring(fileName.lastIndexOf('.')).equals(".hf"))return true;
		return false;
	}

}
