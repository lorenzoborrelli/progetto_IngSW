package pages;

import java.util.Vector;

import baseClasses.Candidato;
import baseClasses.Partito;
import baseClasses.Session;
import dbManagement.Querys;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class OrdinalVotePage extends Pages {
	private static Session s;
	private static Vector<Candidato> candidates;
	private static Vector<Partito> factions;
	private static int isCandOrFact = 0;	// 1-> candidates, 2->factions

	public OrdinalVotePage() {}
	public OrdinalVotePage(Session s) {
		this.s = s;
	}
	
	@Override
	public void show() {
		try {
			Parent mainRoot = FXMLLoader.load(getClass().getResource("/graphicPages/OrdinalVotePage.fxml"));
			scene = new Scene(mainRoot);
			getStage().setScene(scene);
			getStage().show();
			
			
			candidates = Querys.getCandidates(s);
			factions = Querys.getFactions(s);
			
			fillFact();
			Label title = (Label)scene.lookup("#title");
			title.setText("Elezione ordinale "+s.getName());
		}
		catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	public void fillFact() {
		ListView lvF = (ListView)scene.lookup("#sessionFList");
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		lvF.getItems().clear();
		lvC.getItems().clear();

		for(int i=0; i<factions.size(); i++) {
			lvF.getItems().add(factions.get(i).toString());
		}
	}
	
	public void fillCand() {
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		lvC.getSelectionModel().clearSelection();
		
		lvC.getItems().clear();
		
		candidates = Querys.getCandidates( factions.get( ((ListView)scene.lookup("#sessionFList")).getSelectionModel().getSelectedIndex() ) );
		
		for(int i=0; i<candidates.size(); i++) {
			lvC.getItems().add(candidates.get(i).toString());
		}
	}
	
	
	public void sessionFClicked(MouseEvent e) {
		Button conf = (Button)scene.lookup("#confirmButton");
		conf.setDisable(false);
		
		fillCand();
	}
	
	public void sessionCClicked(MouseEvent e) {
		Button up = (Button)scene.lookup("#upC");
		Button down = (Button)scene.lookup("#downC");
		up.setDisable(false);
		down.setDisable(false);
		
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
	
	
	
	
	
	public void upFClicked(MouseEvent e) {
		ListView lv = (ListView)scene.lookup("#sessionFList");
		int selectedSession = lv.getSelectionModel().getSelectedIndex();
		
		
		if(selectedSession > 0) {
			Partito temp = factions.get(selectedSession);
			factions.set(selectedSession, factions.get(selectedSession-1));
			factions.set(selectedSession-1, temp);
		}
	}
	
	public void downFClicked(MouseEvent e) {
		ListView lv = (ListView)scene.lookup("#sessionFList");
		int selectedSession = lv.getSelectionModel().getSelectedIndex();
		
		if(selectedSession < factions.size()-1) {
			Partito temp = factions.get(selectedSession);
			factions.set(selectedSession, factions.get(selectedSession+1));
			factions.set(selectedSession+1, temp);
		}
	}
	
	
	
	public void upCClicked(MouseEvent e) {
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		int selectedSession = lvC.getSelectionModel().getSelectedIndex();
		
		
		if(selectedSession > 0) {
			Candidato temp = candidates.get(selectedSession);
			candidates.set(selectedSession, candidates.get(selectedSession-1));
			candidates.set(selectedSession-1, temp);

			lvC.getItems().clear();
			for(int i=0; i<candidates.size(); i++) {
				lvC.getItems().add(candidates.get(i).toString());
			}
			lvC.getSelectionModel().select(selectedSession-1);
		}
		
	}
	
	public void downCClicked(MouseEvent e) {
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		int selectedSession = lvC.getSelectionModel().getSelectedIndex();
		
		if(selectedSession < candidates.size()-1) {
			Candidato temp = candidates.get(selectedSession);
			candidates.set(selectedSession, candidates.get(selectedSession+1));
			candidates.set(selectedSession+1, temp);
			
			
			lvC.getItems().clear();
			for(int i=0; i<candidates.size(); i++) {
				lvC.getItems().add(candidates.get(i).toString());
			}
			lvC.getSelectionModel().select(selectedSession+1);
		}
		
	}
	
	


	public void backClicked(MouseEvent e) {
		(new ElectorPage()).show();
	}
	
	
	public void confirmClicked(MouseEvent e) {
		Querys.setOrdinalVote(s, candidates);
		
		(new ElectorPage()).show();
	}

}
