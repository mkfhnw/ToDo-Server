package client.view;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class RegistrationDialogPane extends DialogPane {
	
	private Label title;
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
	
	private BorderPane borderPane;
	private VBox firstnameVBox;
	private VBox surnameVBox;
	private VBox emailVBox;
	private VBox passwordVBox;
	private VBox repeatPasswordVBox;
	private HBox hbox;
	
	private ButtonType okButtonType;
	private ButtonType cancelButtonType;
	


	public RegistrationDialogPane() {
	
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
	
	this.firstnameVBox = new VBox();
	this.surnameVBox = new VBox();
	this.emailVBox = new VBox();
	this.passwordVBox = new VBox();
	this.repeatPasswordVBox = new VBox();
	this.hbox = new HBox();
	this.borderPane = new BorderPane();
	
	this.firstnameVBox.getChildren().addAll(firstnameLabel, firstnameField);
	this.surnameVBox.getChildren().addAll(surnameLabel, surnameField);
	this.emailVBox.getChildren().addAll(emailLabel, emailField);
	this.passwordVBox.getChildren().addAll(passwordLabel, passwordField);
	this.repeatPasswordVBox.getChildren().addAll(repeatPasswordLabel, repeatPasswordField);
	this.hbox.getChildren().addAll(
			firstnameVBox, 
			surnameVBox,
			emailVBox,
			passwordVBox, 
			repeatPasswordVBox);
	
	this.borderPane.setTop(title);
	this.borderPane.setCenter(hbox);
	
	
	
	 // Add buttonTypes
    this.okButtonType = new ButtonType("Erstellen", ButtonBar.ButtonData.OK_DONE);
    this.getButtonTypes().add(new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE));
    this.getButtonTypes().add(okButtonType);
	
	this.setContentText("Account erstellen");
		
	
		
	}



	public Label getTitle() {
		return title;
	}



	public BorderPane getBorderPane() {
		return borderPane;
	}



	public Label getFirstnameLabel() {
		return firstnameLabel;
	}



	public TextField getFirstnameField() {
		return firstnameField;
	}



	public Label getSurnameLabel() {
		return surnameLabel;
	}



	public TextField getSurnameField() {
		return surnameField;
	}



	public Label getEmailLabel() {
		return emailLabel;
	}



	public TextField getEmailField() {
		return emailField;
	}



	public Label getPasswordLabel() {
		return passwordLabel;
	}



	public TextField getPasswordField() {
		return passwordField;
	}



	public Label getRepeatPasswordLabel() {
		return repeatPasswordLabel;
	}



	public TextField getRepeatPasswordField() {
		return repeatPasswordField;
	}



	public ButtonType getOkButtonType() {
		return okButtonType;
	}



	public ButtonType getCancelButtonType() {
		return cancelButtonType;
	}



	public void setTitle(Label title) {
		this.title = title;
	}



	public void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}



	public void setFirstnameLabel(Label firstnameLabel) {
		this.firstnameLabel = firstnameLabel;
	}



	public void setFirstnameField(TextField firstnameField) {
		this.firstnameField = firstnameField;
	}



	public void setSurnameLabel(Label surnameLabel) {
		this.surnameLabel = surnameLabel;
	}



	public void setSurnameField(TextField surnameField) {
		this.surnameField = surnameField;
	}



	public void setEmailLabel(Label emailLabel) {
		this.emailLabel = emailLabel;
	}



	public void setEmailField(TextField emailField) {
		this.emailField = emailField;
	}



	public void setPasswordLabel(Label passwordLabel) {
		this.passwordLabel = passwordLabel;
	}



	public void setPasswordField(TextField passwordField) {
		this.passwordField = passwordField;
	}



	public void setRepeatPasswordLabel(Label repeatPasswordLabel) {
		this.repeatPasswordLabel = repeatPasswordLabel;
	}



	public void setRepeatPasswordField(TextField repeatPasswordField) {
		this.repeatPasswordField = repeatPasswordField;
	}



	public void setOkButtonType(ButtonType okButtonType) {
		this.okButtonType = okButtonType;
	}



	public void setCancelButtonType(ButtonType cancelButtonType) {
		this.cancelButtonType = cancelButtonType;
	}

}
