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

public class CategoricalVotePage extends Pages {
	private static Session s;
	private static Vector<Candidato> candidates;
	private static Vector<Partito> factions;

	public CategoricalVotePage() {}
	public CategoricalVotePage(Session s) {
		this.s = s;
	}
	
	@Override
	public void show() {
		try {
			Parent mainRoot = FXMLLoader.load(getClass().getResource("/graphicPages/CategoricalVotePage.fxml"));
			scene = new Scene(mainRoot);
			getStage().setScene(scene);
			getStage().show();
			
			fillCand();
			Label title = (Label)scene.lookup("#title");
			title.setText("Elezione categorica "+s.getName());
			
			(new Log()).newLog("Categorical vote Page", "Open");
		}
		catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	public void fillCand() {
		candidates = Querys.getCandidates(s);
		factions = Querys.getFactions(s);
		
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
	}
	
	
	public void sessionFClicked(MouseEvent e) {
		Button conf = (Button)scene.lookup("#confirmButton");
		conf.setDisable(false);
		
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		lvC.getSelectionModel().clearSelection();
		
		lvC.getItems().clear();
		
		candidates = Querys.getCandidates( factions.get( ((ListView)scene.lookup("#sessionFList")).getSelectionModel().getSelectedIndex() ) );
		
		for(int i=0; i<candidates.size(); i++) {
			lvC.getItems().add(candidates.get(i).toString());
		}
		
		lvC.getSelectionModel().selectFirst();
		
	}
	
	public void sessionCClicked(MouseEvent e) {
		Button conf = (Button)scene.lookup("#confirmButton");
		conf.setDisable(false);
		
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
	
	
	
	

	public void backClicked(MouseEvent e) {
		(new ElectorPage()).show();
	}
	
	public void confirmClicked(MouseEvent e) {
		ListView lvF = (ListView)scene.lookup("#sessionFList");
		ListView lvC = (ListView)scene.lookup("#sessionCList");
		Querys.setCategoricalVote(s, candidates.get( lvC.getSelectionModel().getSelectedIndex() ) );
		
		
		(new Log()).newLog("Categorical vote Page", "Click aggiungi voto");
		
		(new ElectorPage()).show();
		
		
	}

}
