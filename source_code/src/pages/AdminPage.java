package pages;

import java.util.Vector;

import application.userLogged;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class AdminPage extends Pages {
	private static Session s;
	private static Vector<Candidato> candidates;
	private static Vector<Partito> factions;

	public AdminPage() {}
	public AdminPage(Session s) {
		this.s = s;
	}
	
	@Override
	public void show() {
		try {
			Parent mainRoot = FXMLLoader.load(getClass().getResource("/graphicPages/AdminPage.fxml"));
			scene = new Scene(mainRoot);
			getStage().setScene(scene);
			getStage().show();
			
			if(!(new userLogged()).isLogged())
				(new LoginPage()).show();
			
			if(!(new userLogged()).isAdmin())
				(new ElectorPage()).show();
			
			fillCand();
			//Label title = (Label)scene.lookup("#title");
			//title.setText("Elezione categorica "+s.getName());
			
			((ListView)scene.lookup("#sessionFList")).getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		}
		catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			(new Log()).newLog("Admin Page", "Open");
		}
	}
	
	
	public void fillCand() {
		candidates = (new Querys()).getCandidates();
		factions = (new Querys()).getFactions();
		
		ListView lvF = (ListView)scene.lookup("#sessionFList");
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		lvF.getItems().clear();
		lvC.getItems().clear();

		for(int i=0; i<factions.size(); i++) {
			lvF.getItems().add(factions.get(i).toString());
		}
		for(int i=0; i<candidates.size(); i++) {
			lvC.getItems().add(candidates.get(i).toString());
		}
		
		lvF.getSelectionModel().selectFirst();
		lvC.getSelectionModel().selectFirst();
	}
	
	
	public void sessionFClicked(MouseEvent e) {
		//Button conf = (Button)scene.lookup("#confirmButton");
		//conf.setDisable(false);
		(new Log()).newLog("Admin Page", "Click lista Partiti");
		
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		lvC.getSelectionModel().clearSelection();
		
		lvC.getItems().clear();
		
		candidates = (new Querys()).getCandidates( factions.get( ((ListView)scene.lookup("#sessionFList")).getSelectionModel().getSelectedIndex() ) );
		
		for(int i=0; i<candidates.size(); i++) {
			lvC.getItems().add(candidates.get(i).toString());
		}

	}
	
	public void sessionCClicked(MouseEvent e) {
		//Button conf = (Button)scene.lookup("#confirmButton");
		//conf.setDisable(false);
		(new Log()).newLog("Admin Page", "Click lista Candidati");
		
		// seleziono nella lista dei partiti il partito del candidato che ho selezionato
		
		ListView lvF = (ListView)scene.lookup("#sessionFList");
		lvF.getSelectionModel().clearSelection();
		
		int facPos = 0;
		for(int i=0; i<factions.size(); i++) {
			if( factions.get(i).equals(	candidates.get( ((ListView)scene.lookup("#sessionCList")).getSelectionModel().getSelectedIndex() ).getPartito() ) ) {
				facPos = i;
				break;
			}
		}
		lvF.getSelectionModel().select( facPos );
	}
	
	
	
	public void addFClicked(MouseEvent e) {
		(new Log()).newLog("Admin Page", "Click aggiungi partito");
		System.out.println("Aggiungi partito "+((TextField)scene.lookup("#addFName")).getText());
		
		(new Querys()).addFaction( ((TextField)scene.lookup("#addFName")).getText() );
		
		fillCand();
	}
	
	public void addCClicked(MouseEvent e) {
		(new Log()).newLog("Admin Page", "Click aggiungi candidato");
		String name = ((TextField)scene.lookup("#addCName")).getText();
		String surname = ((TextField)scene.lookup("#addCSurname")).getText();
		int factionID = factions.get(  ((ListView)scene.lookup("#sessionFList")).getSelectionModel().getSelectedIndex()  ).getId();
		
		(new Querys()).addCandidate( name, surname, factionID );
		
		fillCand();
	}
	

	
	public void addVotClicked(MouseEvent e) {
		(new Log()).newLog("Admin Page", "Click aggiungi sessione di voto");
		int type = 0;
		if( ((RadioButton)scene.lookup("#rb1")).isSelected() ) type = 1;
		else if( ((RadioButton)scene.lookup("#rb2")).isSelected() ) type = 2;
		else if( ((RadioButton)scene.lookup("#rb3")).isSelected() ) type = 3;
		else if( ((RadioButton)scene.lookup("#rb4")).isSelected() ) type = 4;
		
		ObservableList ol = ((ListView)scene.lookup("#sessionFList")).getSelectionModel().getSelectedIndices();
		
		boolean quorum = false;
		if( ((RadioButton)scene.lookup("#quorumSi")).isSelected() ) quorum = true;
		else quorum = false;
		
		Vector<Integer> factionIDs = new Vector<Integer>(ol.size());
		for(int i=0; i<ol.size(); i++) {
			factionIDs.add( factions.get( (int) ol.get(i) ).getId() );
		}
		(new Querys()).addSession( type, ((TextField)scene.lookup("#nomeVotazione")).getText() , factionIDs , quorum );
		
		(new ElectorPage()).show();
	}
	
	
	
	
	
	
	
	public void logoutClicked(MouseEvent e) {
		(new Log()).newLog("Admin Page", "Logout");
		(new userLogged()).logout();
		(new LoginPage()).show();
	}

	
	public void viewSessionsClicked(MouseEvent e) {
		(new Log()).newLog("Admin Page", "View sessions");
		(new ElectorPage()).show();
	}
	
	public void registrationClicked(MouseEvent e) {
		(new Log()).newLog("Admin Page", "Click Registrazione utenti");
		(new RegistrationPage()).show();
	}
	
	public void terminateClicked(MouseEvent e) {
		(new Log()).newLog("Admin Page", "Click termina sessione");
		(new Querys()).terminateSession(s);
	}
	
	
}
