package server.models;


import common.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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

    // Constructor
    public ServerRunnable(Socket clientSocket) {

        this.clientSocket = clientSocket;
        this.inputReader = this.getInputReader(this.clientSocket);
        this.outputWriter = this.getOutputWriter(this.clientSocket);

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

                // Parse messageString to a "message"
                Message clientMessage = new Message(this.recipient, this.sender, this.defaultToken, inputString);

                switch (clientMessage.getMessageType()) {

                    // Perform action based on messageType
                    case LOGIN -> {
                        this.reactToLogin();
                        break;
                    }
                    
                    case LOGOUT -> {
                    	this.reactToLogout();

                        this.clientSocket.close();
                    	break;
                    }
                    
                    case CREATE_LOGIN -> {
                    	this.reactToCreateLogin(clientMessage);
                    	break;
                    }
                    
                    case CREATE_TODO -> {
                    	this.reactToCreateToDo();
                    	break;
                    }
                    
                    case CHANGE_PASSWORD -> {
                    	this.reactToChangePassword();
                    	break;
                    }
                    
                    case GET_TODO -> {
                    	this.reactToGetToDo();
                    	break;
                    }
                    
                    case DELETE_TODO -> {
                    	this.reactToDeleteToDo();
                    	break;
                    }
                    
                    case LIST_TODOS -> {
                    	this.reactToListToDos();
                    	break;
                    }
                    
                    case PING -> {
                    	this.reactToPing();
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
	private void reactToLogin() {

	}
	
	private void reactToLogout() {
		
	}
    
    private void reactToCreateLogin(Message clientMessage) {

        // Grab data - username first, then password
        String username = clientMessage.getDataParts().get(0);
        String password = clientMessage.getDataParts().get(1);

        // Validate user input
        boolean usernameIsValid = false;
        boolean passwordIsValid = false;

        // Email validation - checks if email contains an @, and does neither start nor end with a "." .
        String[] usernameParts = username.split("@");
        if(usernameParts.length != 0 && !usernameParts[0].startsWith(".") && !usernameParts[1].endsWith(".")) {
            usernameIsValid = true;
        }

        // Password validation


        // TODO: Check if username already exists by checking if a .db file already exists with this username

        // Create new database for the user

        // Send response

	}
    
    private void reactToCreateToDo() {
		
	}
    
    private void reactToChangePassword() {
		
	}
    
    private void reactToGetToDo() {
		
	}
    
    private void reactToDeleteToDo() {
		
	}
    
    private void reactToListToDos() {
		
	}
    
    private void reactToPing() {
		
	}


}