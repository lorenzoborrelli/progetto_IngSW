package debugTool;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import application.CheckLogin;

class ChLoginTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testcheckCredentials() {
		assertThrows(NullPointerException.class, () -> CheckLogin.checkCredentials(null, "abc"));
		assertThrows(NullPointerException.class, () -> CheckLogin.checkCredentials("abc", null));
		assertThrows(NullPointerException.class, () -> CheckLogin.checkCredentials(null, null));
		
		assertFalse(CheckLogin.checkCredentials("", ""));
		
		assertFalse(CheckLogin.checkCredentials("x", "x"));
	}

}
