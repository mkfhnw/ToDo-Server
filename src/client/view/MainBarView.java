package client.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import client.model.ToDo;

/*
 * This abstract class is the super class
 * of the GUI-side-bar, which changes
 * based on the clicked ListView item
 */
public abstract class MainBarView extends VBox {
	
	// control elements for this container
	private ObservableList<ToDo> subSet;
	private ImageView icon;
	private final ImageView plus;
	private Label label;
	private ImageView lupe;
	private TextField searchField;
	private Button searchButton;
	private Button createToDo;
	private TableView<ToDo> tableView;
	private TableColumn<ToDo, String> important;
	private TableColumn<ToDo, String> task;
	private TableColumn<ToDo, String> dueDate;
	private TableColumn<ToDo, String> checkBox;
	private TableColumn<ToDo, String> garbage;
	private HBox header;
	private HBox searchBar;
	private ObservableList<String> filter;
	private ComboBox<String> comboBox;
	
	// Constructor
	public MainBarView() {

		// Add data
		this.subSet = subSet;

		/*
		 * HBox for the icon and label in the
		 * GUI-SideBar (items will be set in
		 * the subclass)
		 */
		this.header = new HBox();
		this.getChildren().add(header);
		
		// Lupe Icon for the searchField		
		this.lupe = new ImageView("/icons/lupe.png");
		this.lupe.setFitHeight(15);
		this.lupe.setFitWidth(15);
				
		
		// SearchBar and button for creating a new item
		this.createToDo = new Button();
		this.plus = new ImageView("/icons/plusIcon.png");
		this.plus.setFitHeight(15);
		this.plus.setFitWidth(15);
		this.createToDo.setGraphic(plus);
		this.searchBar = new HBox();
		
		/*
		 * A ComboBox for a ToDo filter
		 * helps to see what kind of tasks the user has today,
		 * this week or this month
		 */
		this.filter = FXCollections.observableArrayList(
				"Alle",
				"Heute"
				);
		this.comboBox = new ComboBox<>(filter);
		this.searchBar.getChildren().add(comboBox);		
		
		// Puts the Button and Searchfunction to the right side of the view
		this.searchBar.setPadding(new Insets(0.0, 0.0, 30.0, 650.0));
		this.searchField = new TextField();
		this.searchButton = new Button();
		this.searchButton.setGraphic(this.lupe);
		this.searchBar.getChildren().addAll(createToDo, searchField, searchButton);
		this.getChildren().add(searchBar);
		this.searchField.setMaxWidth(250);

		/*
         * Creates a TableView with Columns
         * and includes data from ObservableArrayList.
         * The setCellValueFactory method specifies a cell factory for each column. 
         */
		this.tableView = new TableView<>();
		this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.tableView.setEditable(true);
		this.tableView.setPrefHeight(600);

		this.important = new TableColumn<>("Wichtig");
		this.important.setCellValueFactory(new PropertyValueFactory<ToDo, String>("importantButton"));
    	    
		this.task = new TableColumn<>("Aufgabe");
		this.task.setCellValueFactory(new PropertyValueFactory<ToDo, String>("title"));
    		
		this.dueDate = new TableColumn<>("Termin");
		this.dueDate.setCellValueFactory(new PropertyValueFactory<ToDo, String>("dueDateString"));

		this.checkBox = new TableColumn<>("Erledigt");
		this.checkBox.setCellValueFactory(new PropertyValueFactory<ToDo, String>("doneButton"));
		
		this.garbage = new TableColumn<>("Papierkorb");
		this.garbage.setCellValueFactory(new PropertyValueFactory<ToDo, String>("garbageButton"));
	
		// Adds Columns to the TableView
		this.tableView.getColumns().addAll(this.important, this.task, this.dueDate, this.checkBox, this.garbage);
    	    
		this.getChildren().addAll(tableView);
						
		this.setPrefHeight(600);
	
		
		// Add CSS styling
		this.getStylesheets().add(getClass().getResource("MainBarView.css").toExternalForm());
		this.getStyleClass().add("mainBarView");
		this.lupe.getStyleClass().add("lupe");
		this.searchButton.getStyleClass().add("searchButton");
		this.searchBar.getStyleClass().add("searchField");
		this.createToDo.getStyleClass().add("createToDo");
		this.tableView.getStyleClass().add("tableView");    
        this.checkBox.getStyleClass().add("checkBox");
        this.task.getStyleClass().add("task");
        this.dueDate.getStyleClass().add("dueDate");
        this.important.getStyleClass().add("important");
        this.garbage.getStyleClass().add("garbage");
        this.important.getStyleClass().add("tableColumn");
        this.task.getStyleClass().add("tableColumn");
        this.dueDate.getStyleClass().add("tableColumn");
        this.checkBox.getStyleClass().add("tableColumn");
        this.garbage.getStyleClass().add("tableColumn");
        this.comboBox.getStyleClass().add("comboBox");
        this.comboBox.getStyleClass().add("combo-box");
      		
	}

	public ComboBox<String> getComboBox() {
		return comboBox;
	}

	
	
}
	

