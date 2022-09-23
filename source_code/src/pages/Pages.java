package pages;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Pages {
	private static Stage stage;
	protected static Scene scene;
	
	public Pages() {}
	
	public Pages(Stage stage) {
		this.stage = stage;
	}
	
	protected Stage getStage() {
		return stage;
	}
	
	public abstract void show();
}
