package application;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

import dbManagement.DBConnector;
import dbManagement.Querys;
import debugTool.Log;
import javafx.application.Application;
import javafx.stage.Stage;
import pages.LoginPage;

/*	Per installare javafx, andare su help e cercare e(fx)clipse dal marketplace
 * 	Da Window->Preferences cercare user e andare nella ... seguire guida video youtube
 * 	Installare new sowftware sempre
 * */

public class Main extends Application {
	private static Stage stage;
	
	public static void main(String[] args) {
		(new Log("SwengProject.log")).newLog("STARTING APP", "");
		
		
		DBConnector dbconn = new DBConnector();
		dbconn.openConnection();
		
		new Querys();
		
		
		
		launch();
		
		
	}
	
	@Override
	public void start(Stage stages) {
		stage = stages;
		
		stages.setResizable(false);
		stages.setTitle("Sistema di votazione");
		
		(new LoginPage(stages)).show();
	}
	
	public static Stage getStage() {
		return stage;
	}
}
