package client.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import client.model.ToDo;
import client.model.ToDoList;

public class ToDoView extends BorderPane {
	
	// Control elements for this container
	
		private ToDo toDoModel;
		private ToDoList toDoListModel;

		private ListView <String> listView;
		private VBox vBox;
		private BorderPane borderPane;
		private SplitPane splitPane;
		private StackPane stackPane;
		
		private Dialog<ButtonType> addToDoDialog;
		private AddToDoDialogPane toDoDialogPane;
		
		final static String done = "Erledigt";
		final static String undone = "Geplant";
				
		private final CategoryAxis xAxis;
		private final NumberAxis yAxis;
		private BarChart<String, Number> bc;
		private XYChart.Series serie1;
		private XYChart.Series serie2;
		
		private Dialog<ButtonType> focusDialog;
		private FocusTimerDialogPane focusTimerDialog;
		private Button openFocusTimer;
		
		private Dialog<ButtonType> howToDialog;
		private HowToDialogPane howToDialogPane;
		private Button howTo;
				
		private HBox hBoxHowTo;
		private VBox vBoxBottom;
		private HBox hBoxBottom;
		
		private Button logoutButton;
		private Button changePasswordButton;
		/*
		 * Instantiates all necessary control elements
		 * and adds them to the container
		 */
		public ToDoView(ToDo toDoModel, ToDoList toDoListModel) {
			
			// Instantiates our classes
			this.toDoModel = toDoModel;
			this.toDoListModel = toDoListModel;
			
			// Creates a ListView with items and sets the active item
			this.listView = new ListView<String>();
			listView.getItems().addAll(
					"Wichtig",
					"Geplant",
					"Erledigt",
					"Papierkorb");
			listView.getSelectionModel().select(1);
			
			// Creates a VBox in the BorderPane and includes the listView
			this.vBox = new VBox();
			this.vBox.getChildren().addAll(listView);
			this.setLeft(this.vBox);
			
			/*
			 * Creates a BorderPane in a BorderPane
			 * This is for the view on the right side
			 */
			this.borderPane = new BorderPane();
			this.setCenter(borderPane);
			this.borderPane.setPrefSize(1000, 600);
			

			/*
			 * Creates a SplitPane between vBox and borderPane
			 * This SplitPane should divide the GUI in two
			 * main views (List on the left, View on the right)
			 */
			this.splitPane = new SplitPane();
			this.splitPane.getItems().addAll(vBox, borderPane);
			this.splitPane.setDividerPositions(0.3);
			this.setLeft(splitPane);

			VBox buffer = new VBox();
			buffer.setPrefHeight(60.0);
			this.vBox.getChildren().add(buffer);
			
			//Creating the BarChart to show the done and undone ToDo's
			this.xAxis = new CategoryAxis();
			this.yAxis = new NumberAxis();
			this.bc = new BarChart<String, Number>(xAxis, yAxis);
			
			bc.setTitle("Status Überblick");
			xAxis.setLabel("Kategorie");
			yAxis.setLabel("Anzahl");
			bc.setAnimated(false);
			
			this.serie1 = new XYChart.Series();
			serie1.setName(done);
			this.serie2 = new XYChart.Series<>();
			serie2.setName(undone);	
			
						
			this.vBox.getChildren().add(bc);
			
			/*
			 * Button Focus timer for a focus timer dialog
			 * on the right side of the bottom of the BorderPane
			 */
			this.openFocusTimer = new Button("Fokus Timer");
			this.howTo = new Button("How to");
			this.logoutButton = new Button("Abmelden");
			this.changePasswordButton = new Button("Passwort ändern");
			
			this.vBoxBottom = new VBox();
			
			this.vBoxBottom.getChildren().addAll(openFocusTimer, howTo, logoutButton);
			this.vBoxBottom.setPadding(new Insets(50.0, 00.0, 50.0, 50.0));
			this.vBoxBottom.setSpacing(30);
			this.vBoxBottom.setAlignment(Pos.CENTER);
			
			this.borderPane.setBottom(vBoxBottom);
		    
			// Add CSS styling
			this.getStylesheets().add(getClass().getResource("ToDoViewStyleSheet.css").toExternalForm());
			this.getStyleClass().add("view");
			this.listView.getStylesheets().add(getClass().getResource("ListViewStyleSheet.css").toExternalForm());
			this.vBox.getStyleClass().add("vBox");
			this.splitPane.getStyleClass().add("splitPane");
			this.borderPane.getStyleClass().add("borderPane");
			this.openFocusTimer.getStyleClass().add("openFocusTimer");
			this.bc.getStylesheets().add(getClass().getResource("BarChartStyleSheet.css").toExternalForm());
			this.howTo.getStyleClass().add("howTo");
	        
			
			// Create and customize Focus timer - Dialog
			this.focusDialog = new Dialog<ButtonType>();
			this.focusDialog.setTitle("Fokus Timer");
			
			Stage stage = (Stage) focusDialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource("/common/resources/timer.png").toString()));
			
			this.focusTimerDialog = new FocusTimerDialogPane();
			this.focusDialog.setDialogPane(focusTimerDialog);
			
			this.focusDialog.initModality(Modality.NONE);
			
			// Create and costumize HowTo Dialog
			this.howToDialog = new Dialog<ButtonType>();
			this.howToDialog.setTitle("How-To");
			Stage stage2 = (Stage) howToDialog.getDialogPane().getScene().getWindow();
			stage2.getIcons().add(new Image(this.getClass().getResource("/common/resources/howTo.png").toString()));
			
			this.howToDialogPane = new HowToDialogPane();
			this.howToDialog.setDialogPane(howToDialogPane);
			
			this.howToDialog.initModality(Modality.NONE);
			


		}

		public ToDo getToDoModel() {
			return toDoModel;
		}

		public ToDoList getToDoListModel() {
			return toDoListModel;
		}

		public ListView<String> getListView() {
			return listView;
		}

		public VBox getvBox() {
			return vBox;
		}

		public BorderPane getBorderPane() {
			return borderPane;
		}

		public SplitPane getSplitPane() {
			return splitPane;
		}

		public StackPane getStackPane() {
			return stackPane;
		}

		public Dialog<ButtonType> getAddToDoDialog() {
			return addToDoDialog;
		}

		public AddToDoDialogPane getToDoDialogPane() {
			return toDoDialogPane;
		}

		public static String getDone() {
			return done;
		}

		public static String getUndone() {
			return undone;
		}

		public CategoryAxis getxAxis() {
			return xAxis;
		}

		public NumberAxis getyAxis() {
			return yAxis;
		}

		public BarChart<String, Number> getBc() {
			return bc;
		}

		public XYChart.Series getSerie1() {
			return serie1;
		}

		public XYChart.Series getSerie2() {
			return serie2;
		}

		public Dialog<ButtonType> getFocusDialog() {
			return focusDialog;
		}

		public FocusTimerDialogPane getFocusTimerDialog() {
			return focusTimerDialog;
		}

		public Button getOpenFocusTimer() {
			return openFocusTimer;
		}

		public Dialog<ButtonType> getHowToDialog() {
			return howToDialog;
		}

		public HowToDialogPane getHowToDialogPane() {
			return howToDialogPane;
		}

		public Button getHowTo() {
			return howTo;
		}
		
		public Button getLogoutButton() {
			return logoutButton;
		}
		
		public Button getChangePasswordButton() {
			return changePasswordButton;
		}

		public HBox gethBoxHowTo() {
			return hBoxHowTo;
		}

		public VBox getvBoxBottom() {
			return vBoxBottom;
		}

		public HBox gethBoxBottom() {
			return hBoxBottom;
		}

		public void setToDoModel(ToDo toDoModel) {
			this.toDoModel = toDoModel;
		}

		public void setToDoListModel(ToDoList toDoListModel) {
			this.toDoListModel = toDoListModel;
		}

		public void setListView(ListView<String> listView) {
			this.listView = listView;
		}

		public void setvBox(VBox vBox) {
			this.vBox = vBox;
		}

		public void setBorderPane(BorderPane borderPane) {
			this.borderPane = borderPane;
		}

		public void setSplitPane(SplitPane splitPane) {
			this.splitPane = splitPane;
		}

		public void setStackPane(StackPane stackPane) {
			this.stackPane = stackPane;
		}

		public void setAddToDoDialog(Dialog<ButtonType> addToDoDialog) {
			this.addToDoDialog = addToDoDialog;
		}

		public void setToDoDialogPane(AddToDoDialogPane toDoDialogPane) {
			this.toDoDialogPane = toDoDialogPane;
		}

		public void setBc(BarChart<String, Number> bc) {
			this.bc = bc;
		}

		public void setSerie1(XYChart.Series serie1) {
			this.serie1 = serie1;
		}

		public void setSerie2(XYChart.Series serie2) {
			this.serie2 = serie2;
		}

		public void setFocusDialog(Dialog<ButtonType> focusDialog) {
			this.focusDialog = focusDialog;
		}

		public void setFocusTimerDialog(FocusTimerDialogPane focusTimerDialog) {
			this.focusTimerDialog = focusTimerDialog;
		}

		public void setOpenFocusTimer(Button openFocusTimer) {
			this.openFocusTimer = openFocusTimer;
		}

		public void setHowToDialog(Dialog<ButtonType> howToDialog) {
			this.howToDialog = howToDialog;
		}

		public void setHowToDialogPane(HowToDialogPane howToDialogPane) {
			this.howToDialogPane = howToDialogPane;
		}

		public void setHowTo(Button howTo) {
			this.howTo = howTo;
		}

		public void sethBoxHowTo(HBox hBoxHowTo) {
			this.hBoxHowTo = hBoxHowTo;
		}

		public void setvBoxBottom(VBox vBoxBottom) {
			this.vBoxBottom = vBoxBottom;
		}

		public void sethBoxBottom(HBox hBoxBottom) {
			this.hBoxBottom = hBoxBottom;
		}

}
