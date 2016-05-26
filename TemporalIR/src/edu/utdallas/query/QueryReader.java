package edu.utdallas.query;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.utdallas.fileio.FileUtil;

public class QueryReader {

	public static ArrayList<Query> readQueryFromFile(String file) {
		ArrayList<Query> queryList = new ArrayList<Query>();
		if (FileUtil.isFilePresent(file)) {
			BufferedReader bufferedReader;
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				String queryStr;
				while ((queryStr = bufferedReader.readLine()) != null) {
					queryStr=queryStr.trim();
					if(!queryStr.isEmpty()){
						Query query=new Query(queryStr);
						queryList.add(query);
					}

				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return queryList;

		} else {
			return null;
		}

	}

}
