package dbManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Log;
import auditing.LogElement;
import auditing.LogHistory;

/**
 * Questa classe mette a disposizione i metodi per gestire la connessione con il DB
 * e richiamare la stessa per l'utilizzo.
 * <p>
 * Questa classe è stata creata per semplificare e mantenere raggruppati gli elementi
 * di connessione al DB.
 * 
 * @author Fede
 *
 */
public class DBConnector {
	
	private final static String url = "jdbc:mysql://localhost/sistemaelettoraleingsw";
	private final static String username = "root";
	private final static String password = "";
	
	private static Connection dbConnection;
	
	
	
	public void insertQuery(String query){
		try {
			if(dbConnection.isClosed()){
				throw new Exception("DB connection is closed");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// create the java statement
		Statement st = null;
		try {
			st = dbConnection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ResultSet executeQuery(String query) throws Exception{
		if(dbConnection.isClosed()){
			throw new Exception("DB connection is closed");
		}
		// create the java statement
		Statement st = null;
		try {
			st = dbConnection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
		// execute the query, and get a java resultset
		ResultSet rs = null;
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	/**
	 * Permette di ottenere la connessione aperta col Database.
	 * 
	 * @throws IllegalStateException nel caso la connessione non sia stata aperta. 
	 * Per aprirla vedi {@link #openConnection()}.
	 * 
	 * @return Connessione al DB
	 */
	public Connection getDbConnection() {
		try {
			if(dbConnection == null || dbConnection.isClosed())
				throw new SQLException();
		} catch (SQLException e) {
			Log.getInstance().addLog(new LogElement(DBConnector.class, "gettingDbConnection", "Provando ad ottenere la connessione con il DB",true));
			throw new IllegalStateException("Server seems not to be started");
		}
		return dbConnection;
	}
	
	
	/**
	 * Permette di aprire la connessione con il Database locale Mysql
	 * @throws SQLException Nel caso ci siano errori di comunicazione con il DB
	 */
	public void openConnection() throws SQLException {
		LogHistory.getInstance().addLog(new LogElement(DBConnector.class, "openConnection", "Provando ad aprire la connessione con il DB"));
		dbConnection = DriverManager.getConnection(url, username, password);
		LogHistory.getInstance().addLog(new LogElement(DBConnector.class, "openConnection", "Connessione con il DB aperta con successo"));
	}
	

	/**
	 * Permette di chiudere la connessione al DB se � aperta
	 */
	public void closeConnection() {
		try {
			if(dbConnection!=null && !dbConnection.isClosed())
				dbConnection.close();
			LogHistory.getInstance().addLog(new LogElement(DBConnector.class, "closeConnection", "Chiusa la connessione con il DB"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
