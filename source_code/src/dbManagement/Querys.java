package dbManagement;
/*Federico Millefanti - Lorenzo Borrelli*/
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Vector;

import application.Encryption;
import application.SessionWinner;
import application.userLogged;
import baseClasses.Candidato;
import baseClasses.CategoricalPrefVote;
import baseClasses.CategoricalVote;
import baseClasses.OrdinalVote;
import baseClasses.Partito;
import baseClasses.ReferendumVote;
import baseClasses.Session;
import baseClasses.User;
import debugTool.Log;


public class Querys {
	private static DBConnector db;
	
	public Querys(){
		db = new DBConnector();
	}
	
	
	
	
	
	public Vector<Session> getSessions(){
		Vector<Session> ss = new Vector<Session>();
		String query = 		"SELECT s.id AS idSess, s.name AS nomeSessione, vt.name AS tipoSessione, s.isActive AS isActive, (SELECT COUNT(vv.id) FROM vervote vv WHERE idUser="+(new userLogged()).getUtente().getId()+" AND idSession=s.id) AS alreadyVoted "
						+ 	"FROM session s, voteType vt "
						+ 	"WHERE idVoteType=vt.id "
						+ 	"ORDER BY isActive DESC, alreadyVoted ASC;";		// mostrare solo le sessioni di userLogged
		//System.out.println(query);
		try {
			ResultSet rs = db.selectQuery(query);
			
			
			int rowcount = 0;
			if (rs.last()) {
				rowcount = rs.getRow();
				rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			for(int i=0; i<rowcount; i++){
				rs.next();
				//rs.getString()	// da fare le query e il riempimento della sessione adatta
				String sessione = rs.getString("tipoSessione");
				Session s = null;
				if(sessione.equals("votoOrdinale")){
					s = new OrdinalVote(Integer.parseInt(rs.getString("idSess")), rs.getString("nomeSessione"), rs.getBoolean("isActive"));
				}
				else if(sessione.equals("votoCategorico")){
					s = new CategoricalVote(Integer.parseInt(rs.getString("idSess")), rs.getString("nomeSessione"), rs.getBoolean("isActive"));
				}
				else if(sessione.equals("votoCategoricoPref")){
					s = new CategoricalPrefVote(Integer.parseInt(rs.getString("idSess")), rs.getString("nomeSessione"), rs.getBoolean("isActive"));
				}
				else if(sessione.equals("referendum")){
					s = new ReferendumVote(Integer.parseInt(rs.getString("idSess")), rs.getString("nomeSessione"), rs.getBoolean("isActive"));
				}
				else{
					continue;
				}
				s.setAlreadyVoted(rs.getBoolean("alreadyVoted"));
				ss.add(s);
				
			}
			(new Log()).newLog("DB Operations", "Sessioni lette");
		} catch (Exception e) {
			(new Log()).newLog("DB Operations ERROR", "Errore get sessions "+e.getMessage());
			e.printStackTrace();
		}
		
		return ss;
	}
	
	
	
	public User checkCredentials(String email, String psw){
		String query = "SELECT id,name,surname,email,type FROM user INNER JOIN credential ON idUser=user.id WHERE email LIKE \""+email+"\" AND password = \""+psw+"\";";
		try {
			ResultSet rs = db.selectQuery(query);
			
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			
			if( rowcount == 0){
				System.out.println("Utente non trovato");
				(new Log()).newLog("DB Operations", "Utente non trovato");
				return null;
			}
			
			rs.next();
			User u = new User(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), email, rs.getInt("type"));
			
			System.out.println("Credenziali valide");
			(new Log()).newLog("DB Operations", "Credenziali valide");
			return u;
		} catch (Exception e) {
			System.out.println("Errore controllo credenziali "+e.getMessage());
			(new Log()).newLog("DB Operations", "Errore controllo credenziali "+e.getMessage());
			//e.printStackTrace();
		}
		
		return null;
	}

