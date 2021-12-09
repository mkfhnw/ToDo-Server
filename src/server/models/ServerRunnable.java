package server.models;


import common.HashDigest;
import common.Message;
import common.Token;
import server.services.DatabaseManager;
import server.services.InputValidator;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

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
    private final String falseResult = "Result|false\n";
    private final String trueResult = "Result|true\n";
    private final String trueResultWithoutNewline = "Result|true|";
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

        boolean shouldRun = true;

        while (shouldRun) {

            try {
                System.out.println("[SERVER-RUNNABLE] Listening for messages...");

                // Build string from sent message
                String inputString = this.inputReader.readLine();
                System.out.println("[SERVER-RUNNABLE] Received message: " + inputString);

                // Parse messageString to a "message"
                // Message clientMessage = new Message(this.recipient, this.sender, this.defaultToken, inputString);
                Message clientMessage = new Message(inputString);

                switch (clientMessage.getMessageType()) {

                    // Perform action based on messageType
                    case LOGIN -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to LOGIN...");
                        this.reactToLogin(clientMessage);
                        break;
                    }
                    
                    case LOGOUT -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to LOGOUT...");
                    	this.reactToLogout(clientMessage);

                        System.out.println("[SERVER-RUNNABLE] User logged out, shutting down socket connection.");
                        shouldRun = false;
                        this.clientSocket.close();
                    	break;
                    }
                    
                    case CREATE_LOGIN -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to CREATE_LOGIN...");
                    	this.reactToCreateLogin(clientMessage);
                    	break;
                    }
                    
                    case CREATE_TODO -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to CREATE_TODO...");
                    	this.reactToCreateToDo(clientMessage);
                    	break;
                    }
                    
                    case CHANGE_PASSWORD -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to CHANGE_PASSWORD...");
                    	this.reactToChangePassword(clientMessage);
                    	break;
                    }
                    
                    case GET_TODO -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to GET_TODO...");
                    	this.reactToGetToDo(clientMessage);
                    	break;
                    }
                    
                    case DELETE_TODO -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to DELETE_TODO...");
                    	this.reactToDeleteToDo(clientMessage);
                    	break;
                    }
                    
                    case LIST_TODOS -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to LIST_TODOS...");
                    	this.reactToListToDos(clientMessage);
                    	break;
                    }
                    
                    case PING -> {
                        System.out.println("[SERVER-RUNNABLE] Reacting to PING...");
                    	this.reactToPing(clientMessage);
                    	break;
                    }

                }

                // Make the loop lay back for a bit - only for dev purpose
                Thread.sleep(1000);

            } catch (Exception e) {
                System.out.println("[SERVER-RUNNABLE] EXCEPTION: " + e.getMessage());
                System.out.println("[SERVER-RUNNABLE] EXCEPTION: " + Arrays.toString(e.getStackTrace()));
                if(this.clientSocket.isClosed()) { shouldRun = false; }

                // Socket gets closed
                try {
                    this.clientSocket.close();
                } catch (IOException ex) {
                    System.out.println("[SERVER-RUNNABLE] EXCEPTION: " + ex.getMessage());;
                }
                
            }

        }


    }

    // Reception methods based on the input of the client
	private void reactToLogin(Message clientMessage) {

        // Grab data - username first, then password
        String username = clientMessage.getDataParts().get(0);
        String password = clientMessage.getDataParts().get(1);

        // Validate user input
        InputValidator inputValidator = InputValidator.getInputValidator();
        boolean usernameIsValid = inputValidator.validateUsername(username);
        boolean passwordIsValid = inputValidator.validatePassword(password);
        boolean userDoesAlreadyExist = DatabaseManager.doesDatabaseExist(username.split("@")[0]);

        // Return false if any of the checks above failed
        if(!usernameIsValid || !passwordIsValid || !userDoesAlreadyExist) {
            this.sendMessage(this.falseResult);
            return;
        }

        // See if password matches
        DatabaseManager databaseManager = new DatabaseManager(username.split("@")[0]);
        String storedPassword = databaseManager.getPassword();
        String hashedPasswordInput = new HashDigest(password).getDigest();

        if(storedPassword.equals(hashedPasswordInput)) {

            // If all checks passed - create a token
            Token token = new Token();

            // Assign the token to the user
            token.setUser(username);

            // Add token to the active token list
            this.parent.insertToken(token);

            // Send token back to user
            this.sendMessage(this.trueResultWithoutNewline + token.getTokenString() + "\n");

            return;
        }

        // If we reach this line, return false since the password was not correct
        this.sendMessage(this.falseResult);

    }
	
	private void reactToLogout(Message clientMessage) {

        // Just send trueResult if no token is provided
        if(clientMessage.getToken().equals("0")) { this.sendMessage(this.trueResult); }

        // If a token is provided, delete it from the tokenList on the server parent class
        if(!clientMessage.getToken().equals("0")) {
            this.sendMessage(this.trueResult);
            this.parent.deleteToken(clientMessage.getToken());
        }
		
	}
    
    private void reactToCreateLogin(Message clientMessage) {

        // Grab data - username first, then password
        String username = clientMessage.getDataParts().get(0);
        String password = clientMessage.getDataParts().get(1);

        // Validate user input
        InputValidator inputValidator = InputValidator.getInputValidator();
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

        // Parse out token
        String tokenString = clientMessage.getToken();
        Token token = this.parent.getToken(tokenString);

        // If token is invalid, send negative response
        InputValidator inputValidator = InputValidator.getInputValidator();
        if(!inputValidator.isTokenStillAlive(token)) {
            this.sendMessage(this.falseResult);
            return;
        }

        // If token is valid, go ahead
        if(inputValidator.isTokenStillAlive(token)) {

            // Parse username
            String username = token.getUser();

            // Create database manager
            DatabaseManager databaseManager = new DatabaseManager(username.split("@")[0]);

            // Parse out item details to pass to the database manager
            String title = clientMessage.getDataParts().get(0);
            String priority = clientMessage.getDataParts().get(1);
            String description = null;
            if(clientMessage.getDataParts().size() >= 3) { description = clientMessage.getDataParts().get(2); }
            String dueDate = null;
            if(clientMessage.getDataParts().size() == 4) { dueDate = clientMessage.getDataParts().get(3); }

            // Choose fitting constructor based on how many inputs we have
            if(description == null && dueDate == null) {
                // databaseManager.createItem()
            }

            if(description == null && dueDate != null) {
                // databaseManager.createItem();
            }

            if(description != null && dueDate == null) {
                // databaseManager.createItem();
            }

            if(description != null && dueDate != null) {
                int itemID = databaseManager.createItem(title, priority, description, dueDate);
                this.sendMessage(this.trueResultWithoutNewline + itemID + "\n");
            }
        }
    	
		
	}
    
    private void reactToChangePassword(Message clientMessage) {
    	
    	// Parse out token
        String tokenString = clientMessage.getToken();
        Token token = this.parent.getToken(tokenString);
        
     // If token is invalid, send negative response
        InputValidator inputValidator = InputValidator.getInputValidator();
        if(!inputValidator.isTokenStillAlive(token)) {
            this.sendMessage(this.falseResult);
            return;
        }
        
     // If token is valid, go ahead
        if(inputValidator.isTokenStillAlive(token)) {

            // Parse username
            String username = token.getUser();

        }
		
	}
    
    private void reactToGetToDo(Message clientMessage) {
    	
    	// Parse out token
        String tokenString = clientMessage.getToken();
        Token token = this.parent.getToken(tokenString);
		
     // If token is invalid, send negative response
        InputValidator inputValidator = InputValidator.getInputValidator();
        if(!inputValidator.isTokenStillAlive(token)) {
            this.sendMessage(this.falseResult);
            return;
        }
        
     // If token is valid, go ahead
        if(inputValidator.isTokenStillAlive(token)) {

            // Parse username
            String username = token.getUser();

        }
		
	
	}
    
    private void reactToDeleteToDo(Message clientMessage) {
    	
    	// Parse out token
        String tokenString = clientMessage.getToken();
        Token token = this.parent.getToken(tokenString);
        
        // If token is invalid, send negative response
        InputValidator inputValidator = InputValidator.getInputValidator();
        if(!inputValidator.isTokenStillAlive(token)) {
            this.sendMessage(this.falseResult);
            return;
        }
        
        // If token is valid, go ahead
        if(inputValidator.isTokenStillAlive(token)) {

            // Parse username
            String username = token.getUser();
            
            // Create database manager
            DatabaseManager databaseManager = new DatabaseManager(username.split("@")[0]);
            
            //Parse out item ID to delete
            String ID = clientMessage.getDataParts().get(0);
            databaseManager.deleteItem(ID);
            

        }
		
	}
    
    private void reactToListToDos(Message clientMessage) {
    	
    	// Parse out token
        String tokenString = clientMessage.getToken();
        Token token = this.parent.getToken(tokenString);
        
     // If token is invalid, send negative response
        InputValidator inputValidator = InputValidator.getInputValidator();
        if(!inputValidator.isTokenStillAlive(token)) {
            this.sendMessage(this.falseResult);
            return;
        }
        
     // If token is valid, go ahead
        if(inputValidator.isTokenStillAlive(token)) {

            // Parse username
            String username = token.getUser();

        }
		
	}
    
    private void reactToPing(Message clientMessage) {
    	
    	// Parse out token
        String tokenString = clientMessage.getToken();
        Token token = this.parent.getToken(tokenString);
        
     // If token is invalid, send negative response
        InputValidator inputValidator = InputValidator.getInputValidator();
        if(!inputValidator.isTokenStillAlive(token)) {
            this.sendMessage(this.falseResult);
            return;
        }
        
     // If token is valid, go ahead
        if(inputValidator.isTokenStillAlive(token)) {

            // Parse username
            String username = token.getUser();

        }
		
	}


}