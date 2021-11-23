package client.view;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.awt.*;

public class RegistrationView extends DialogPane {
	
	private Label title;
	
	private BorderPane borderPane;
	
	private Label firstnameLabel;
	private TextField firstnameField;
	private Label surnameLabel;
	private TextField surnameField;
	private Label emailLabel;
	private TextField emailField;
	private Label passwordLabel;
	private TextField passwordField;
	private Label repeatPasswordLabel;
	private TextField repeatPasswordField;
	
	private ButtonType okButtonType;
	private ButtonType cancelButtonType;
	


	public RegistrationView() {
	
	this.title = new Label("Account erstellen");
	
	this.firstnameLabel = new Label("Vorname");
	this.firstnameField = new TextField();
	
	this.surnameLabel = new Label("Nachname");
	this.surnameField = new TextField();
	
	this.emailLabel = new Label("E-Mail-Adresse");
	this.emailField = new TextField();
	
	this.passwordLabel = new Label("Passwort");
	this.passwordField = new TextField();
	
	this.repeatPasswordLabel = new Label("Passwort wiederholen");
	this.repeatPasswordField = new TextField();
	
	 // Add buttonTypes
    // this.okButtonType = new ButtonType("Erstellen", ButtonBar.ButtonData.OK_DONE);
    // this.getButtonTypes().add(new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE));
    // this.getButtonTypes().add(okButtonType);
	
	this.setContentText("Account erstellen");
		
	
		
	}

}
