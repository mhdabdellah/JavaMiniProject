module myProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.swt;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.swing;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.chat to javafx.graphics, javafx.fxml;
	opens application.src.Client to javafx.graphics, javafx.fxml;
	opens application.src.Server to javafx.graphics, javafx.fxml;
}
