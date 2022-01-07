package client.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import client.ClientNetworkPlugin;
import client.model.Priority;
import client.model.ToDo;


public class AddToDoDialogPane extends DialogPane {

    // Components
    private BorderPane root;
    private VBox leftPane;
    private VBox rightPane;
    private HBox titleBar;
    private HBox categoryBar;
    private HBox dueDateBar;
    private HBox priorityBar;
    private HBox tagsBar;
    private VBox headerBar;
    private HBox notice;
    private VBox topBar;

    private Label newTaskLabel;
    private Label tippLabel;
    private Label titleLabel;
    private Label categoryLabel;
    private Label dueDateLabel;
    private Label priorityLabel;
    private Label messageLabel;
    private Label tagsLabel;
    private Label noticeLabel;
    
    private ImageView attention;

    private TextField titleTextfield;
    private TextField tagsTextfield;

    private Tooltip titleToolTip;
    private Tooltip messageToolTip;
    private Tooltip categoryToolTip;
    private Tooltip dateToolTip;
    private Tooltip priorityTip;
    private Tooltip tagsToolTip;

    private ComboBox<String> categoryComboBox;
    private DatePicker datePicker;
    private TextArea messageTextArea;
    private ComboBox<Priority> priorityComboBox;
    
    private ClientNetworkPlugin clientNetworkPlugin;

    // Custom button type for eventhandling
    ButtonType okButtonType;

    // Fields
    private final int SPACING_CATEGORYBAR = 15;
    private final int SPACING_TITLEBAR = 43;
    private final int SPACING_DUEDATEBAR = 28;
    private final int SPACING_PRIORITYBAR = 22;
    private final int SPACING_TAGSBAR = 40;
    private final int SPACING_HEADERBAR = -10;
    private final Duration DURATION_UNTIL_SHOW = Duration.seconds(0.2);


