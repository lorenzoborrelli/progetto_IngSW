package pages;

import java.util.Vector;

import baseClasses.Candidato;
import baseClasses.Partito;
import baseClasses.Session;
import dbManagement.Querys;
import debugTool.Log;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

public class CategoricalVotePrefPage extends Pages {
	private static Session s;
	private static Vector<Candidato> candidates;
	private static Vector<Partito> factions;

	public CategoricalVotePrefPage() {}
	public CategoricalVotePrefPage(Session s) {
		this.s = s;
	}
	
	@Override
	public void show() {
		try {
			Parent mainRoot = FXMLLoader.load(getClass().getResource("/graphicPages/CategoricalVotePrefPage.fxml"));
			scene = new Scene(mainRoot);
			getStage().setScene(scene);
			getStage().show();
			
			fillCand();
			Label title = (Label)scene.lookup("#title");
			title.setText("Elezione categorica pref "+s.getName());
			
			((ListView)scene.lookup("#sessionCList")).getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		}
		catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	public void fillCand() {
		candidates = (new Querys()).getCandidates(s);
		factions = (new Querys()).getFactions(s);
		
		ListView lvF = (ListView)scene.lookup("#sessionFList");
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		lvF.getItems().clear();
		lvC.getItems().clear();

		for(int i=0; i<factions.size(); i++) {
			lvF.getItems().add(factions.get(i).toString());
		}
		
	}
	
	
	public void sessionFClicked(MouseEvent e) {
		
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		lvC.getSelectionModel().clearSelection();
		
		lvC.getItems().clear();
		
		candidates = (new Querys()).getCandidates( factions.get( ((ListView)scene.lookup("#sessionFList")).getSelectionModel().getSelectedIndex() ) );
		
		for(int i=0; i<candidates.size(); i++) {
			lvC.getItems().add(candidates.get(i).toString());
		}
		
		lvC.getSelectionModel().selectFirst();
	}
	
	public void sessionCClicked(MouseEvent e) {
		Button conf = (Button)scene.lookup("#confirmButton");
		conf.setDisable(false);
		
		
	}
	


	public void backClicked(MouseEvent e) {
		(new ElectorPage()).show();
	}
	
	public void confirmClicked(MouseEvent e) {
		ListView lvF = (ListView)scene.lookup("#sessionFList");
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		
		Vector<Candidato> cc = new Vector<Candidato>();
		
		ObservableList indexes = lvC.getSelectionModel().getSelectedIndices();
		
		for(int i=0; i<indexes.size(); i++) {
			cc.add( candidates.get( (int) indexes.get(i) ) );
		}
		
		(new Querys()).setCategoricalPrefVote(s, cc);
		
		(new Log()).newLog("Categorical Pref Page", "Inserimento voto");
		
		(new ElectorPage()).show();
	}
}
