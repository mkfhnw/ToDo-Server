package client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView extends BorderPane {
	
	// Elements for Login Scene
	private Label userLabel;
	private Label passwordLabel;
	private TextField userField;
	private TextField passwordField;
	private Button signInButton;
	private Button registerButton;
	private Label changePassword;
	
	private ImageView image;
	
	private VBox userVBox;
	private VBox passwordVBox;
	private VBox buttonVBox;
	private VBox loginVBox;
	private VBox spaceVBox;
	private VBox imageVBox;
	
	// Spacings
    private final int SPACING_BUTTON_VBOX = 20;
    private final int SPACING_LOGIN_VBOX = 15;
    private final int SPACING_IMAGE_VBOX = 20;
    private final int SPACING = 20;
	
	
	// Constructor
	public LoginView() {
	
	// Content
		
	this.image = new ImageView("/client/resources/User.png");
	this.image.setFitHeight(140);
	this.image.setFitWidth(140);
		
	this.userLabel = new Label("Benutzername");
	this.userLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	this.userLabel.setTextFill(Color.web("#181C54"));
	
	this.passwordLabel = new Label("Passwort");
	this.passwordLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	
	this.userField = new TextField();
	this.userField.setPromptText("email@outlook.com");
	this.userField.setMaxWidth(250);
	
	this.passwordField = new TextField();
	this.passwordField.setPromptText("Passwort");
	this.passwordField.setMaxWidth(250);
	
	this.signInButton = new Button("Anmelden");
	this.signInButton.setPrefSize(200, 50);
	this.signInButton.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	
	this.registerButton = new Button("Registrieren");
	this.registerButton.setPrefSize(200, 50);
	this.registerButton.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	
	this.changePassword = new Label("Passwort vergessen?");
	this.changePassword.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
	
	// Layout

	VBox vBoxSpace = new VBox(SPACING);
	
	this.imageVBox = new VBox(SPACING_IMAGE_VBOX);
	this.imageVBox.getChildren().addAll(image, vBoxSpace);
	this.imageVBox.setAlignment(Pos.CENTER);
		
	this.userVBox= new VBox();
	this.userVBox.getChildren().addAll(userLabel, userField);
	this.userVBox.setAlignment(Pos.CENTER);
	
	this.passwordVBox = new VBox();
	this.passwordVBox.getChildren().addAll(passwordLabel, passwordField);
	this.passwordVBox.setAlignment(Pos.CENTER);
	
	this.spaceVBox = new VBox();
	
	this.loginVBox = new VBox(SPACING_LOGIN_VBOX);
	this.loginVBox.getChildren().addAll(userVBox, passwordVBox, spaceVBox);
	
	VBox changePasswordVBox = new VBox();
	changePasswordVBox.getChildren().add(changePassword);
	changePasswordVBox.setAlignment(Pos.CENTER);
	
	HBox signInRegister = new HBox();
	signInRegister.getChildren().addAll(registerButton, signInButton);
	signInRegister.setAlignment(Pos.CENTER);	
	
	this.buttonVBox = new VBox(SPACING_BUTTON_VBOX);
	this.buttonVBox.getChildren().addAll(changePasswordVBox, signInRegister);
	this.buttonVBox.setAlignment(Pos.CENTER);
	
	this.setTop(imageVBox);	
	this.setCenter(loginVBox);
	this.setBottom(buttonVBox);
	
	}
	

}
