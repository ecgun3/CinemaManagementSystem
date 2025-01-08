module CinemaManagementSystem {
	exports application;
	exports database;
	
    requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
    requires java.sql;
    
    opens application to javafx.graphics, javafx.fxml;
    opens ui to javafx.graphics, javafx.fxml;
}