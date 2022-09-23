package pages;

import java.util.Vector;

import baseClasses.Candidato;
import baseClasses.Partito;
import baseClasses.Session;
import dbManagement.Querys;
import debugTool.Log;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ReferendumVotePage extends Pages {
	private static Session s;
	private static int yesOrNo = 0;	// 1-> yes, 0->no

	public ReferendumVotePage() {}
	public ReferendumVotePage(Session s) {
		this.s = s;
	}
	
	@Override
	public void show() {
		try {
			Parent mainRoot = FXMLLoader.load(getClass().getResource("/graphicPages/ReferendumVotePage.fxml"));
			scene = new Scene(mainRoot);
			getStage().setScene(scene);
			getStage().show();
			
			Label title = (Label)scene.lookup("#title");
			title.setText("Referendum "+s.getName());
			
			(new Log()).newLog("Referendum vote Page", "Open");
		}
		catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	
	
	public void siClicked(MouseEvent e) {
		Button bsi = (Button)scene.lookup("#si");
		Button bno = (Button)scene.lookup("#no");
		bsi.setDisable(true);
		bno.setDisable(false);
		yesOrNo = 1;
		
		Button bconf = (Button)scene.lookup("#confirmButton");
		bconf.setDisable(false);
	}
	
	
	
	
	public void noClicked(MouseEvent e) {
		Button bsi = (Button)scene.lookup("#si");
		Button bno = (Button)scene.lookup("#no");
		bsi.setDisable(false);
		bno.setDisable(true);
		yesOrNo = 0;
		
		Button bconf = (Button)scene.lookup("#confirmButton");
		bconf.setDisable(false);
	}
	
	
	


	public void backClicked(MouseEvent e) {
		(new ElectorPage()).show();
	}
	
	
	
	public void confirmClicked(MouseEvent e) {
		(new Querys()).setReferendumVote(s, yesOrNo);
		
		(new Log()).newLog("Referendum vote Page", "Inserimento voto");
		
		(new ElectorPage()).show();
	}

}
