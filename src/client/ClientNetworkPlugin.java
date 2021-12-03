package client;

import client.model.ToDo;
import common.Message;
import common.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;

import client.view.LoginView;
import client.view.RegistrationView;

public class ClientNetworkPlugin {

    // Fields
    private final int PORT = 50001;
    private Socket clientSocket;
    private BufferedReader inputReader;
    private PrintWriter outputWriter;
    private String token;
    private final String defaultToken = "3963c9cae5c5aeaa71f287190774db4d354287c7973e969e9d6c5722c1037a33";
    private final String sender = "Client";
    private final String recipient = "Server";

    // Constructor
    public ClientNetworkPlugin() {

        try {
            this.clientSocket = new Socket("localhost", this.PORT);
            this.inputReader = this.getInputReader(this.clientSocket);
            this.outputWriter = this.getOutputWriter(this.clientSocket);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("[CLIENT] New ToDoClient created.");
        

    }

    // Private helper methods to keep constructor clean of try/catch-clauses
    private BufferedReader getInputReader(Socket clientSocket) {
        try {
            return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    private PrintWriter getOutputWriter(Socket clientSocket) {
        try {
            return new PrintWriter(clientSocket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Message sending method
    public void sendMessage(String command, ArrayList<String> data) {

        // Create cient message based on input
        Message clientMessage = new Message(this.sender, this.recipient, this.defaultToken, command, data);

        // Send message
        this.outputWriter.write(clientMessage.getMessageString());
        this.outputWriter.flush();
        System.out.println("[CLIENT] Sent message: " + clientMessage.getMessageString());
    }

    // Message reading method
    public void parseResponse() {

        try {
            // Parse the server response
            StringBuilder stringBuilder = new StringBuilder();
            String inputString;
            while((inputString = this.inputReader.readLine()) != null && inputString.length() != 0) {
                stringBuilder.append(inputString);
                System.out.println("inputString = " + inputString);
            }
            String messageString = stringBuilder.toString();
            System.out.println("[CLIENT] Received message: " + messageString);

            // Create Message
//            Message message = new Message(messageString);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        public void login() {
        	LoginView loginView = new LoginView();
            
        	
        	// sendMessage("LOGIN", toDoController.get());
        	
        	// In Controller
        	String emailLogin = loginView.getUserField().getText();
        	
        	String passwordLogin = loginView.getPasswordField().getText();
        	
        	
        	// Fails if name/password do not match --> Server

        }
        
        public void logout() {
        	
        	// No Data must be sent 
        	// Never fails; token becomes invalid
        	
        }
        
        
        public void createLogin() {
        	RegistrationView registrationView = new RegistrationView();
        	
        	// In Controller
        	// sendMessage("CREATE_LOGIN", toDoController.get());
        	String emailCreateLogin = registrationView.getEmailField().getText();
        	
        	String passwordCreateLogin = registrationView.getPasswordField().getText();
        	
        	// Fails if name already taken, or invalid --> SERVER
        	// After creating an account, you still have to login --> CONTROLLER
        	// (Password validation) -- if we want --> CONTROLLER
        	
        	
        }
        
        public void createToDo(ToDo toDo) {
        	
//        	sendMessage("CREATE_TODO", toDoController.updateToDo());

            ArrayList<String> data = new ArrayList<>();
            data.add(toDo.getTitle());
            data.add(toDo.getMessage());
            data.add(toDo.getDueDate().toString());
        	
        	// title (+ validation) --> Controller
        	// description (+ validation) --> Controller 
        	// duedate (with and without) --> controller
        	// Priority --> Controller, AddToDoDialogPane, ToDo
        	// Token-Validation --> SERVER
        	
        /*
         * For Controller, if not existing yet: LocalDate today = LocalDate.now();
         * 
        	if (toDoTitle.length() >= 3 && toDoTitle.length() <= 20 
        		&& !toDoDialogPane.getDatePicker().isBefore(today) 
        		&& toDoDialogPane.getMessageTextArea().length() <= 255) {
         */
        
        
        	 this.sendMessage("CREATE_TODO", data);
		}

        public void changePassword() {
        	// password --> CONTROLLER
        	// (password validation) --> CONTROLLER
        	// Validation (Password length (CONTROLLER) + Token valid (SERVER))
        	
        	// toDoClient.sendMessage("CHANGE_PASSWORD", data);
        }
        
        public void getToDo() {
        	// int ID 
        	// toDoList for each; --> SERVER
        	// Token-Validation
        	
        	// toDoClient.sendMessage("GET_TODO", ID);
        }
        
        public void deleteToDo() {
        	// ID
        	// toDoController.deleteToDo(); --> SERVER
        	
        	// Token-Validation
        	
        	// toDoClient.sendMessage("DELETE_TODO", toDo);
        }
        
        public void listToDos() {

        	// Token
        	
        	// toDoClient.sendMessage("LIST_TODOS", data);
        }
        
        public void ping() {
        	// With and without Token
        	
        	// toDoClient.sendMessage("PING", data);
        }
    
        
}
