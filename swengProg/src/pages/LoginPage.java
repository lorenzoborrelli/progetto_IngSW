package pages;

import application.CheckLogin;
import debugTool.Log;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class LoginPage extends Pages{
	
	@FXML
	private Label loginMessage;
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	
	
	
	public LoginPage() {}
	
	public LoginPage(Stage stage) {
		super(stage);
	}
	
	
	@Override
	public void show() {
		try {
			Parent loginRoot = FXMLLoader.load(getClass().getResource("/graphicPages/LoginPage.fxml"));
			scene = new Scene(loginRoot);
			scene.getStylesheets().add(getClass().getResource("/graphicPages/application.css").toExternalForm());
			getStage().setScene(scene);
			getStage().show();
			(new Log()).newLog("Elector Page", "Open");
		}
		catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			//ex.printStackTrace();
		}
	}
	
	
	
	public void loginClick(MouseEvent e) {
		System.out.println("Login");
		(new Log()).newLog("Elector Page", "Click Login");
		
		if(email != null) {
			if(!CheckLogin.checkCredentials(email.getText(), password.getText()))
				loginMessage.setText("Email o password sbagliate");
			else 
				(new ElectorPage()).show();
		}
	}
	
	
	
	public void checkEnter(KeyEvent e) {
		if(e.getCode() == KeyCode.ENTER)
			if(email != null) {
				(new Log()).newLog("Elector Page", "Click invio login");
				
				if(!CheckLogin.checkCredentials(email.getText(), password.getText()))
					loginMessage.setText("Email o password sbagliate");
				else 
					(new ElectorPage()).show();
			}
	}
}


