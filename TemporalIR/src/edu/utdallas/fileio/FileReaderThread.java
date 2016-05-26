package edu.utdallas.fileio;

import java.io.File;

import edu.utdallas.data.DataTokenizer;

/**
 * @author ramesh
 *  Thread Class for tokening the file
 */
public class FileReaderThread implements Runnable {
	private FileStats fileStats;

	public FileReaderThread(FileStats fileStats) {
		// TODO Auto-generated constructor stub
		this.fileStats=fileStats;
	}

	@Override
	public void run() {
		//System.out.println("Running Thread:"+file.getName());
		DataTokenizer tokenizer= new DataTokenizer(fileStats.getFile(), fileStats);
		tokenizer.start();
	}

}
