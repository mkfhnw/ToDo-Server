package client;

import common.Message;
import common.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ToDoClient {

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
    public ToDoClient() {

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

     // Will be used to know which kind of MessageType is needed
        public void sendMessageType(MessageType messageType) {
        	
        	switch (messageType) {
            
            case LOGIN:
            	login();
            	break;
            case LOGOUT:
            	logout();
            	break;
            case CREATE_LOGIN:
            	createLogin();
            	break;
            case CREATE_TODO:
            	createToDo();
            	break;
            case CHANGE_PASSWORD:
            	changePassword();
            	break;
            case GET_TODO:
            	getToDo();
            	break;
            case DELETE_TODO:
            	deleteToDo();
            	break;
            case LIST_TODOS:
            	listToDos();
            	break;
            case PING:
            	ping();
            }
            
        }
        
        public void login() {
        	ToDoClient toDoClient = new ToDoClient();
            ArrayList<String> data = new ArrayList<>();
        	
        	toDoClient.sendMessage("LOGIN", data);
        	data.add("Username");
        	data.add("Password");
        	
        	// Token
        }
        
        public void logout() {
        	
        	// Token must be invalid
        	
        	// DELETE toDoClient.sendMessage("LOGOUT", data);
        }
        
        
        public void createLogin() {
        	// Username
        	// Password
        	// (Lastname, Surname)
        	// (Password validation)
        	// (Token)
        	
        	// toDoClient.sendMessage("CREATE_LOGIN", data);
        }
        
        public void createToDo() {
        	// title (+ validation)
        	// description (+ validation)
        	// duedate (with and without)
        	// Priority
        	// Token-Validation
        	
        	// toDoClient.sendMessage("CREATE_TODO", data);
        }

        public void changePassword() {
        	// password
        	// (password validation)
        	// (Token)
        	// Validation (Password length + Token valid)
        	
        	// toDoClient.sendMessage("CHANGE_PASSWORD", data);
        }
        
        public void getToDo() {
        	// ID
        	// Token-Validation
        	
        	// toDoClient.sendMessage("GET_TODO", data);
        }
        
        public void deleteToDo() {
        	// getToDo();
        	// clear(); / remove();
        	// Token-Validation
        	
        	// toDoClient.sendMessage("DELETE_TODO", data);
        }
        
        public void listToDos() {
        	// ArrayList data
        	// Token-Validation
        	
        	// toDoClient.sendMessage("LIST_TODOS", data);
        }
        
        public void ping() {
        	// Token
        	
        	// toDoClient.sendMessage("PING", data);
        }
    
}
