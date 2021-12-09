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

public class ChangePasswordDialogPane extends DialogPane {
	
	// Elements
	
	private Label title;
	
	private Label oldPasswordLabel;
	private Label newPasswordLabel;
	
	private TextField oldPasswordField;
	private TextField newPasswordField;
	
	// Layout
	
	private BorderPane borderPane;
	private VBox header;
	private HBox oldPassword;
	private HBox newPassword;
	private VBox general;
	
	// Buttontypes for DialogPane
	private ButtonType okButtonType;
	private ButtonType cancelButtonType;
	
	// Spacing for Layout
	private final int SPACING_OLDPASSWORD = 50;
	private final int SPACING_NEWPASSWORD = 42;
	
	// Size for TextFields
	private final int SIZE_TEXTFIELDS = 250;
	
	public ChangePasswordDialogPane() {
		
	// Fields for the registration formula and design
		
	this.title = new Label("Account erstellen");
	this.title.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	this.title.setTextFill(Color.web("#181C54"));
	
	this.oldPasswordLabel = new Label("Altes Passwort");
	this.oldPasswordLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	this.oldPasswordLabel.setTextFill(Color.web("#181C54"));
	this.oldPasswordField = new TextField();
	this.oldPasswordField.setPrefWidth(SIZE_TEXTFIELDS);
	
	this.newPasswordLabel = new Label("Neues Passwort");
	this.newPasswordLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	this.newPasswordLabel.setTextFill(Color.web("#181C54"));
	this.newPasswordField = new TextField();
	this.newPasswordField.setPrefWidth(SIZE_TEXTFIELDS);
	
	// Layout
	this.borderPane = new BorderPane();
	this.header = new VBox();
	this.oldPassword = new HBox(SPACING_OLDPASSWORD);
	this.newPassword = new HBox(SPACING_NEWPASSWORD);
	this.general = new VBox();
	
	// Add Fields to Layout
	this.header.getChildren().add(title);
	this.oldPassword.getChildren().addAll(oldPasswordLabel, oldPasswordField);
	this.newPassword.getChildren().addAll(newPasswordLabel, newPasswordField);
	this.general.getChildren().addAll(oldPassword, newPassword);
	
	this.borderPane.setTop(header);
	this.borderPane.setCenter(general);
	
	
	 // Add buttonTypes
    this.okButtonType = new ButtonType("Speichern", ButtonBar.ButtonData.OK_DONE);
    this.getButtonTypes().add(okButtonType);
    
    this.cancelButtonType = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
    this.getButtonTypes().add(cancelButtonType);
	
    // set content and text for content
	this.setContentText("Passwort ändern");
	
	this.setContent(borderPane);
	
	
		
		
	}

	// Getter
	public Label getTitle() {
		return title;
	}

	public Label getOldPasswordLabel() {
		return oldPasswordLabel;
	}

	public Label getNewPasswordLabel() {
		return newPasswordLabel;
	}

	public TextField getOldPasswordField() {
		return oldPasswordField;
	}

	public TextField getNewPasswordField() {
		return newPasswordField;
	}

	public BorderPane getBorderPane() {
		return borderPane;
	}

	public HBox getOldPassword() {
		return oldPassword;
	}

	public HBox getNewPassword() {
		return newPassword;
	}

	public VBox getGeneral() {
		return general;
	}

	public ButtonType getOkButtonType() {
		return okButtonType;
	}

	public ButtonType getCancelButtonType() {
		return cancelButtonType;
	}

	public int getSPACING() {
		return SPACING_NEWPASSWORD;
	}
	
	public int getSPACING2() {
		return SPACING_OLDPASSWORD;
	}

	public int getSIZE_TEXTFIELDS() {
		return SIZE_TEXTFIELDS;
	}
	
	
	//Setter

	public void setTitle(Label title) {
		this.title = title;
	}

	public void setOldPasswordLabel(Label oldPasswordLabel) {
		this.oldPasswordLabel = oldPasswordLabel;
	}

	public void setNewPasswordLabel(Label newPasswordLabel) {
		this.newPasswordLabel = newPasswordLabel;
	}

	public void setOldPasswordField(TextField oldPasswordField) {
		this.oldPasswordField = oldPasswordField;
	}

	public void setNewPasswordField(TextField newPasswordField) {
		this.newPasswordField = newPasswordField;
	}

	public void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}

	public void setHeader(VBox header) {
		this.header = header;
	}

	public void setOldPassword(HBox oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(HBox newPassword) {
		this.newPassword = newPassword;
	}

	public void setGeneral(VBox general) {
		this.general = general;
	}

	public void setOkButtonType(ButtonType okButtonType) {
		this.okButtonType = okButtonType;
	}

	public void setCancelButtonType(ButtonType cancelButtonType) {
		this.cancelButtonType = cancelButtonType;
	}
	
	
	
	
	
}
