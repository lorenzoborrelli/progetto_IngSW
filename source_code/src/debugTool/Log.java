package debugTool;
/*Federico Millefanti - Lorenzo Borrelli*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
	private static String fileLogName;
	private static FileWriter file;
	
	public Log(String fileLogName){
		this.fileLogName = fileLogName;
		
		try {
			file = new FileWriter(fileLogName, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Log() {
		try {
			file = new FileWriter(fileLogName, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/*@	requires file != null
	 * 	requires title != null
	 * 
	 * 
	 * @*/
	public void newLog(String title, String text){
		// scrittura log su file
		
		
		try {
			BufferedWriter bw = new BufferedWriter(file);
			bw.write(title + " - " + text + "\n");
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
