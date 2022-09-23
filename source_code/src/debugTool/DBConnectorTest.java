package debugTool;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dbManagement.DBConnector;

class DBConnectorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testInsertQuery() {
		DBConnector d = new DBConnector();
		assertThrows(NullPointerException.class, () -> d.insertQuery(null));
	}

	@Test
	void testSelectQuery() {
		DBConnector d = new DBConnector();
		assertThrows(NullPointerException.class, () -> d.insertQuery(null));
	}

	

}
