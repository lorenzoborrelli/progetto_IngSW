package application;

import dbManagement.DBConnector;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Querys {
	private static DBConnector db;
	
	
	
	public static Sessions getSessions(){
		Sessions ss = new Sessions();
		String query = "SELECT ...;";
		
		try {
			ResultSet rs = db.executeQuery(query);
			
			for(int i=0; i<rs.getFetchSize(); i++){
				//rs.getString()	// da fare le query e il riempimento della sessione adatta
				String sessione = rs.getString("tipoSessione");
				Session s = null;
				if(sessione.equals("votoOrdinale")){
					s = new OrdinalVote(/*blablacar*/);
				}
				else if(sessione.equals("voto categorico")){
					s = new CategoricalVote(/*blablacar*/);
				}
				else if(sessione.equals("referendum")){
					s = new Referendum(/*blablacar*/);
				}
				else{
					rs.next();
					continue;
				}
				ss.addSession(s);
				rs.next();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ss;
	}
	
	
	public static void insertUser(){
		String query = "INSERT INTO User() VALUES ;";
		db.insertQuery(query);
	}
	
	
	public static User checkCredentials(String email, String psw){
		Encryption enc = new Encryption();
		psw = enc.getHash(psw);
		String query = "SELECT id,name,surname,email FROM User WHERE email LIKE \""+email+"\" AND password = \""+psw+"\";";
		try {
			ResultSet rs = db.executeQuery(query);
			if(rs.getFetchSize() == 0){
				return null;
			}
			User u = new User(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), email);
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
