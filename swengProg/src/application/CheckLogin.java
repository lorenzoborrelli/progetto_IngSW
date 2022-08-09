package application;

import baseClasses.User;
import dbManagement.Querys;

public class CheckLogin {		// gestore della pagina di login
	public CheckLogin(){
		
	}
	
	public static boolean checkCredentials(String email, String psw){
		System.out.println("Controllo credenziali");
		
		User u;
		
		Encryption enc = new Encryption();
		String password = enc.getHash(psw);
		System.out.println("email: "+email+" psw: "+psw+" enc: "+password);
		
		if( (u = Querys.checkCredentials(email, password)) != null ){
			new userLogged(u);
			return true;
		}
		else {
			return false;
		}
	}
}



