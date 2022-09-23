package application;

import baseClasses.User;
import dbManagement.DBConnector;
import dbManagement.Querys;

public class CheckLogin {		// gestore della pagina di login
	public CheckLogin(){
		
	}
	
	public static boolean checkCredentials(String email, String psw){
		System.out.println("Controllo credenziali");
		
		if(email == null || psw == null) {
			throw new NullPointerException();
		}
		
		if(email.equals("") || psw.equals("")) {
			return false;
		}
		
		User u;
		
		Encryption enc = new Encryption();
		String password = enc.getHash(psw);
		//System.out.println("email: "+email+" psw: "+psw+" enc: "+password);
		
		try {
			if(!(new DBConnector()).isConnected()) return false;
				
			Querys q = new Querys();
			
			if( (u = q.checkCredentials(email, password)) != null ){
				new userLogged(u);
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}



