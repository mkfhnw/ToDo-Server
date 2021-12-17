package client.controller;

import client.ClientNetworkPlugin;
import client.view.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.models.ServerRunnable;
import client.model.FocusTimerModel;
import client.model.Priority;
import client.model.ToDo;
import client.model.ToDoList;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToDoController implements Serializable {

    // Fields
    private final ToDoView toDoView;
    private final ToDo toDo;
    private final ToDoList toDoList;
    private final Stage stage;
    private final Scene scene2;
    private final Scene scene1;

    private ImportantBarView importantBarView;
    private GarbageBarView garbageBarView;
    private PlannedBarView plannedBarView;
    private DoneBarView doneBarView;
    private SearchBarView searchBarView;
    private final FocusTimerDialogPane dialog;
    private FocusTimerModel focusModel;
    
    private LoginView loginView;
    private ClientNetworkPlugin clientNetworkPlugin;

    // Constructor
    public ToDoController(
    		ToDoView toDoView,
    		ToDo toDo,
    		ToDoList toDoList,
    		Stage stage,
    		Scene scene2,
    		LoginView loginView,
    		Scene scene1) {

        this.toDoView = toDoView;
        this.toDo = toDo;
        this.toDoList = toDoList;
        this.stage = stage;
        this.scene2 = scene2;
        this.loginView = loginView;
        this.scene1 = scene1;
        this.focusModel = focusModel;

        this.clientNetworkPlugin = new ClientNetworkPlugin();

        // Load items from database
        this.toDoList.updateSublists();

        // Set default midPane & add initial event handling for searchbar
        this.plannedBarView = new PlannedBarView(this.toDoList.getToDoListPlanned());
        plannedBarView.getCreateToDo().setOnMouseClicked(this::createToDoDialog);
        plannedBarView.getSearchButton().setOnMouseClicked(this::searchItemAndGenerateView);
        this.plannedBarView.getSearchField().setOnAction(this::searchItemAndGenerateView);
        plannedBarView.getComboBox().setOnAction(this::changeCombo);
        plannedBarView.getTableView().setOnMouseClicked(this::updateToDo);
        this.linkTableViewListeners(plannedBarView.getTableView().getItems());
        toDoView.getBorderPane().setCenter(plannedBarView);

        // Register buttons EventHandling
        this.toDoView.getListView().setOnMouseClicked(this::changeCenterBar);

        // Focus timer button EventHandling
        this.toDoView.getOpenFocusTimer().setOnMouseClicked(this::createFocusTimer);

        // Add focus timer dialog and model 
        this.dialog = new FocusTimerDialogPane();
        this.focusModel = new FocusTimerModel(null);
        
        // HowTo Button EventHandling
        this.toDoView.getHowTo().setOnMouseClicked(this::createHowTo);
        
        // EventHandling for play, stop or replay How-To Video
        this.toDoView.getHowToDialogPane().getPlayButton().setOnMouseClicked(this::playMedia);
        this.toDoView.getHowToDialogPane().getStopButton().setOnMouseClicked(this::stopMedia);
        this.toDoView.getHowToDialogPane().getReplayButton().setOnMouseClicked(this::replayMedia);

        // EventHandling for logout
        this.toDoView.getLogoutButton().setOnMouseClicked(this::logout);

        // EventHandling for registration
        this.loginView.getRegisterButton().setOnMouseClicked(this::openRegistration);
        
        // EventHandling to open ToDoApp
        this.loginView.getSignInButton().setOnMouseClicked(this::handleLogin);
       
        // EventHandling for changing password
        this.toDoView.getChangePasswordItem().setOnAction(this::changePassword);

        
        // Instantiate barchart with utils
        Timeline Updater = new Timeline(new KeyFrame(Duration.seconds(0.3), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                toDoView.getSerie1().getData().clear();
                toDoView.getSerie2().getData().clear();
                toDoView.getSerie1().getData().add(new XYChart.Data<String, Number>("Erledigt", toDoList.getToDoListDone().size()));
                toDoView.getSerie2().getData().add(new XYChart.Data<String, Number>("Geplant", toDoList.getToDoListPlanned().size()));
            }
        }));

        Updater.setCycleCount(Timeline.INDEFINITE);
        Updater.play();
        toDoView.getBc().getData().addAll(toDoView.getSerie1(), toDoView.getSerie2());
    }


	// ---------------------------------- Classic Getters
    public ToDoView getToDoView() {
        return toDoView;
    }
    public ToDo getToDo() {
        return toDo;
    }
    public ToDoList getToDoList() {
        return toDoList;
    }
    public ImportantBarView getImportantBarView() {
        return importantBarView;
    }
    public GarbageBarView getGarbageBarView() {
        return garbageBarView;
    }
    public PlannedBarView getPlannedBarView() {
        return plannedBarView;
    }
    public DoneBarView getDoneBarView() {
        return doneBarView;
    }
    public SearchBarView getSearchBarView() {
        return searchBarView;
    }
    public FocusTimerDialogPane getDialog() {
        return dialog;
    }
    public FocusTimerModel getModel() {
        return focusModel;
    }

    // ---------------------------------- CRUD-Methods
    /* Create method
     * Parses the inputs of the user required for a new ToDoInstance, creates the instance and stores it.
     * Needs input from ToDoView
     */
    public void createToDo(String title, String message, LocalDate dueDate, String category, String priorityString,
                           ArrayList<String> tags) {
        ToDo toDo = new ToDo(title, message, dueDate, category, priorityString, tags);
        this.toDoList.addToDo(toDo);
        this.toDoList.updateSublists();

        // Send data to server
        this.clientNetworkPlugin.createToDo(toDo);

    }

    /* Read method
     * Returns a ToDo based on its ID
     */
    public ToDo getToDo(int ID) {
    	this.clientNetworkPlugin.getToDo(ID);
    	return this.toDoList.getToDo(ID);

    }

    /* Update method
     * Gets a specific ToDo based on its ID, updated the contents and stores it again.
     * Maybe pass in an ToDo as parameter?
     */
    public void updateToDo(MouseEvent e) {

        // Check for double click
        if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {

            // Get clicked item
            MainBarView activeMidView = (MainBarView) this.getActiveMidView();
            int index = activeMidView.getTableView().getSelectionModel().getSelectedIndex();

            // Don't run if double click on table head
            if (index != -1) {
                ObservableList<ToDo> items = activeMidView.getTableView().getItems();
                ToDo itemToUpdate = items.get(index);

                // Open new dialogPane to make it editable
                this.toDoView.setAddToDoDialog(new Dialog<ButtonType>());
                this.toDoView.setToDoDialogPane(new AddToDoDialogPane(this.toDoView.getListView().getItems(), itemToUpdate));
                this.toDoView.getAddToDoDialog().setDialogPane(this.toDoView.getToDoDialogPane());
                Optional<ButtonType> result = this.toDoView.getAddToDoDialog().showAndWait();

                // Parse only positive result, ignore CANCEL_CLOSE
                if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {

                    // Validate user input
                    if (this.validateUserInput()) {

                        // Delete old item from arrayList
                        // this.toDoList.removeToDo(itemToUpdate);

                        // Parse out data
                        String title = this.toDoView.getToDoDialogPane().getTitleTextfield().getText();
                        String category = this.toDoView.getToDoDialogPane().getCategoryComboBox().getValue();
                        String message = this.toDoView.getToDoDialogPane().getMessageTextArea().getText();
                        String dueDateString = this.toDoView.getToDoDialogPane().getDatePicker().getValue().toString();
                        String tags = this.toDoView.getToDoDialogPane().getTagsTextfield().getText();


                        String[] tagArray = tags.replaceAll("\\s", "").split(";");
                        ArrayList<String> tagArrayList = new ArrayList<String>(List.of(tagArray));

                        // TODO: We're double-creating the item here
                        // this.createToDo(title, message, LocalDate.parse(dueDateString), category, tagArrayList);
                        ToDo updatedItem = new ToDo(itemToUpdate.getID(), title, message, LocalDate.parse(dueDateString),
                                itemToUpdate.getDateOfCreation(), category, tagArrayList, true);
                        this.toDoList.updateToDo(itemToUpdate, updatedItem);

                    }

                }
            }

        }

        // Update lists
        this.updateInstancedSublists();
    }

    /* Delete method
     * Gets a specific ToDo based on its ID and deletes it.
     */
    public void deleteToDo(int ID) {

        // Fetch ToDo item
        ToDo itemToRemove = this.toDoList.getToDo(ID);
        this.toDoList.removeToDo(itemToRemove);
        
        
    }


    // ---------------------------------- Methods to change items
    /* Method to set a ToDo on done ("Erledigt") whenever the button is clicked.
     * Fetches out the corresponding toDo from the button clicked
     * Deletes all other categories from the toDo, since a toDo can only be 'done' when it's done
     */
    public void setToDoOnDone(MouseEvent e) {
        ToDo toDo = toDoList.getToDo((Button) e.getSource());
        // TODO: Either write new constructor for a ToDo where we pass in an existing instance of a ToDo together with
        // a string that is being set as category. Or use an existing constructor and pass in all the elements
        // so we have an old and a new item to pass into the sqliteManager.update() method.
        toDo.setCategory("Erledigt");
        toDo.setDone(true);
        this.updateInstancedSublists();
    }

    /* Method to mark ToDo as important
     * ToDo gets deleted from preceding sublist via the .setCategory method.
     */
    private void setToDoAsImportant(MouseEvent e) {
        ToDo toDo = toDoList.getToDo((Button) e.getSource());
        toDo.setCategory("Wichtig");
        this.updateInstancedSublists();
    }

    /* Method to mark Item as garbage
     * Item gets deleted from preceding sublist via the .setCategory method.
     * Deletes the item from the database as well.
     */
    private void setToDoAsGarbage(MouseEvent e) {
        ToDo toDo = toDoList.getToDo((Button) e.getSource());
        toDo.setCategory("Papierkorb");
        int ID = toDo.getID();
        this.clientNetworkPlugin.deleteToDo(ID);
        this.updateInstancedSublists();
        

    }


    // ---------------------------------- Searchbar-method
    /* Method that is linked to the searchButton
     * Does not generate a new view and is only used by searchItemAndGenerateView
     */
    private void searchItem(MouseEvent e) {

        // Fetch input
        MainBarView midView = (MainBarView) this.getActiveMidView();
        String searchString = midView.getSearchField().getText();

        // Clear pane
        ((MainBarView) this.getActiveMidView()).getTableView().getItems().clear();

        // Search items
        ArrayList<ToDo> searchList = this.toDoList.searchItem(searchString);

        // Populate list
        ((MainBarView) this.getActiveMidView()).getTableView().getItems().addAll(searchList);

    }

    /* Method that is linked to the searchButton
     * Generates a new view and sets it to the center
     */
    private void searchItemAndGenerateView(MouseEvent e) {

        // Fetch input
        MainBarView midView = (MainBarView) this.getActiveMidView();
        String searchString = midView.getSearchField().getText();

        // Only go ahead if input is not empty
        if (searchString.length() != 0) {

            // Search items
            ArrayList<ToDo> searchList = this.toDoList.searchItem(searchString);
            ObservableList<ToDo> observableSearchList = FXCollections.observableArrayList(searchList);

            // Generate new searchView
            this.searchBarView = new SearchBarView(observableSearchList);
            this.searchBarView.getCreateToDo().setOnMouseClicked(this::createToDoDialog);
            this.linkTableViewListeners(searchBarView.getTableView().getItems());
            this.searchBarView.getTableView().setOnMouseClicked(this::updateToDo);
            this.searchBarView.getSearchButton().setOnMouseClicked(this::searchItem);

            // Put it on main view
            toDoView.getBorderPane().setCenter(this.searchBarView);

        }

        // Otherwise just consume the event
        e.consume();
    }

    // Takes the enter key instead of a mouseclick on the search button
    private void searchItemAndGenerateView(ActionEvent ae) {

    	 // Fetch input
        MainBarView midView = (MainBarView) this.getActiveMidView();
        String searchString = midView.getSearchField().getText();

        // Only go ahead if input is not empty
        if (searchString.length() != 0) {

            // Search items
            ArrayList<ToDo> searchList = this.toDoList.searchItem(searchString);
            ObservableList<ToDo> observableSearchList = FXCollections.observableArrayList(searchList);

            // Generate new searchView
            this.searchBarView = new SearchBarView(observableSearchList);
            this.searchBarView.getCreateToDo().setOnMouseClicked(this::createToDoDialog);
            this.linkTableViewListeners(searchBarView.getTableView().getItems());
            this.searchBarView.getTableView().setOnMouseClicked(this::updateToDo);
            this.searchBarView.getSearchButton().setOnMouseClicked(this::searchItem);

            // Put it on main view
            toDoView.getBorderPane().setCenter(this.searchBarView);

        }

        // Otherwise just consume the event
        ae.consume();
    }

    /* Method to update local as well as instantiated sublists
     * Updates the sublists of the controller, as well as each sublist in the different instantiated views
     */
    private void updateInstancedSublists() {

        // Update current sublists
        this.toDoList.updateSublists();

        // Update sublists in each view
        if (this.importantBarView != null) {
            this.importantBarView.getTableView().getItems().clear();
            this.importantBarView.getTableView().getItems().addAll(this.toDoList.getToDoListImportant());
            this.linkTableViewListeners(this.importantBarView.getTableView().getItems());
        }

        if (this.garbageBarView != null) {
            this.garbageBarView.getTableView().getItems().clear();
            this.garbageBarView.getTableView().getItems().addAll(this.toDoList.getToDoListGarbage());
            this.linkTableViewListeners(this.garbageBarView.getTableView().getItems());
        }

        if (this.plannedBarView != null) {
            this.plannedBarView.getTableView().getItems().clear();
            this.plannedBarView.getTableView().getItems().addAll(this.toDoList.getToDoListPlanned());
            this.linkTableViewListeners(this.plannedBarView.getTableView().getItems());
        }

        if (this.doneBarView != null) {
            this.doneBarView.getTableView().getItems().clear();
            this.doneBarView.getTableView().getItems().addAll(this.toDoList.getToDoListDone());
            this.linkTableViewListeners(this.doneBarView.getTableView().getItems());
        }
    }

    /* Method that is used to retrieve the active midView
     *
     */
    private Node getActiveMidView() {
        return this.toDoView.getBorderPane().getCenter();
    }

    /* Method to set event handlers for the tableView Items
     *
     */
    private void linkTableViewListeners(ObservableList<ToDo> listItems) {
        for (ToDo toDo : listItems) {
            toDo.getDoneButton().setOnMouseClicked(this::setToDoOnDone);
            toDo.getImportantButton().setOnMouseClicked(this::setToDoAsImportant);
            toDo.getGarbageButton().setOnMouseClicked(this::setToDoAsGarbage);
        }
    }

    /* Method to change center view of GUI
     * ----------------------------------- Swapping out centerView
     * We set up a clickListener on the (main) listView and listen on any click
     * On a click, we parse out which item was clicked by its index
     * Based on which item was clicked, we swap out the center of the main borderPane with the corresponding view
     * ----------------------------------- Adding listeners to the rows of the tableview
     * Since we have buttons inside the tableView, they need to be addressed by the controller as well.
     * However, the concept of a javaFX-tableView intends to represent instances of a model inside each row.
     * On the other hand, the MVC pattern demands a strict separation of model (data), and view.
     * This leads to a dilemma, where each solution violates one of the concepts. Placing the button inside the model
     * violates the MVC concept, placing the button inside the tableView via a workaround violates the intends of javaFX.
     * Anyhow - we see less dissonance in adding a button to a model, since this can be perceived as a "trait" of the model.
     */
    private void changeCenterBar(MouseEvent e) {
        switch (toDoView.getListView().getSelectionModel().getSelectedIndex()) {
            case 0 -> {
                // Create new instance of the view, populated with up-to-date dataset
                this.importantBarView = new ImportantBarView(this.toDoList.getToDoListImportant());

                // Add listeners
                importantBarView.getCreateToDo().setOnMouseClicked(this::createToDoDialog);
                linkTableViewListeners(importantBarView.getTableView().getItems());
                importantBarView.getSearchButton().setOnMouseClicked(this::searchItemAndGenerateView);
                importantBarView.getSearchButton().setOnAction(this::searchItemAndGenerateView);
                importantBarView.getComboBox().setOnAction(this::changeCombo);
                importantBarView.getTableView().setOnMouseClicked(this::updateToDo);

                // Put it on main view
                toDoView.getBorderPane().setCenter(importantBarView);
            }
            case 1 -> {
                plannedBarView = new PlannedBarView(this.toDoList.getToDoListPlanned());
                plannedBarView.getCreateToDo().setOnMouseClicked(this::createToDoDialog);
                plannedBarView.getSearchButton().setOnMouseClicked(this::searchItemAndGenerateView);
                plannedBarView.getSearchButton().setOnAction(this::searchItemAndGenerateView);
                plannedBarView.getComboBox().setOnAction(this::changeCombo);
                plannedBarView.getTableView().setOnMouseClicked(this::updateToDo);
                linkTableViewListeners(plannedBarView.getTableView().getItems());
                toDoView.getBorderPane().setCenter(plannedBarView);
                
            }
            case 2 -> {
                doneBarView = new DoneBarView(this.toDoList.getToDoListDone());
                doneBarView.getCreateToDo().setOnMouseClicked(this::createToDoDialog);
                doneBarView.getSearchButton().setOnMouseClicked(this::searchItemAndGenerateView);
                doneBarView.getSearchButton().setOnAction(this::searchItemAndGenerateView);
                doneBarView.getComboBox().setOnAction(this::changeCombo);
                doneBarView.getTableView().setOnMouseClicked(this::updateToDo);
                linkTableViewListeners(doneBarView.getTableView().getItems());
                toDoView.getBorderPane().setCenter(doneBarView);
            }
            case 3 -> {
                garbageBarView = new GarbageBarView(this.toDoList.getToDoListGarbage());
                garbageBarView.getCreateToDo().setOnMouseClicked(this::createToDoDialog);
                garbageBarView.getSearchButton().setOnMouseClicked(this::searchItemAndGenerateView);
                garbageBarView.getSearchButton().setOnAction(this::searchItemAndGenerateView);
                garbageBarView.getComboBox().setOnAction(this::changeCombo);
                garbageBarView.getTableView().setOnMouseClicked(this::updateToDo);
                linkTableViewListeners(garbageBarView.getTableView().getItems());
                toDoView.getBorderPane().setCenter(garbageBarView);
            }
        }
    }


    // ---------------------------------- Creation Dialog methods
    /* Validate user input method
     *
     */
    private boolean validateUserInput() {

        // Parse out data
        String title = this.toDoView.getToDoDialogPane().getTitleTextfield().getText();
        String category = this.toDoView.getToDoDialogPane().getCategoryComboBox().getValue();
        String message = this.toDoView.getToDoDialogPane().getMessageTextArea().getText();
        String dueDateString = "";
        try {
            dueDateString = this.toDoView.getToDoDialogPane().getDatePicker().getValue().toString();
            if (dueDateString.equals("")) {
                // Setting default date to today
                this.toDoView.getToDoDialogPane().getDatePicker().setValue(LocalDate.now());
            }
        } catch (NullPointerException e) {
            // Setting default date to today
            this.toDoView.getToDoDialogPane().getDatePicker().setValue(LocalDate.now());
            dueDateString = LocalDate.now().toString();
        }

        String tags = this.toDoView.getToDoDialogPane().getTagsTextfield().getText();

        // Set default category if none is chosen
        // Note that we need to update the stored variable as it is used for the validity check later
        if (category == null) {
            this.toDoView.getToDoDialogPane().getCategoryComboBox().setValue("Geplant");
            category = this.toDoView.getToDoDialogPane().getCategoryComboBox().getValue();
        }

        // Validate easy inputs first
        boolean titleIsValid = title.length() >= 3 && title.length() <= 20;
        boolean messageIsValid = message.length() <= 255;
        boolean categoryIsValid = this.toDoView.getListView().getItems().contains(category);
        boolean tagsAreValid = false;
        String[] tagArray;

        // Validate date
        boolean dateIsValid = false;
        LocalDate paneDate = LocalDate.parse(dueDateString);
        if (paneDate.compareTo(LocalDate.now()) >= 0) {
            dateIsValid = true;
        }

        // Validate tags
        // Removes all whitespace and non-visible characters with \\s and splits the string by ;
        try {
            tagArray = tags.replaceAll("\\s", "").split(";");
            tagsAreValid = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tagsAreValid = false;
        }

        // Give graphical feedback
        if (!titleIsValid) {
            this.toDoView.getToDoDialogPane().getTitleTextfield().getStyleClass().add("notOk");
        }
        if (!messageIsValid) {
            this.toDoView.getToDoDialogPane().getMessageTextArea().getStyleClass().add("notOk");
        }
        if (!categoryIsValid) {
            this.toDoView.getToDoDialogPane().getCategoryComboBox().getStyleClass().add("notOk");
        }
        if (!dateIsValid) {
            this.toDoView.getToDoDialogPane().getDatePicker().getStyleClass().add("notOk");
        }
        if (!tagsAreValid) {
            this.toDoView.getToDoDialogPane().getTagsTextfield().getStyleClass().add("notOk");
        }

        return (titleIsValid && messageIsValid && categoryIsValid && dateIsValid && tagsAreValid);

    }

    /* Dialog creation method
     * Shows the dialog to get input from the user required for a new ToDO
     * After user has made his input, controller parses out the data and creates a new ToDo
     * After the new ToDo is created, it wipes the inputs form the dialog pane so we can set up a clean, new dialog
     */
    public void createToDoDialog(MouseEvent e) {

        // Create & Customize Dialog
        this.toDoView.setAddToDoDialog(new Dialog<ButtonType>());
        this.toDoView.setToDoDialogPane(new AddToDoDialogPane(this.toDoView.getListView().getItems()));
        this.toDoView.getAddToDoDialog().setDialogPane(this.toDoView.getToDoDialogPane());

        this.toDoView.getAddToDoDialog().setTitle("Neue Aufgabe");
        Stage stage = (Stage) toDoView.getAddToDoDialog().getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/common/resources/doneIcon4.png").toString()));
		

        // Set up event filter on OK-button to prevent dialog from closing when user input is not valid
        Button okButton = (Button) this.toDoView.getToDoDialogPane().lookupButton(this.toDoView.getToDoDialogPane().getOkButtonType());
        okButton.addEventFilter(ActionEvent.ACTION,
                event -> {
                    if (!validateUserInput()) {
                        event.consume();
                    }
                });

        // Clear graphical validation
        this.toDoView.getToDoDialogPane().getTitleTextfield().getStyleClass().remove("notOk");
        this.toDoView.getToDoDialogPane().getMessageTextArea().getStyleClass().remove("notOk");
        this.toDoView.getToDoDialogPane().getCategoryComboBox().getStyleClass().remove("notOk");
        this.toDoView.getToDoDialogPane().getDatePicker().getStyleClass().remove("notOk");
        this.toDoView.getToDoDialogPane().getTagsTextfield().getStyleClass().remove("notOk");

        // Show dialog
        Optional<ButtonType> result = this.toDoView.getAddToDoDialog().showAndWait();

        // Parse only positive result, ignore CANCEL_CLOSE
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {

            // Validate user input
            if (this.validateUserInput()) {

                // Parse out data
                String title = this.toDoView.getToDoDialogPane().getTitleTextfield().getText();
                String category = this.toDoView.getToDoDialogPane().getCategoryComboBox().getValue();
                String message = this.toDoView.getToDoDialogPane().getMessageTextArea().getText();
                String dueDateString = this.toDoView.getToDoDialogPane().getDatePicker().getValue().toString();
                String tags = this.toDoView.getToDoDialogPane().getTagsTextfield().getText();
                String priorityString = this.toDoView.getToDoDialogPane().getPriorityComboBox().getValue().toString();
                String[] tagArray = tags.replaceAll("\\s", "").split(";");
                ArrayList<String> tagArrayList = new ArrayList<String>(List.of(tagArray));
                this.createToDo(title, message, LocalDate.parse(dueDateString), category, priorityString, tagArrayList);

            }

        }

        // Clear out dialogPane
        this.toDoView.getToDoDialogPane().clearPane();

        // Add editing functionality
        MainBarView midView = (MainBarView) this.getActiveMidView();
        midView.getTableView().setOnMouseClicked(this::updateToDo);

        // Refresh views
        this.updateInstancedSublists();

    }


    // ---------------------------------- Focus timer methods

    // Open a new focus timer window
    public void createFocusTimer(MouseEvent e) {

    	this.toDoView.getFocusTimerDialog().getModel().restart();
    	this.toDoView.getFocusTimerDialog().getModel().stop();

    	((FocusTimerDialogPane) this.toDoView.getFocusDialog().getDialogPane()).getPlayButton().setOnAction(a->{
    	this.toDoView.getFocusTimerDialog().getModel().start();
    	});
    	((FocusTimerDialogPane) this.toDoView.getFocusDialog().getDialogPane()).getStopButton().setOnAction(a->{
    	this.toDoView.getFocusTimerDialog().getModel().stop();
    	});
    	((FocusTimerDialogPane) this.toDoView.getFocusDialog().getDialogPane()).getReplayButton().setOnAction(a->{
    	this.toDoView.getFocusTimerDialog().getModel().restart();
    	});
    	this.toDoView.getFocusDialog().showAndWait();
    	}
    	

    /*
     * Depending on which date filter (ComboBox) the user choosed,
     * the ToDo task-view will change.
     */
    private void changeCombo(ActionEvent event) {

        // Update sublists
        this.updateInstancedSublists();

        // Set items based on selected category
        MainBarView main = (MainBarView) getActiveMidView();
        switch (main.getComboBox().getSelectionModel().getSelectedIndex()) {
            case 0: {
                String selectedCategory = this.toDoView.getListView().getSelectionModel().getSelectedItem();
                ObservableList<ToDo> resultSet = FXCollections.observableArrayList();
                switch (selectedCategory) {

                    case "Geplant": {
                        resultSet = this.toDoList.getToDoListPlanned();
                        break;
                    }
                    case "Wichtig": {
                        resultSet = this.toDoList.getToDoListImportant();
                        break;
                    }
                    case "Papierkorb": {
                        resultSet = this.toDoList.getToDoListGarbage();
                        break;
                    }
                    case "Erledigt": {
                        resultSet = this.toDoList.getToDoListDone();
                    }

                }

                main.getTableView().getItems().clear();
                main.getTableView().getItems().addAll(resultSet);
                break;
            }
            case 1: {

                String selectedCategory = this.toDoView.getListView().getSelectionModel().getSelectedItem();
                ObservableList<ToDo> resultSet = FXCollections.observableArrayList();
                switch (selectedCategory) {

                    case "Geplant": {
                        ArrayList<ToDo> arrayListToday = this.toDoList.searchLocalToday();
                        for(ToDo item : arrayListToday) {
                            if(item.getCategories().contains("Geplant")) { resultSet.add(item); }
                        }
                        break;
                    }
                    case "Wichtig": {
                        ArrayList<ToDo> arrayListToday = this.toDoList.searchLocalToday();
                        for(ToDo item : arrayListToday) {
                            if(item.getCategories().contains("Wichtig")) { resultSet.add(item); }
                        }
                        break;
                    }
                    case "Papierkorb": {
                        ArrayList<ToDo> arrayListToday = this.toDoList.searchLocalToday();
                        for(ToDo item : arrayListToday) {
                            if(item.getCategories().contains("Papierkorb")) { resultSet.add(item); }
                        }
                        break;
                    }
                    case "Erledigt": {
                        ArrayList<ToDo> arrayListToday = this.toDoList.searchLocalToday();
                        for(ToDo item : arrayListToday) {
                            if(item.getCategories().contains("Erledigt")) { resultSet.add(item); }
                        }
                    }

                }

                ObservableList<ToDo> observableListToday = FXCollections.observableArrayList(resultSet);
                main.getTableView().getItems().clear();
                main.getTableView().getItems().addAll(observableListToday);
            }
            
        }
    }

    
    public void playTimer(MouseEvent event) {
    	focusModel.start();
    }
    
    public void stopTimer(MouseEvent event) {
    	focusModel.stop();
    }
    
    public void replayTimer(MouseEvent event) {
    	focusModel.restart();
    }
    
    
    
    // Open a new focus timer window
  public void createHowTo(MouseEvent e) {
    		  
        // show dialog
        this.toDoView.getHowToDialog().showAndWait();
	  	this.toDoView.getHowToDialogPane().getMediaPlayer().stop();
        
        
        // If ButtonType "beenden" is clicked, stop the Video
        if (toDoView.getHowToDialogPane().getCloseButtonType().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
 		   
 		   toDoView.getHowToDialogPane().getMediaPlayer().stop();  
    }
  }
  
  // Plays HowTo video
  
  public void playMedia(MouseEvent event) {
	  
	  this.toDoView.getHowToDialogPane().getMediaPlayer().play();
  }
  
  //Stops HowTo video
  
  public void stopMedia(MouseEvent event) {
	  
	  this.toDoView.getHowToDialogPane().getMediaPlayer().pause();
	  
  }
  //Replays HowTo video
  
  public void replayMedia(MouseEvent event) {
	  
	  this.toDoView.getHowToDialogPane().getMediaPlayer().stop();
	  
  }
  
 /*
  * Parses login data and checks if ok.
  * If ok, the App will open,
  * if not ok, Alert Box will open.
  */
  public void handleLogin(MouseEvent event) {
	  	
	String emailLogin = loginView.getUserField().getText();
  	String passwordLogin = loginView.getPasswordField().getText();
  	
  	boolean result = this.clientNetworkPlugin.login(emailLogin, passwordLogin);

  	if (result) {
  		Platform.runLater(() -> {
  		this.stage.setScene(scene2);
  		stage.show();
  		});
  	} else {
  		// Alertdialog if Login failed
  		Alert alert = new Alert(AlertType.NONE);

  		 // Action event
        EventHandler<ActionEvent> eventAlert = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                // set alert type
                alert.setAlertType(AlertType.ERROR);

                // set content text
                alert.setContentText("Error Login failed");

                // show the dialog
                alert.show();
            }
        };
  	}

  }
  
  public boolean getNewAccount() {
	  
	String emailCreateLogin = this.loginView.getRegistrationDialogPane().getEmailField().getText();
  	String passwordCreateLogin = this.loginView.getRegistrationDialogPane().getPasswordField().getText();
  	
  	boolean result = this.clientNetworkPlugin.createLogin(emailCreateLogin, passwordCreateLogin);
  	return result;
  	
  }
  
  
  
  public boolean validatePassword() {
	  if (this.loginView.getRegistrationDialogPane().getPasswordField().getText().length() >= 3
		 && this.loginView.getRegistrationDialogPane().getPasswordField().getText().length() <= 20) {
		  return true;
	  } else {
		  
		 this.loginView.getRegistrationDialogPane().getPasswordField().getStyleClass().add("notOk");
		 
		// Alertdialog if Login failed
	  		Alert alert = new Alert(AlertType.NONE);

	  		 // Action event
	        EventHandler<ActionEvent> eventAlert = new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent e)
	            {
	                // set alert type
	                alert.setAlertType(AlertType.ERROR);

	                // set content text
	                alert.setContentText("Error Password failed");

	                // show the dialog
	                alert.show();
	            }
	        };
		  return false;
	  }
	    
  }
  
  public void logout(MouseEvent event) {
	// ALARMDIALOG?
	
	  boolean result = this.clientNetworkPlugin.logout();
	  
	  //if user logged out, show LoginView again
	  if (result) {
		  this.stage.close();
		  this.stage.setScene(scene1);
		  this.stage.show();
	  }
	  
	  // Close App & back to LoginView (scene1)
	  
	  }
  
  public void changePassword(ActionEvent event) {
	  
			toDoView.getChangePasswordDialog().showAndWait();
			 
			String password = this.toDoView.getChangePasswordDialogPane().getNewPasswordField().getText();
			this.clientNetworkPlugin.changePassword(password);
			
			
			//Password validation, alarming if not between 3 and 20 characters
			// validateNewPassword();

			  
		}


  

//   public boolean validateNewPassword() {
  
//	  }
  
  public void registerAccount() {
	  
	// Set up event filter on OK-button to prevent dialog from closing when user input is not valid
      Button okButton = (Button) this.loginView.getRegistrationDialogPane().lookupButton(this.loginView.getRegistrationDialogPane().getOkButtonType());
      okButton.addEventFilter(ActionEvent.ACTION,
              event -> {
            	  //Validation is not working right, you can create a user with a password that does not matches the requirements
            	  //the registrationView closes anyway, even if password incorrect -> needs to be fixed
                  if (!validatePassword()) {
                	  event.consume();
                      
                  } else {
                	  boolean result = getNewAccount();
                	  if (result) {
                    	  Platform.runLater(() -> {
                    	  		this.stage.setScene(scene1);
                    	  		stage.show();
                    	  		});    		
                      } else {
                    	  
                      }
                  }
              });

	  
  }


public void openRegistration(MouseEvent event) {
	  
	// show dialog
      this.loginView.getRegistrationDialog().showAndWait();
      
      registerAccount();
      
    	  // save login?
  }
  
 

}

		
	

	

	
		
	

