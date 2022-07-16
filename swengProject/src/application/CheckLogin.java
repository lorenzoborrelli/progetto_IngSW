package application;

public class CheckLogin {		// gestore della pagina di login
	public CheckLogin(){
		
	}
	
	public static checkCredentials(String email, String psw){
		if( Querys.checkCredentials(email, psw) ){
			
		}
	}
}
