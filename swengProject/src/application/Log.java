package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
	private final String fileLogName;
	private static File file;
	
	public Log(String fileLogName){
		this.fileLogName = fileLogName;
		file = new File("logProject.log");
		
		// apertura file in append
	}
	
	public void newLog(String title, String text){
		// scrittura log su file
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(title + " - " + text);
			bw.flush();
			bw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		// chiudi file
	}
	
	
	
	
}
