package client.view;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegistrationDialogPane extends DialogPane {
	
	// Elements for registration
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
	
	// Layout
	private BorderPane borderPane;
	private VBox firstnameVBox;
	private VBox surnameVBox;
	private VBox emailVBox;
	private VBox passwordVBox;
	private VBox repeatPasswordVBox;
	private HBox hbox;
	
	// Buttontypes for DialogPane
	private ButtonType okButtonType;
	private ButtonType cancelButtonType;
	
	// Spacings for Layout
	private final int SPACING_FIRSTNAME = 15;
    private final int SPACING_SURNAME = 43;
    private final int SPACING_EMAIL = 28;
    private final int SPACING_PASSWORD = 40;
    private final int SPACING_REPEAT_PASSWORD = -10;
	

	// Constructor
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
	
	this.firstnameVBox = new VBox(SPACING_FIRSTNAME);
	this.surnameVBox = new VBox(SPACING_SURNAME);
	this.emailVBox = new VBox(SPACING_EMAIL);
	this.passwordVBox = new VBox(SPACING_PASSWORD);
	this.repeatPasswordVBox = new VBox(SPACING_REPEAT_PASSWORD);
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
    this.getButtonTypes().add(okButtonType);
    
    this.cancelButtonType = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
    this.getButtonTypes().add(cancelButtonType);
	
    // set content and text for content
	this.setContentText("Account erstellen");
	
	this.setContent(borderPane);
		
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



	public VBox getFirstnameVBox() {
		return firstnameVBox;
	}



	public VBox getSurnameVBox() {
		return surnameVBox;
	}



	public VBox getEmailVBox() {
		return emailVBox;
	}



	public VBox getPasswordVBox() {
		return passwordVBox;
	}



	public VBox getRepeatPasswordVBox() {
		return repeatPasswordVBox;
	}



	public HBox getHbox() {
		return hbox;
	}



	public int getSPACING_FIRSTNAME() {
		return SPACING_FIRSTNAME;
	}



	public int getSPACING_SURNAME() {
		return SPACING_SURNAME;
	}



	public int getSPACING_EMAIL() {
		return SPACING_EMAIL;
	}



	public int getSPACING_PASSWORD() {
		return SPACING_PASSWORD;
	}



	public int getSPACING_REPEAT_PASSWORD() {
		return SPACING_REPEAT_PASSWORD;
	}



	public void setFirstnameVBox(VBox firstnameVBox) {
		this.firstnameVBox = firstnameVBox;
	}



	public void setSurnameVBox(VBox surnameVBox) {
		this.surnameVBox = surnameVBox;
	}



	public void setEmailVBox(VBox emailVBox) {
		this.emailVBox = emailVBox;
	}



	public void setPasswordVBox(VBox passwordVBox) {
		this.passwordVBox = passwordVBox;
	}



	public void setRepeatPasswordVBox(VBox repeatPasswordVBox) {
		this.repeatPasswordVBox = repeatPasswordVBox;
	}



	public void setHbox(HBox hbox) {
		this.hbox = hbox;
	}

}
