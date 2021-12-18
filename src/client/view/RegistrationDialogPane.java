package client.view;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RegistrationDialogPane extends DialogPane {
	
	// Elements for registration
	private Label title;
	private Label emailLabel;
	private TextField emailField;
	private Label passwordLabel;
	private TextField passwordField;
	private Label repeatPasswordLabel;
	private TextField repeatPasswordField;
	
	// Layout
	private BorderPane borderPane;
	private HBox emailPane;
	private HBox passwordPane;
	private HBox repeatPasswordPane;
	private VBox header;
	private VBox vBox;

	
	// Buttontypes for DialogPane
	private ButtonType okButtonType;
	private ButtonType cancelButtonType;
	
	// Spacing for Layout
	private final int SPACING_EMAIL = 60;
	private final int SPACING_PASSWORD = 100;
	private final int SPACING_REPEAT_PASSWORD = 12;
	private final int SPACING_HEADER = 150;
	private final int SPACING = 15;

	// Size for TextFields
	private final int SIZE_TEXTFIELDS = 250;
	
	// Constructor
	public RegistrationDialogPane() {
	
		
	// Fields for the registration formula and design
		
	this.title = new Label("Account erstellen");
	this.title.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	this.title.setTextFill(Color.web("#181C54"));
	
	this.emailLabel = new Label("E-Mail-Adresse");
	this.emailLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	this.emailLabel.setTextFill(Color.web("#181C54"));
	this.emailField = new TextField();
	this.emailField = new TextField();
	this.emailField.setPrefWidth(SIZE_TEXTFIELDS);
	
	this.passwordLabel = new Label("Passwort");
	this.passwordLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	this.passwordLabel.setTextFill(Color.web("#181C54"));
	this.passwordField = new TextField();
	this.passwordField = new TextField();
	this.passwordField.setPrefWidth(SIZE_TEXTFIELDS);
	
	this.repeatPasswordLabel = new Label("Passwort wiederholen");
	this.repeatPasswordLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	this.repeatPasswordLabel.setTextFill(Color.web("#181C54"));
	this.repeatPasswordField = new TextField();
	this.repeatPasswordField = new TextField();
	this.repeatPasswordField.setPrefWidth(SIZE_TEXTFIELDS);
	
	// Layout
	this.borderPane = new BorderPane();
	this.emailPane = new HBox(SPACING_EMAIL);
	this.passwordPane = new HBox(SPACING_PASSWORD);
	this.repeatPasswordPane = new HBox(SPACING_REPEAT_PASSWORD);
	this.header = new VBox(SPACING_HEADER);
	this.vBox = new VBox(SPACING);
	
	// Add Fields to Layout
	this.header.getChildren().add(title);
	this.emailPane.getChildren().addAll(emailLabel, emailField);
	this.passwordPane.getChildren().addAll(passwordLabel, passwordField);
	this.repeatPasswordPane.getChildren().addAll(repeatPasswordLabel, repeatPasswordField);
	this.vBox.getChildren().addAll(
			header,
			emailPane, 
			passwordPane,
			repeatPasswordPane);
	
	this.borderPane.setTop(header);
	this.borderPane.setCenter(vBox);
	
	
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
