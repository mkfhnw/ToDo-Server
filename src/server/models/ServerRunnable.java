package server.models;


import common.Message;
import common.Token;
import server.services.DatabaseManager;
import server.services.InputValidator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import client.model.ToDo;

/* The ClientRunnable class
 * The ClientRunnable is a class that implements the Runnable interface.
 * An instance of this class gets created each time a client connects to the serverSocket, running on the main
 * thread of the server. The purpose of this class therefore is to manage each incoming client connection to the server
 * as a separate thread. It represents the connection between the server and the client.
 */
public class ServerRunnable implements Runnable {

    // Fields
    private Socket clientSocket;
    private BufferedReader inputReader;
    private PrintWriter outputWriter;
    private final String defaultToken = "3963c9cae5c5aeaa71f287190774db4d354287c7973e969e9d6c5722c1037a33";
    private final String sender = "Server";
    private final String recipient = "Client";
    private final String falseResult = "Result|false";
    private final String trueResult = "Result|true";
    private final ToDoServer parent;

    // Constructor
    public ServerRunnable(Socket clientSocket, ToDoServer parent) {

        this.clientSocket = clientSocket;
        this.inputReader = this.getInputReader(this.clientSocket);
        this.outputWriter = this.getOutputWriter(this.clientSocket);
        this.parent = parent;

        System.out.println("[SERVER-RUNNABLE] Client connected: "
                + clientSocket.getInetAddress().getHostAddress()
                + ":" + clientSocket.getPort());
    }

    // Private helper methods to keep constructor clean of try/catch-clauses
    private BufferedReader getInputReader(Socket clientSocket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            return bufferedReader;
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

    private void sendMessage(String message) {
        try {
            this.outputWriter.write(message);
            this.outputWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /* The run method
     * On a new connection, the serverScket creates a new thread using this runnable. As soon as the thread gets
     * started, this run method gets called. Inside this run method, we listen for calls from the client until the
     * client sends the LOGOUT-message type. On reception of the LOGOUT-message, the socket gets closed and the runnable
     * comes to an end - which means the above thread closes as well.
     */
    @Override
    public void run() {

        while (true) {

            try {
                System.out.println("[SERVER-RUNNABLE] Listening for messages...");

                // Build string from sent message
                String inputString = this.inputReader.readLine();
                System.out.println("[SERVER-RUNNABLE] Received message: " + inputString);
                Thread.sleep(3000);

                // Parse messageString to a "message"
                // Message clientMessage = new Message(this.recipient, this.sender, this.defaultToken, inputString);
                Message clientMessage = new Message(inputString);

                switch (clientMessage.getMessageType()) {

                    // Perform action based on messageType
                    case LOGIN -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to LOGIN...");
                        Thread.sleep(5000);
                        this.reactToLogin(clientMessage);
                        break;
                    }
                    
                    case LOGOUT -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to LOGOUT...");
                        Thread.sleep(5000);
                    	this.reactToLogout(clientMessage);

                        this.clientSocket.close();
                    	break;
                    }
                    
                    case CREATE_LOGIN -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to CREATE_LOGIN...");
                        Thread.sleep(5000);
                    	this.reactToCreateLogin(clientMessage);
                    	break;
                    }
                    
                    case CREATE_TODO -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to CREATE_TODO...");
                        Thread.sleep(5000);
                    	this.reactToCreateToDo(clientMessage);
                    	break;
                    }
                    
                    case CHANGE_PASSWORD -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to CHANGE_PASSWORD...");
                        Thread.sleep(5000);
                    	this.reactToChangePassword(clientMessage);
                    	break;
                    }
                    
                    case GET_TODO -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to GET_TODO...");
                        Thread.sleep(5000);
                    	this.reactToGetToDo(clientMessage);
                    	break;
                    }
                    
                    case DELETE_TODO -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to DELETE_TODO...");
                        Thread.sleep(5000);
                    	this.reactToDeleteToDo(clientMessage);
                    	break;
                    }
                    
                    case LIST_TODOS -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to LIST_TODOS...");
                        Thread.sleep(5000);
                    	this.reactToListToDos(clientMessage);
                    	break;
                    }
                    
                    case PING -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to PING...");
                        Thread.sleep(5000);
                    	this.reactToPing(clientMessage);
                    	break;
                    }

                }

                // Socket gets closed
                this.clientSocket.close();

            } catch (Exception e) {
                System.out.println("[SERVER-RUNNABLE] EXCEPTION: " + e.getMessage());
            }

        }


    }

    // Reception methods based on the input of the client
	private void reactToLogin(Message clientMessage) {

        // Grab data - username first, then password
        String username = clientMessage.getDataParts().get(0);
        String password = clientMessage.getDataParts().get(1);

        // Validate user input
        InputValidator inputValidator = new InputValidator();
        boolean usernameIsValid = inputValidator.validateUsername(username);
        boolean passwordIsValid = inputValidator.validatePassword(password);
        boolean userDoesAlreadyExist = DatabaseManager.doesDatabaseExist(username.split("@")[0]);

        // Return false if any of the checks above failed
        if(!usernameIsValid || !passwordIsValid || !userDoesAlreadyExist) {
            this.sendMessage(this.falseResult);
            return;
        }

        // If all checks passed - create a token
        Token token = new Token();

        // Assign the token to the user

        // Add token to the active token list
        this.parent.insertToken(token);

    }
	
	private void reactToLogout(Message clientMessage) {
		
	}
    
    private void reactToCreateLogin(Message clientMessage) {

        // Grab data - username first, then password
        String username = clientMessage.getDataParts().get(0);
        String password = clientMessage.getDataParts().get(1);

        // Validate user input
        InputValidator inputValidator = new InputValidator();
        boolean usernameIsValid = inputValidator.validateUsername(username);
        boolean passwordIsValid = inputValidator.validatePassword(password);
        boolean userDoesAlreadyExist = DatabaseManager.doesDatabaseExist(username.split("@")[0]);

        // Create new database and store login credentials for the user if input is valid
        if(usernameIsValid && passwordIsValid && !userDoesAlreadyExist) {
            DatabaseManager databaseManager = new DatabaseManager(username.split("@")[0]);
            databaseManager.initializeDatabase();
            databaseManager.storeLoginCredentials(username, password);
            this.sendMessage(this.trueResult);
        }

        // Send response
        if(!usernameIsValid || !passwordIsValid || userDoesAlreadyExist) {
            this.sendMessage(this.falseResult);
        }
        

	}
    
    private void reactToCreateToDo(Message clientMessage) {
    	
    	//String title = clientMessage.getDataParts().get(0);
    	//String message = clientMessage.getDataParts().get(1);
    	//String dueDateAsString = clientMessage.getDataParts().get(2);
    	
    	//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    	//LocalDate dueDate = LocalDate.parse(dueDateAsString, dtf);
    	
    	//ToDo toDo = new ToDo(title, message, dueDate);
    	
		
	}
    
    private void reactToChangePassword(Message clientMessage) {
		
	}
    
    private void reactToGetToDo(Message clientMessage) {
		
	}
    
    private void reactToDeleteToDo(Message clientMessage) {
		
	}
    
    private void reactToListToDos(Message clientMessage) {
		
	}
    
    private void reactToPing(Message clientMessage) {
		
	}


}