package edu.utdallas.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IndexFileManager {
	
	
	public static int writeObjectFile(String indexFile, Object indexMap ) {
		File file = new File(indexFile);
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		if (file.exists()) {
			file.delete();
		}
		try {
			fileOutputStream = new FileOutputStream(file);

			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(indexMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				objectOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
		return 0;
	}
	
	
	
	public static Object readObjectFile(String indexFile) {

		File file = new File(indexFile);
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		try {
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			Object obj = objectInputStream.readObject();
			return obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				objectInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}


	
	public static int writeIndexFile(String indexFile, Object indexMap,Object docStatsList) {
		File file = new File(indexFile);
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		if (file.exists()) {
			file.delete();
		}
		try {
			fileOutputStream = new FileOutputStream(file);

			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(indexMap);
			objectOutputStream.writeObject(docStatsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				objectOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
		return 0;
	}
	
	public static Object readIndexFile(String indexFile) {

		File file = new File(indexFile);
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		try {
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			Object obj = objectInputStream.readObject();
			return obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				objectInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}



}
