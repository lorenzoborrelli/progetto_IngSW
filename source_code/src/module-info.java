module provaJfx {
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires javafx.fxml;
	requires org.junit.jupiter.api;
	
	opens application to javafx.graphics, javafx.fxml;
	opens pages to javafx.graphics, javafx.fxml;
}
