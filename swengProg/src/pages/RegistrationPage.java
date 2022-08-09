package pages;

/*Federico Millefanti - Lorenzo Borrelli*/

import application.userLogged;
import dbManagement.Querys;
import debugTool.Log;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
		String name = ((TextField)scene.lookup("#name")).getText();
		String surname = ((TextField)scene.lookup("#surname")).getText();
		String cf = ((TextField)scene.lookup("#cf")).getText();
		
		String email = ((TextField)scene.lookup("#email")).getText();
		String password = ((TextField)scene.lookup("#password")).getText();
		
		boolean isAdmin = false;
		if( ((RadioButton)scene.lookup("#elector")).isSelected() )
			isAdmin = false;
		else
			isAdmin = true;
		
		Querys.registerUser(  name, surname, cf, isAdmin, email, password  );
		
		(new Log()).newLog("Registration Page", "Inserimento utente "+name+" "+surname);
		
		(new AdminPage()).show();
	}
	
}