    // Constructor
    public AddToDoDialogPane(ObservableList<String> listViewItems, ClientNetworkPlugin clientNetworkPlugin) {
    	
    	this.clientNetworkPlugin = clientNetworkPlugin;

        // Instantiate components
        root = new BorderPane();
        leftPane = new VBox();
        rightPane = new VBox();
        titleBar = new HBox(SPACING_TITLEBAR);
        categoryBar = new HBox(SPACING_CATEGORYBAR);
        dueDateBar = new HBox(SPACING_DUEDATEBAR);
        priorityBar = new HBox(SPACING_PRIORITYBAR);
        tagsBar = new HBox(SPACING_TAGSBAR);
        headerBar = new VBox(SPACING_HEADERBAR);
        notice = new HBox();
        topBar = new VBox();
        
        newTaskLabel = new Label("Neue Aufgabe");
        titleLabel = new Label("Titel");
        categoryLabel = new Label("Kategorie");
        dueDateLabel = new Label("Termin");
        priorityLabel = new Label("Priorität");
        messageLabel = new Label("Beschreibung");
        tagsLabel = new Label("Tags");
        tippLabel = new Label("Bewegen Sie Ihren Mauszeiger über einen Schriftzug!");
        noticeLabel = new Label("​Kategorien für ein ToDo können nur auf dem zur Applikation dazugehörigen Server gespeichert werden.");
        
        attention = new ImageView("/common/resources/attention.png");
		attention.setFitHeight(15);
		attention.setFitWidth(15);

        titleTextfield = new TextField();
        tagsTextfield = new TextField();

        // Instantiate tooltips
        titleToolTip = new Tooltip("Ihr Titel muss zwischen 3 - 20 Zeichen lang sein.");
        messageToolTip = new Tooltip("Ihre Beschreibung muss < 255 Zeichen lang sein.");
        categoryToolTip = new Tooltip("Die Kategorie ist optional.");
        dateToolTip = new Tooltip("Ihr Datum muss im Format YYYY.MM.DD sein und in der Zukunft liegen.");
        priorityTip = new Tooltip("Die Priorität muss einen Wert enthalten.");
        tagsToolTip = new Tooltip("Ihre Tags müssen einzelne Wörter sein, separiert mit einem Semikolon (;).");

        // Change tooltip timers
        titleToolTip.setShowDelay(DURATION_UNTIL_SHOW);
        messageToolTip.setShowDelay(DURATION_UNTIL_SHOW);
        categoryToolTip.setShowDelay(DURATION_UNTIL_SHOW);
        dateToolTip.setShowDelay(DURATION_UNTIL_SHOW);
        priorityTip.setShowDelay(DURATION_UNTIL_SHOW);
        tagsToolTip.setShowDelay(DURATION_UNTIL_SHOW);

        // Instantiate the rest of the items, remove "Papierkorb" from selectable category
        categoryComboBox = new ComboBox<>();
        ObservableList<String> copy = FXCollections.observableArrayList();
        copy.addAll(listViewItems);
        copy.remove(3);
        categoryComboBox.setItems(copy);
        
        
        // If Server is "147.86.8.31", the category CheckBox, Label and the header note will be disabled or cleared.
        if (this.clientNetworkPlugin.getIP().equals("147.86.8.31")) {
        	this.categoryComboBox.setDisable(true);
        	this.categoryLabel.setDisable(true);
            this.notice.getChildren().addAll(attention, noticeLabel);
        	        
        }
                
        datePicker = new DatePicker();
        messageTextArea = new TextArea();

        // ComboBox for Priority
        priorityComboBox = new ComboBox<>();
        priorityComboBox.setItems(FXCollections.observableArrayList(Priority.values()));
        priorityComboBox.setValue(Priority.Low);
        
        // Fill controls into containers
        titleBar.getChildren().addAll(titleLabel, titleTextfield);
        categoryBar.getChildren().addAll(categoryLabel, categoryComboBox);
        dueDateBar.getChildren().addAll(dueDateLabel, datePicker);
        priorityBar.getChildren().addAll(priorityLabel, priorityComboBox);
        tagsBar.getChildren().addAll(tagsLabel, tagsTextfield);
        headerBar.getChildren().addAll(newTaskLabel, tippLabel);
        topBar.getChildren().addAll(notice, headerBar);
        
        leftPane.getChildren().addAll(titleBar, categoryBar, dueDateBar, priorityBar);
        rightPane.getChildren().addAll(messageLabel, messageTextArea);

        // Set containers
        root.setTop(topBar);
        root.setLeft(leftPane);
        root.setRight(rightPane);

        // Associate tooltips
        titleLabel.setTooltip(titleToolTip);
        messageLabel.setTooltip(messageToolTip);
        categoryLabel.setTooltip(categoryToolTip);
        dueDateLabel.setTooltip(dateToolTip);
        priorityLabel.setTooltip(priorityTip);
        // tagsLabel.setTooltip(tagsToolTip);
        

        // Add CSS styling
        this.getStylesheets().add(getClass().getResource("DialogPaneStyleSheet.css").toExternalForm());
        this.root.getStyleClass().add("root");
        this.leftPane.getStyleClass().add("leftPane");
        this.rightPane.getStyleClass().add("rightPane");
        this.newTaskLabel.getStyleClass().add("newTaskLabel");
        this.tippLabel.getStyleClass().add("tippLabel");
        this.titleLabel.getStyleClass().add("titleLabel");
        this.categoryLabel.getStyleClass().add("categoryLabel");
        this.dueDateLabel.getStyleClass().add("dueDateLabel");
        this.messageLabel.getStyleClass().add("messageLabel");
        this.tagsLabel.getStyleClass().add("tagsLabel");
        this.messageTextArea.getStyleClass().add("messageTextArea");
        this.priorityLabel.getStyleClass().add("priorityLabel");
        this.notice.getStyleClass().add("notice");
        this.categoryComboBox.getStyleClass().add("comboBox");
        this.categoryComboBox.getStyleClass().add("combo-box");
        this.priorityComboBox.getStyleClass().add("comboBox");
        this.priorityComboBox.getStyleClass().add("combo-box");

        // Word wrap
        this.messageTextArea.setWrapText(true);
        

        // Add buttonTypes
        okButtonType = new ButtonType("Erstellen", ButtonBar.ButtonData.OK_DONE);
        this.getButtonTypes().add(new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE));
        this.getButtonTypes().add(okButtonType);
        this.getStylesheets().add(getClass().getResource("FocusAndHowToDialogPaneStyleSheet.css").toExternalForm());

