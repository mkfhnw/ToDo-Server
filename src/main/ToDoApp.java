package main;

import client.controller.ToDoController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import client.view.LoginView;
import client.view.ToDoView;
import client.model.ToDo;
import client.model.ToDoList;

import java.util.ArrayList;

public class ToDoApp extends Application {

	// Fields
	private ToDo todoModel;
	private ToDoList toDoList;
	private ToDoView toDoView;
	private ToDoController toDoController;
	
	private LoginView loginView;

	// Starts the JavaFX application
	public static void main(String[] args) {
		launch(args);
	}
	
	// Shows a GUI for the ToDo-App
	
	public void start(Stage stage) {

		// 1. Instantiates the root todoView
		this.todoModel = new ToDo();
		this.toDoList = new ToDoList();
		this.toDoView = new ToDoView(todoModel, toDoList);
		this.toDoController = new ToDoController(this.toDoView, this.todoModel, toDoList);
		
		// 1.1 Instance for LoginView
		this.loginView = new LoginView();
		
		// 2. Passes the root to the scene
		Scene scene2 = new Scene(toDoView);
		
		// 2.1 Scene for LoginView
		Scene scene1 = new Scene(loginView);
		
		// open App
		this.loginView.getSignInButton().setOnAction(e -> stage.setScene(scene2));
		
		// 3. Shows scene in a window (object stage)
		stage.setScene(scene1);
		stage.setTitle("ToDo-App");
		stage.show();
		
		// Adds an icon to the window
		Image doneImage = new Image("/common/resources/doneIcon.png");
		stage.getIcons().add(doneImage);
		
	}

	@Override
	public void stop() {

		// Kill all items that are marked as garbage
		ArrayList<ToDo> garbageList = this.toDoController.getToDoList().getGarbageList();


	}

}
