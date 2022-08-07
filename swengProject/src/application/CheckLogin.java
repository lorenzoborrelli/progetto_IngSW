package application;

public class CheckLogin {		// gestore della pagina di login
	public CheckLogin(){
		
	}
	
	public static boolean checkCredentials(String email, String psw){
		User u;
		if( (u = Querys.checkCredentials(email, psw)) != null ){
			new userLogged(u);
			return true;
		}
		else {
			return false;
		}
	}
}