	public Vector<Candidato> getCandidates(Session s) {
		String query = "SELECT c.id,c.name,c.surname,f.id,f.name FROM candidate c INNER JOIN faction f ON f.id=c.factionId INNER JOIN faction_session fs ON fs.idFaction = f.id WHERE fs.idSession = "+s.getId()+";";
		try {
			ResultSet rs = db.selectQuery(query);
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			Vector<Candidato> cc = new Vector<Candidato>(rowcount);
			for(int i=0; i<rowcount; i++) {
				rs.next();
				cc.add( new Candidato(  rs.getInt(1), rs.getString(2), rs.getString(3), new Partito(rs.getInt(4), rs.getString(5))  ) );
			}
			
			(new Log()).newLog("DB Operations", "Candidati letti");
			return cc;
		} catch (Exception e) {
			System.out.println("Errore get candidati "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore get candidates "+e.getMessage());
			//e.printStackTrace();
		}
		return null;
	}

	public Vector<Partito> getFactions(Session s) {
		String query = "SELECT f.id,f.name FROM faction f INNER JOIN faction_session fs ON f.id=fs.idFaction WHERE fs.idSession = "+s.getId()+";";
		try {
			ResultSet rs = db.selectQuery(query);
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			Vector<Partito> cc = new Vector<Partito>(rowcount);
			for(int i=0; i<rowcount; i++) {
				rs.next();
				cc.add( new Partito( rs.getInt(1), rs.getString(2) ) );
			}
			
			(new Log()).newLog("DB Operations", "Partiti letti");
			return cc;
		} catch (Exception e) {
			System.out.println("Errore get factions "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore get factions "+e.getMessage());
			//e.printStackTrace();
		}
		return null;
	}

	public void setOrdinalVote(Session s, Vector<Candidato> candidates) {		// come parametri i vettori riordinati dei partiti e dei candidati
		try {
			for(int i=0; i<candidates.size(); i++) {
				String query = "INSERT INTO vote " + "VALUES (null, " + (candidates.size()-i-1) + ", " + candidates.get(i).getId() + ", " + s.getId() + ")";
				db.insertQuery(query);
				
			}
			
			String query = "INSERT INTO vervote " + "VALUES (null, " + (new userLogged()).getUtente().getId() + ", " + s.getId() + ")";
			db.insertQuery(query);
			
			(new Log()).newLog("DB Operations", "Inserito voto");
		} catch (Exception e) {
			System.out.println("Errore add ord vote "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore add ord vote "+e.getMessage());
			//e.printStackTrace();
		}
		
	}

	public void setCategoricalVote(Session s, Candidato c) {
		try {
			String query = "INSERT INTO vote " + "VALUES (null, 1, " + c.getId() + ", " + s.getId() + ")";
			db.insertQuery(query);
			
			
			query = "INSERT INTO vervote " + "VALUES (null, " + (new userLogged()).getUtente().getId() + ", " + s.getId() + ")";
			db.insertQuery(query);
			(new Log()).newLog("DB Operations", "Inserito voto");
		} catch (Exception e) {
			System.out.println("Errore add cat vote "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore add cat vote "+e.getMessage());
			//e.printStackTrace();
		}
		
	}

	public void setCategoricalPrefVote(Session s, Vector<Candidato> candidates) {
		try {
			for(int i=0; i<candidates.size(); i++) {
				String query = "INSERT INTO vote " + "VALUES (null, 1, " + candidates.get(i).getId() + ", " + s.getId() + ")";
				db.insertQuery(query);
			}
			
			
			String query = "INSERT INTO vervote " + "VALUES (null, " + (new userLogged()).getUtente().getId() + ", " + s.getId() + ")";
			db.insertQuery(query);
			(new Log()).newLog("DB Operations", "Inserito voto");
		} catch (Exception e) {
			System.out.println("Errore add cat pref vote "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore add cat pref vote "+e.getMessage());
			//e.printStackTrace();
		}
	}

	public void setReferendumVote(Session s, int yesOrNo) {
		try {
			String query = "INSERT INTO vote " + "VALUES (null, " + yesOrNo + ", null, " + s.getId() + ")";
			db.insertQuery(query);
			
			query = "INSERT INTO vervote " + "VALUES (null, " + (new userLogged()).getUtente().getId() + ", " + s.getId() + ")";
			db.insertQuery(query);
			(new Log()).newLog("DB Operations", "Inserito voto");
		} catch (Exception e) {
			System.out.println("Errore add ref vote "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore add ref vote "+e.getMessage());
			//e.printStackTrace();
		}
	}

	public Vector<Candidato> getCandidates(Partito partito) {
		String query = "SELECT c.id,c.name,c.surname,f.id,f.name FROM candidate c INNER JOIN faction f ON f.id=c.factionId WHERE f.id="+partito.getId()+";";
		try {
			ResultSet rs = db.selectQuery(query);
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			Vector<Candidato> cc = new Vector<Candidato>(rowcount);
			for(int i=0; i<rowcount; i++) {
				rs.next();
				cc.add( new Candidato(  rs.getInt(1), rs.getString(2), rs.getString(3), new Partito(rs.getInt(4), rs.getString(5))  ) );
			}
			
			(new Log()).newLog("DB Operations", "Candidati per partito letti");
			return cc;
		} catch (Exception e) {
			System.out.println("Errore get cand part "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore get cand part "+e.getMessage());
			//e.printStackTrace();
		}
		return null;
	}

	public Vector<Candidato> getCandidates() {
		String query = "SELECT c.id,c.name,c.surname,f.id,f.name FROM candidate c INNER JOIN faction f ON f.id=c.factionId;";
		try {
			ResultSet rs = db.selectQuery(query);
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			Vector<Candidato> cc = new Vector<Candidato>(rowcount);
			for(int i=0; i<rowcount; i++) {
				rs.next();
				cc.add( new Candidato(  rs.getInt(1), rs.getString(2), rs.getString(3), new Partito(rs.getInt(4), rs.getString(5))  ) );
			}
			
			(new Log()).newLog("DB Operations", "Candidati letti");
			return cc;
		} catch (Exception e) {
			System.out.println("Errore get cand "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore get cand "+e.getMessage());
			//e.printStackTrace();
		}
		return null;
	}

	public Vector<Partito> getFactions() {
		String query = "SELECT f.id,f.name FROM faction f;";
		try {
			ResultSet rs = db.selectQuery(query);
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			Vector<Partito> cc = new Vector<Partito>(rowcount);
			for(int i=0; i<rowcount; i++) {
				rs.next();
				cc.add( new Partito( rs.getInt(1), rs.getString(2) ) );
			}
			
			(new Log()).newLog("DB Operations", "Partiti letti");
			return cc;
		} catch (Exception e) {
			System.out.println("Errore get fact "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore get fact "+e.getMessage());
			//e.printStackTrace();
		}
		return null;
	}

	public void addFaction(String text) {
		try {
			String query = "INSERT INTO faction " + "VALUES (null, \"" + text + "\");";
			db.insertQuery(query);
			(new Log()).newLog("DB Operations", "Partito aggiunto");
		} catch (Exception e) {
			System.out.println("Errore add fact "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore add fact "+e.getMessage());
			//e.printStackTrace();
		}
	}

	public void addCandidate(String name, String surname, int factionID) {
		try {
			String query = "INSERT INTO candidate " + "VALUES (null, \"" + name + "\", \"" + surname + "\", " + factionID + ");";
			db.insertQuery(query);
			
			(new Log()).newLog("DB Operations", "Inserimento candidato");
		} catch (Exception e) {
			System.out.println("Errore add cand "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore add cand "+e.getMessage());
			//e.printStackTrace();
		}
	}

	public void addSession(int type, String text, Vector<Integer> v, boolean quorum) {
		try {
			String query = "INSERT INTO session " + "VALUES (null, \"" + text + "\" , 1 , " + type + ", "+(quorum?1:0)+");";
			db.insertQuery(query);
			
			query = "SELECT MAX(id) FROM session;";
			ResultSet rs = db.selectQuery(query);
			rs.next();
			int sessId = rs.getInt(1);
			
			for(int i=0; i<v.size(); i++) {
				query = "INSERT INTO faction_session " + "VALUES (" + v.get(i) + " , " + sessId + ");";
				db.insertQuery(query);
			}
			(new Log()).newLog("DB Operations", "Inserita sessione");
		} catch (Exception e) {
			System.out.println("Errore add sess "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore add sess "+e.getMessage());
			//e.printStackTrace();
		}
	}

	
	
	
	public SessionWinner getWinner(Session session, boolean isReferendum) {
		try {
			String query;
			if(isReferendum)
				query = "SELECT COUNT(v.id), (SELECT COUNT(v.id) FROM vote v WHERE v.val=1 AND v.idSession="+session.getId()+"), (SELECT COUNT(id) FROM user), quorum FROM session s INNER JOIN vote v ON v.idSession=s.id WHERE s.id="+session.getId()+";";
			else
				query = 	"SELECT SUM(v.val) AS Win, c.id, c.name AS Nome, c.surname AS Cognome, f.id, f.name AS Partito, quorum, (SELECT COUNT(id) FROM vervote WHERE idSession="+session.getId()+"), (SELECT COUNT(id) FROM user) "
						+ 	"FROM vote v INNER JOIN candidate c ON v.candidateId=c.id INNER JOIN faction f ON f.id=c.factionId INNER JOIN session s ON s.id=v.idSession "
						+ 	"WHERE idSession="+session.getId()+" GROUP BY v.candidateId ORDER BY Win DESC LIMIT 1;";
			
			ResultSet rs = db.selectQuery(query);

			if(isReferendum) {
				if(rs.next()) {
					boolean quorumNonRaggiunto = false;
					
					if(rs.getBoolean(4) && rs.getInt(1)<((rs.getInt(3)/2)+1))
						quorumNonRaggiunto = true;
					
					if(rs.getInt(2) >= (rs.getInt(1)/2))
						return new SessionWinner(true, true, null, quorumNonRaggiunto, rs.getInt(2) );
					return new SessionWinner(true, false, null, quorumNonRaggiunto, rs.getInt(2) );
				}
			}
			else {
				if(rs.next()) {
					boolean quorumNonRaggiunto = false;
					
					if(rs.getBoolean(7) && rs.getInt(8)<(rs.getInt(9)/2+1))
						quorumNonRaggiunto = true;
					
					return new SessionWinner(false, false, new Candidato(rs.getInt(2), rs.getString(3), rs.getString(4), new Partito(rs.getInt(5), rs.getString(6))) , quorumNonRaggiunto, rs.getInt(8) );
				}
			}
			
		} catch (Exception e) {
			System.out.println("Errore get win "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore get win "+e.getMessage());
			//e.printStackTrace();
		}
		return null;
	}
	
	
	

	public void registerUser(String name, String surname, String cf, boolean isAdmin, String email, String password) {
		try {
			String query = "INSERT INTO user " + "VALUES (null, \"" + name + "\" , \"" + surname + "\" , \"" + cf + "\" ,  " + (isAdmin?1:0) + ");";
			db.insertQuery(query);
			
			query = "SELECT MAX(id) FROM user;";
			ResultSet rs = db.selectQuery(query);
			
			rs.next();
			int userId = rs.getInt(1);
			
			Encryption enc = new Encryption();
			password = enc.getHash(password);
			
			query = "INSERT INTO credential " + "VALUES ("+userId+", \"" + email + "\" , \"" + password + "\");";
			db.insertQuery(query);
			
			(new Log()).newLog("DB Operations", "Inserito nuovo utente");
		} catch (Exception e) {
			System.out.println("Errore add user "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore add user "+e.getMessage());
			//e.printStackTrace();
		}
	}





	public void terminateSession(Session s) {
		try {
			String query = "UPDATE session SET isActive = 0 WHERE id="+s.getId()+";";
			db.insertQuery(query);
			(new Log()).newLog("DB Operations", "Sessione terminata");
		} catch (Exception e) {
			System.out.println("Errore terminate session "+e.getMessage());
			(new Log()).newLog("DB Operations ERROR", "Errore terminate session "+e.getMessage());
			//e.printStackTrace();
		}
	}
}
