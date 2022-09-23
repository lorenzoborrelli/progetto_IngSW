package dbManagement;
/*Federico Millefanti - Lorenzo Borrelli*/
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import debugTool.Log;

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
	
	
	
	public boolean isConnected() {
		try {
			if(dbConnection == null) return false;
			return !dbConnection.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void insertQuery(String query){
		if(query == null) {
			throw new NullPointerException();
		}
		
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
	
	
	public ResultSet selectQuery(String query) throws Exception{
		if(query == null) {
			throw new NullPointerException();
		}
		
		if(dbConnection.isClosed()){
			throw new Exception("DB connection is closed");
		}
		// create the java statement
		Statement st = null;
		try {
			st = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
			//Log.getInstance().addLog(new LogElement(DBConnector.class, "gettingDbConnection", "Provando ad ottenere la connessione con il DB",true));
			throw new IllegalStateException("Server seems not to be started");
		}
		return dbConnection;
	}
	
	
	/**
	 * Permette di aprire la connessione con il Database locale Mysql
	 * @throws SQLException Nel caso ci siano errori di comunicazione con il DB
	 * @throws ClassNotFoundException 
	 */
	public void openConnection() {
		//LogHistory.getInstance().addLog(new LogElement(DBConnector.class, "openConnection", "Provando ad aprire la connessione con il DB"));
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			dbConnection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//LogHistory.getInstance().addLog(new LogElement(DBConnector.class, "openConnection", "Connessione con il DB aperta con successo"));
	}
	

	/**
	 * Permette di chiudere la connessione al DB se � aperta
	 */
	public void closeConnection() {
		try {
			if(dbConnection!=null && !dbConnection.isClosed())
				dbConnection.close();
			//LogHistory.getInstance().addLog(new LogElement(DBConnector.class, "closeConnection", "Chiusa la connessione con il DB"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
