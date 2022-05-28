package application;

import java.util.Vector;

public class Sessions {
	private Vector<Session> sessions;
	
	public Sessions(){
		sessions = new Vector<Session>();
	}
	
	public void addSession(Session s){
		sessions.add(s);
	}
	
	public void clearSessions(){
		sessions.clear();
	}
	
	public String getAllSessions(){
		String s = "";
		for(int i=0; i<sessions.size(); i++){
			s += sessions.get(i).toString() + "\n";
		}
		return s;
	}
}
