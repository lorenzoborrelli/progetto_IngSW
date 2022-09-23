package pages;

import java.util.Vector;

import application.SessionWinner;
import application.userLogged;
import baseClasses.Candidato;
import baseClasses.CategoricalPrefVote;
import baseClasses.CategoricalVote;
import baseClasses.OrdinalVote;
import baseClasses.ReferendumVote;
import baseClasses.Session;
import dbManagement.Querys;
import debugTool.Log;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ElectorPage extends Pages {
	private static Vector<Session> sess;
	
	
	
	
	public ElectorPage() {
		super();
	}
	
	
	@Override
	public void show() {
		try {
			Parent mainRoot = FXMLLoader.load(getClass().getResource("/graphicPages/MainPage.fxml"));
			scene = new Scene(mainRoot);
			getStage().setScene(scene);
			getStage().show();
			
			if(!(new userLogged()).isLogged())
				(new LoginPage()).show();
			
			fillSessions();
			
			((Label)scene.lookup("#name")).setText((new userLogged()).toString());
			
			
			if((new userLogged()).isAdmin()) {
				((Button)scene.lookup("#adminButton")).setVisible(true);
				((Button)scene.lookup("#terminateButton")).setVisible(true);
			}
		}
		catch(Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	public void fillSessions() {
		sess = (new Querys()).getSessions();
		
		ListView lv = (ListView)scene.lookup("#sessionList");
		
		
		for(int i=0; i<sess.size(); i++) {
			String s = "";
			if( sess.get(i).getIsActive() ) {
				if(sess.get(i).getAlreadyVoted())
					s = "|| VOTATO || ";
			}
			else {
				s = "|| SCADUTA || ";
			}
			s += sess.get(i).toString();
			lv.getItems().add(s);
		}
		
		lv.getSelectionModel().selectFirst();
	}
	
	
	
	
	public void sessionClick(MouseEvent e) {
		ListView lv = (ListView)scene.lookup("#sessionList");
		int selectedSession = lv.getSelectionModel().getSelectedIndex();
		System.out.println("Sessione click " + selectedSession );
		
		if(sess != null && selectedSession != -1) {
			Button b = (Button)scene.lookup("#voteButton");
			if(sess.get(selectedSession).getAlreadyVoted() || !sess.get(selectedSession).getIsActive())
				b.setDisable(true);
			else
				b.setDisable(false);
			
			Label ss = (Label)scene.lookup("#sessionStatus");
			if(!sess.get(selectedSession).getIsActive()) {
				
				if(sess.get(selectedSession).isReferendum()) {
					SessionWinner winner = (new Querys()).getWinner(sess.get(selectedSession), true);
					if( winner.getQuorumNonRaggiunto() ) 
						ss.setText("Risultato referendum: Quorum non raggiunto");
					else
						ss.setText("Risultato referendum: Maggioranza per il " + (winner.getResult()?"SI":"NO") + " con " + winner.getNumVoti() + " voti");
				}
				else {
					SessionWinner winner = (new Querys()).getWinner(sess.get(selectedSession), false);
					if( winner.getQuorumNonRaggiunto() ) 
						ss.setText("Risultato elezione: Quorum non raggiunto");
					else
						ss.setText("Risultato elezione: " + winner.getCandidato().toString() + " con " + winner.getNumVoti() + " voti");
				}
			}
			else if(sess.get(selectedSession).getAlreadyVoted())
				ss.setText("Hai già inviato il tuo voto per questa sessione");
			else
				ss.setText("Clicca vota per procedere alla votazione");
		}
	}
	
	public void voteClick(MouseEvent e) {
		ListView lv = (ListView)scene.lookup("#sessionList");
		int selectedSession = lv.getSelectionModel().getSelectedIndex();
		
		System.out.println("Vota: "+sess.get(selectedSession).getClass().getSimpleName());
		(new Log()).newLog("Elector Page", "Vota "+sess.get(selectedSession).getClass().getSimpleName());
		
		
		if(sess.get(selectedSession) instanceof OrdinalVote) {
			(new OrdinalVotePage(sess.get(selectedSession))).show();
		}
		else if(sess.get(selectedSession) instanceof CategoricalVote) {
			(new CategoricalVotePage(sess.get(selectedSession))).show();
		}
		else if(sess.get(selectedSession) instanceof CategoricalPrefVote) {
			(new CategoricalVotePrefPage(sess.get(selectedSession))).show();
		}
		else if(sess.get(selectedSession) instanceof ReferendumVote) {
			(new ReferendumVotePage(sess.get(selectedSession))).show();
		}
	}

	
	
	public void adminClicked(MouseEvent e) {
		(new AdminPage()).show();
	}

	
	public void logoutClicked(MouseEvent e) {
		(new userLogged()).logout();
		(new LoginPage()).show();
	}
	
	public void terminateClicked(MouseEvent e) {
		ListView lv = (ListView)scene.lookup("#sessionList");
		int selectedSession = lv.getSelectionModel().getSelectedIndex();
		
		(new Querys()).terminateSession(sess.get(selectedSession));
		
		(new ElectorPage()).show();
	}
	
	
}