        // Set content
        this.setContent(root);
        this.setContentText("Neue Aufgabe");

    }

    // Constructor overload method used for updating an item
    public AddToDoDialogPane(ObservableList<String> listViewItems, ToDo todo) {

        // Instantiate components
        root = new BorderPane();
        leftPane = new VBox();
        rightPane = new VBox();
        titleBar = new HBox(SPACING_TITLEBAR);
        categoryBar = new HBox(SPACING_CATEGORYBAR);
        dueDateBar = new HBox(SPACING_DUEDATEBAR);
        priorityBar = new HBox(SPACING_PRIORITYBAR);
        headerBar = new VBox(SPACING_HEADERBAR);

        newTaskLabel = new Label(todo.getTitle());
        titleLabel = new Label("Titel");
        categoryLabel = new Label("Kategorie");
        dueDateLabel = new Label("Termin");
        messageLabel = new Label("Beschreibung");
        priorityLabel = new Label("Priorität");
        tippLabel = new Label("Bewegen Sie Ihren Mauszeiger über einen Schriftzug!");

        titleTextfield = new TextField();

        // Instantiate tooltips
        titleToolTip = new Tooltip("Ihr Titel muss zwischen 3 - 20 Zeichen lang sein.");
        messageToolTip = new Tooltip("Ihre Beschreibung muss < 255 Zeichen lang sein.");
        categoryToolTip = new Tooltip("Die Kategorie muss einen Wert enthalten.");
        dateToolTip = new Tooltip("Ihr Datum muss im Format DD.MM.YYYY sein und in der Zukunft liegen.");
        priorityTip = new Tooltip("Die Priorität muss einen Wert enthalten.");

        // Change tooltip timers
        titleToolTip.setShowDelay(DURATION_UNTIL_SHOW);
        messageToolTip.setShowDelay(DURATION_UNTIL_SHOW);
        categoryToolTip.setShowDelay(DURATION_UNTIL_SHOW);
        dateToolTip.setShowDelay(DURATION_UNTIL_SHOW);
        priorityTip.setShowDelay(DURATION_UNTIL_SHOW);

        // Remove Papierkorb
        ObservableList<String> copy = FXCollections.observableArrayList();
        copy.addAll(listViewItems);
        copy.remove(3);

        // Instantiate the rest of the items
        categoryComboBox = new ComboBox<>();
        categoryComboBox.setItems(copy);
        datePicker = new DatePicker();
        messageTextArea = new TextArea();

        // Instantiate combobox
        priorityComboBox = new ComboBox<>();
        Priority[] priorities = Priority.values();
        Priority[] validPriorities = new Priority[3];
        validPriorities[0] = priorities[0];
        validPriorities[1] = priorities[1];
        validPriorities[2] = priorities[2];
        priorityComboBox.setItems(FXCollections.observableArrayList(validPriorities));
        priorityComboBox.setValue(Priority.Low);

        // Fill controls into containers
        titleBar.getChildren().addAll(titleLabel, titleTextfield);
        categoryBar.getChildren().addAll(categoryLabel, categoryComboBox);
        dueDateBar.getChildren().addAll(dueDateLabel, datePicker);
        priorityBar.getChildren().addAll(priorityLabel, priorityComboBox);
        headerBar.getChildren().addAll(newTaskLabel, tippLabel);

        leftPane.getChildren().addAll(titleBar, categoryBar, dueDateBar, priorityBar);
        rightPane.getChildren().addAll(messageLabel, messageTextArea);

        // Set containers
        root.setTop(headerBar);
        root.setLeft(leftPane);
        root.setRight(rightPane);

        // Associate tooltips
        titleLabel.setTooltip(titleToolTip);
        messageLabel.setTooltip(messageToolTip);
        categoryLabel.setTooltip(categoryToolTip);
        dueDateLabel.setTooltip(dateToolTip);
        priorityLabel.setTooltip(priorityTip);

        // Fill fields
        titleTextfield.setText(todo.getTitle());
        datePicker.getEditor().setText(todo.getDueDateString());

        // Fill category combobox depending on what category the item has
        categoryComboBox.getEditor().setText(todo.getCategory());
        if(todo.getCategory() != null && todo.getCategory().equals("Wichtig")) { categoryComboBox.getSelectionModel().select(0); }
        if(todo.getCategory() != null && todo.getCategory().equals("Geplant")) { categoryComboBox.getSelectionModel().select(1); }
        if(todo.getCategory() != null && todo.getCategory().equals("Erledigt")) { categoryComboBox.getSelectionModel().select(2); }


        // Debugging tag string - if it's empty it will insert a semicolon
        messageTextArea.setText(todo.getMessage());

        // Add CSS styling
        this.getStylesheets().add(getClass().getResource("DialogPaneStyleSheet.css").toExternalForm());
        this.root.getStyleClass().add("root");
        this.leftPane.getStyleClass().add("leftPane");
        this.rightPane.getStyleClass().add("rightPane");
        this.newTaskLabel.getStyleClass().add("newTaskLabel");
        this.tippLabel.getStyleClass().add("tippLabel");
        this.titleLabel.getStyleClass().add("titleLabel");
        this.categoryLabel.getStyleClass().add("categoryLabel");
        this.dueDateLabel.getStyleClass().add("dueDateLabel");
        this.messageLabel.getStyleClass().add("messageLabel");
        this.priorityLabel.getStyleClass().add("priorityLabel");
        this.messageTextArea.getStyleClass().add("messageTextArea");

        // Word wrap
        this.messageTextArea.setWrapText(true);

        // Add buttonTypes
        okButtonType = new ButtonType("Erstellen", ButtonBar.ButtonData.OK_DONE);
        this.getButtonTypes().add(new ButtonType("Schliessen", ButtonBar.ButtonData.CANCEL_CLOSE));
        this.getButtonTypes().add(okButtonType);
        this.lookupButton(okButtonType).setDisable(true);

        // Set content
        this.setContent(root);

    }

    // Clearing method
    public void clearPane() {
        this.titleTextfield.clear();
        this.categoryComboBox.valueProperty().setValue(null);
        this.datePicker.getEditor().clear();
        this.messageTextArea.clear();
    }

    // Disabled all controls
    public void disableAllControls() {
        this.titleTextfield.setDisable(true);
        this.categoryComboBox.setDisable(true);
        this.datePicker.setDisable(true);
        this.messageTextArea.setDisable(true);
        this.priorityComboBox.setDisable(true);
    }

	public BorderPane getRoot() {
		return root;
	}

	public VBox getLeftPane() {
		return leftPane;
	}

	public VBox getRightPane() {
		return rightPane;
	}

	public HBox getTitleBar() {
		return titleBar;
	}

	public HBox getCategoryBar() {
		return categoryBar;
	}

	public HBox getDueDateBar() {
		return dueDateBar;
	}

	public HBox getTagsBar() {
		return tagsBar;
	}

	public VBox getHeaderBar() {
		return headerBar;
	}

	public Label getNewTaskLabel() {
		return newTaskLabel;
	}

	public Label getTippLabel() {
		return tippLabel;
	}

	public Label getTitleLabel() {
		return titleLabel;
	}

	public Label getCategoryLabel() {
		return categoryLabel;
	}

	public Label getDueDateLabel() {
		return dueDateLabel;
	}

	public Label getMessageLabel() {
		return messageLabel;
	}

	public Label getTagsLabel() {
		return tagsLabel;
	}

	public TextField getTitleTextfield() {
		return titleTextfield;
	}

	public TextField getTagsTextfield() {
		return tagsTextfield;
	}

	public Tooltip getTitleToolTip() {
		return titleToolTip;
	}

	public Tooltip getMessageToolTip() {
		return messageToolTip;
	}

	public Tooltip getCategoryToolTip() {
		return categoryToolTip;
	}

	public Tooltip getDateToolTip() {
		return dateToolTip;
	}

	public Tooltip getTagsToolTip() {
		return tagsToolTip;
	}

	public ComboBox<String> getCategoryComboBox() {
		return categoryComboBox;
	}

	public DatePicker getDatePicker() {
		return datePicker;
	}

	public TextArea getMessageTextArea() {
		return messageTextArea;
	}

	public ButtonType getOkButtonType() {
		return okButtonType;
	}

	public int getSPACING_CATEGORYBAR() {
		return SPACING_CATEGORYBAR;
	}

	public int getSPACING_TITLEBAR() {
		return SPACING_TITLEBAR;
	}

	public int getSPACING_DUEDATEBAR() {
		return SPACING_DUEDATEBAR;
	}

	public int getSPACING_TAGSBAR() {
		return SPACING_TAGSBAR;
	}

	public int getSPACING_HEADERBAR() {
		return SPACING_HEADERBAR;
	}

	public Duration getDURATION_UNTIL_SHOW() {
		return DURATION_UNTIL_SHOW;
	}

	public void setRoot(BorderPane root) {
		this.root = root;
	}

	public void setLeftPane(VBox leftPane) {
		this.leftPane = leftPane;
	}

	public void setRightPane(VBox rightPane) {
		this.rightPane = rightPane;
	}

	public void setTitleBar(HBox titleBar) {
		this.titleBar = titleBar;
	}

	public void setCategoryBar(HBox categoryBar) {
		this.categoryBar = categoryBar;
	}

	public void setDueDateBar(HBox dueDateBar) {
		this.dueDateBar = dueDateBar;
	}

	public void setTagsBar(HBox tagsBar) {
		this.tagsBar = tagsBar;
	}

	public void setHeaderBar(VBox headerBar) {
		this.headerBar = headerBar;
	}

	public void setNewTaskLabel(Label newTaskLabel) {
		this.newTaskLabel = newTaskLabel;
	}

	public void setTippLabel(Label tippLabel) {
		this.tippLabel = tippLabel;
	}

	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}

	public void setCategoryLabel(Label categoryLabel) {
		this.categoryLabel = categoryLabel;
	}

	public void setDueDateLabel(Label dueDateLabel) {
		this.dueDateLabel = dueDateLabel;
	}

	public void setMessageLabel(Label messageLabel) {
		this.messageLabel = messageLabel;
	}

	public void setTagsLabel(Label tagsLabel) {
		this.tagsLabel = tagsLabel;
	}

	public void setTitleTextfield(TextField titleTextfield) {
		this.titleTextfield = titleTextfield;
	}

	public void setTagsTextfield(TextField tagsTextfield) {
		this.tagsTextfield = tagsTextfield;
	}

	public void setTitleToolTip(Tooltip titleToolTip) {
		this.titleToolTip = titleToolTip;
	}

	public void setMessageToolTip(Tooltip messageToolTip) {
		this.messageToolTip = messageToolTip;
	}

	public void setCategoryToolTip(Tooltip categoryToolTip) {
		this.categoryToolTip = categoryToolTip;
	}

	public void setDateToolTip(Tooltip dateToolTip) {
		this.dateToolTip = dateToolTip;
	}

	public void setTagsToolTip(Tooltip tagsToolTip) {
		this.tagsToolTip = tagsToolTip;
	}

	public void setCategoryComboBox(ComboBox<String> categoryComboBox) {
		this.categoryComboBox = categoryComboBox;
	}

	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}

	public void setMessageTextArea(TextArea messageTextArea) {
		this.messageTextArea = messageTextArea;
	}

	public void setOkButtonType(ButtonType okButtonType) {
		this.okButtonType = okButtonType;
	}

	public HBox getPriorityBar() {
		return priorityBar;
	}

	public Label getPriorityLabel() {
		return priorityLabel;
	}

	public Tooltip getPriorityTip() {
		return priorityTip;
	}

	public ComboBox<Priority> getPriorityComboBox() {
		return priorityComboBox;
	}

	public int getSPACING_PRIORITYBAR() {
		return SPACING_PRIORITYBAR;
	}

	public void setPriorityBar(HBox priorityBar) {
		this.priorityBar = priorityBar;
	}

	public void setPriorityLabel(Label priorityLabel) {
		this.priorityLabel = priorityLabel;
	}

	public void setPriorityTip(Tooltip priorityTip) {
		this.priorityTip = priorityTip;
	}

	public void setPriorityComboBox(ComboBox<Priority> priorityComboBox) {
		this.priorityComboBox = priorityComboBox;
	}



}
