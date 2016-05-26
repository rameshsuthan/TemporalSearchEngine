package edu.utdallas.fileio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadTitle {
	
	public  static String readFile(String file) {
		StringBuffer fileData=new StringBuffer();
		if (FileUtil.isFilePresent(file)) {
			BufferedReader bufferedReader;
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					line=line.trim();
					if(!line.isEmpty()){
						fileData.append(line);
					}

				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return fileData.toString();

		} else {
			return null;
		}

	}


}
