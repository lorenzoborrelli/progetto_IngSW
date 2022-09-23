package pages;

/*Federico Millefanti - Lorenzo Borrelli*/

import application.userLogged;
import dbManagement.DBConnector;
import dbManagement.Querys;
import debugTool.Log;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegistrationPage extends Pages {

	@Override
	public void show() {
		try {
			Parent mainRoot = FXMLLoader.load(getClass().getResource("/graphicPages/RegistrationPage.fxml"));
			scene = new Scene(mainRoot);
			getStage().setScene(scene);
			getStage().show();
			
			if(!(new userLogged()).isLogged())
				(new LoginPage()).show();
			
			if(!(new userLogged()).isAdmin())
				(new ElectorPage()).show();
			
			(new Log()).newLog("Registration Page", "Open");
		}
		catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		
	}

	
	
	public void backClicked(MouseEvent e) {
		(new AdminPage()).show();
	}
	
	
	public void createClicked(MouseEvent e) {
		Label status = (Label)scene.lookup("#status");
		
		String name = ((TextField)scene.lookup("#name")).getText();
		if(name == null || name.equals("")) { status.setText("Inserire un nome"); return; }
		
		String surname = ((TextField)scene.lookup("#surname")).getText();
		if(surname == null || surname.equals("")) { status.setText("Inserire un cognome"); return; }
		
		String cf = ((TextField)scene.lookup("#cf")).getText();
		if(cf == null || cf.equals("")) { status.setText("Inserire il codice fiscale"); return; }
		
		String email = ((TextField)scene.lookup("#email")).getText();
		if(email == null || email.equals("")) { status.setText("Inserire la mail"); return; }
		if(!email.contains("@") || !email.contains(".")) { status.setText("Inserire una mail valida"); return; }
		
		String password = ((TextField)scene.lookup("#password")).getText();
		if(password == null || password.equals("")) { status.setText("Inserire la password"); return; }
		
		boolean isAdmin = false;
		if( ((RadioButton)scene.lookup("#elector")).isSelected() )
			isAdmin = false;
		else
			isAdmin = true;
		
		if(!(new DBConnector()).isConnected()) return;
		(new Querys()).registerUser(  name, surname, cf, isAdmin, email, password  );
		
		(new Log()).newLog("Registration Page", "Inserimento utente "+name+" "+surname);
		
		(new AdminPage()).show();
	}
	
}
