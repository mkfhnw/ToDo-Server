package client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	
    // Dialog for CreateAccount
    private Dialog<ButtonType> registrationDialog;
	private RegistrationDialogPane registrationDialogPane;
	
	// Constructor
	public LoginView() {
	
	// Content
		
	this.image = new ImageView("/common/resources/User.png");
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
	
	// Create and costumize Registration Dialog
	this.registrationDialog = new Dialog<ButtonType>();
	this.registrationDialog.setTitle("Account erstellen");
	Stage stage3 = (Stage) registrationDialog.getDialogPane().getScene().getWindow();
	stage3.getIcons().add(new Image(this.getClass().getResource("/common/resources/howTo.png").toString()));
	
	this.registrationDialogPane = new RegistrationDialogPane();
	this.registrationDialog.setDialogPane(registrationDialogPane);
	
	this.registrationDialog.initModality(Modality.NONE);
	
	}


	public Label getUserLabel() {
		return userLabel;
	}


	public Label getPasswordLabel() {
		return passwordLabel;
	}


	public TextField getUserField() {
		return userField;
	}


	public TextField getPasswordField() {
		return passwordField;
	}


	public Button getSignInButton() {
		return signInButton;
	}


	public Button getRegisterButton() {
		return registerButton;
	}


	public Label getChangePassword() {
		return changePassword;
	}


	public ImageView getImage() {
		return image;
	}


	public VBox getUserVBox() {
		return userVBox;
	}


	public VBox getPasswordVBox() {
		return passwordVBox;
	}


	public VBox getButtonVBox() {
		return buttonVBox;
	}


	public VBox getLoginVBox() {
		return loginVBox;
	}


	public VBox getSpaceVBox() {
		return spaceVBox;
	}


	public VBox getImageVBox() {
		return imageVBox;
	}


	public int getSPACING_BUTTON_VBOX() {
		return SPACING_BUTTON_VBOX;
	}


	public int getSPACING_LOGIN_VBOX() {
		return SPACING_LOGIN_VBOX;
	}


	public int getSPACING_IMAGE_VBOX() {
		return SPACING_IMAGE_VBOX;
	}


	public int getSPACING() {
		return SPACING;
	}


	public void setUserLabel(Label userLabel) {
		this.userLabel = userLabel;
	}


	public void setPasswordLabel(Label passwordLabel) {
		this.passwordLabel = passwordLabel;
	}


	public void setUserField(TextField userField) {
		this.userField = userField;
	}


	public void setPasswordField(TextField passwordField) {
		this.passwordField = passwordField;
	}


	public void setSignInButton(Button signInButton) {
		this.signInButton = signInButton;
	}


	public void setRegisterButton(Button registerButton) {
		this.registerButton = registerButton;
	}


	public void setChangePassword(Label changePassword) {
		this.changePassword = changePassword;
	}


	public void setImage(ImageView image) {
		this.image = image;
	}


	public void setUserVBox(VBox userVBox) {
		this.userVBox = userVBox;
	}


	public void setPasswordVBox(VBox passwordVBox) {
		this.passwordVBox = passwordVBox;
	}


	public void setButtonVBox(VBox buttonVBox) {
		this.buttonVBox = buttonVBox;
	}


	public void setLoginVBox(VBox loginVBox) {
		this.loginVBox = loginVBox;
	}


	public void setSpaceVBox(VBox spaceVBox) {
		this.spaceVBox = spaceVBox;
	}


	public void setImageVBox(VBox imageVBox) {
		this.imageVBox = imageVBox;
	}


	public Dialog<ButtonType> getRegistrationDialog() {
		return registrationDialog;
	}


	public RegistrationDialogPane getRegistrationDialogPane() {
		return registrationDialogPane;
	}


	public void setRegistrationDialog(Dialog<ButtonType> registrationDialog) {
		this.registrationDialog = registrationDialog;
	}


	public void setRegistrationDialogPane(RegistrationDialogPane registrationDialogPane) {
		this.registrationDialogPane = registrationDialogPane;
	}
	

}
