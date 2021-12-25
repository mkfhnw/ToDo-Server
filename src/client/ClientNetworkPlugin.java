package client;

import client.model.ToDo;
import common.Message;
import common.MessageType;
import server.models.ServerRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;

import client.view.LoginView;
import client.view.RegistrationDialogPane;

public class ClientNetworkPlugin {

    // Fields
    private final int PORT = 50002;
    private Socket clientSocket;
    private BufferedReader inputReader;
    private PrintWriter outputWriter;
    private String token;
    private final String sender = "Client";
    private final String recipient = "Server";
    private ServerRunnable serverRunnable;

    // Constructor
    public ClientNetworkPlugin() {

		// This try-catch / thread.sleep is only because my laptop isn't able to spin up the server fast enough..
//		try {
//			Thread.sleep(3000);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}



        System.out.println("[CLIENT] New ToDoClient created.");
        

    }

	// Public method to connect to server
	public void connect(String IP, int port) {
		try {
			this.clientSocket = new Socket(IP, port);
			this.inputReader = this.getInputReader(this.clientSocket);
			this.outputWriter = this.getOutputWriter(this.clientSocket);
			System.out.println("[CLIENT] ToDo-Client connected to server");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
    public void sendMessage(String command, ArrayList<String> data, String token) {

        // Create cient message based on input
        Message clientMessage = new Message(this.sender, this.recipient, this.token, command, data);

        // Send message
        this.outputWriter.write(clientMessage.getMessageString());
        this.outputWriter.flush();
        System.out.println("[CLIENT] Sent message: " + clientMessage.getMessageString());
    }
    
 // Message sending method for login and ping
    public void sendMessage(String command, ArrayList<String> data) {

        // Create cient message based on input
        Message clientMessage = new Message(this.sender, this.recipient, command, data);

        // Send message
        this.outputWriter.write(clientMessage.getMessageString());
        this.outputWriter.flush();
        System.out.println("[CLIENT] Sent message: " + clientMessage.getMessageString());
    }

    // Message reading method
    public Message parseResponse() {

        try {
            // Parse the server response
            String messageString = this.inputReader.readLine();
            System.out.println("[CLIENT] Received message: " + messageString);

            // Create Message
            Message message = new Message(messageString);
    		return message;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
		

    }
        
        public boolean login(String emailLogin, String passwordLogin) {
        
        	boolean result = false;
        	
        	try {
                ArrayList<String> loginData = new ArrayList<>();
        	    loginData.add(emailLogin);
        	    loginData.add(passwordLogin);
        	
        	    sendMessage("LOGIN", loginData);

                // Receive server response case
        	    Message responseLogin = this.parseResponse();

                // Parse response result
                result = Boolean.parseBoolean(responseLogin.getMessageParts().get(1));



        	    if (result) {
            	    // set token
            	    this.token = responseLogin.getToken();
        	    } else {
        		    result = false;
        	    }
        	
            } catch (Exception e) {
                System.out.println("[NETWORK-PLUGIN] Exception: " + e.getMessage());
                result = false;
            }
        	
        	return result;

        }
        
        public boolean logout() {
        	
        	boolean result = false;
        	
        	try {
        	
	        	ArrayList<String> logoutData = new ArrayList<>();        	
	        	
	        	sendMessage("LOGOUT", logoutData, this.token);
	        	
	        	// Receive server response case
        	    Message responseLogin = this.parseResponse();

                // Parse response result
                result = Boolean.parseBoolean(responseLogin.getMessageParts().get(1));
        	
        	} catch (Exception e){
        		System.out.println("[NETWORK-PLUGIN] Exception: " + e.getMessage());
                result = false;
        	}
        	
        	return result;
        }
        
        
        public boolean createLogin(String emailCreateLogin, String passwordCreateLogin) {
        	 
        	boolean result = false;
        	
        	
        	try { 
        		ArrayList<String> createLoginData = new ArrayList<>();
        		createLoginData.add(emailCreateLogin);
        		createLoginData.add(passwordCreateLogin);
        	
        		sendMessage("CREATE_LOGIN", createLoginData);
        	
        		// Receive server response case
        		Message responseLogin = this.parseResponse();

        		// Parse response result
        		result = Boolean.parseBoolean(responseLogin.getMessageParts().get(1));

    	
        	} catch (Exception e) {
        		System.out.println("[NETWORK-PLUGIN] Exception: " + e.getMessage());
        		result = false;
        }
        	
        	
        	// Fails if name already taken, or invalid --> SERVER
        	// After creating an account, you still have to login --> CONTROLLER
        	
        	return result;
        }
        
        public void createToDo(ToDo toDo) {
        	
        	boolean result = false;
        	int toDoID;
        	
        	try {
	            ArrayList<String> createToDoData = new ArrayList<>();
	            createToDoData.add(toDo.getTitle());
	            createToDoData.add(toDo.getPriority().toString());
	            if (toDo.getMessage() != null && !toDo.getMessage().equals("") && !toDo.getMessage().equals(" ")) {
					createToDoData.add(toDo.getMessage());
				}
	            if (toDo.getDueDate() != null) { createToDoData.add(toDo.getDueDate().toString()); }
				if (toDo.getCategory() != null) { createToDoData.add(toDo.getCategory()); }
	            
	            sendMessage("CREATE_TODO", createToDoData, this.token);
	            
	            // Receive server response case
        		Message responseLogin = this.parseResponse();
        		
        		// Parse response result
        		result = Boolean.parseBoolean(responseLogin.getMessageParts().get(1));
        		
        		if (result) {
        			toDoID = Integer.parseInt(responseLogin.getMessageParts().get(2));
        		}


        	} catch (Exception e) {
        		System.out.println("[NETWORK-PLUGIN] Exception: " + e.getMessage());
        		
        	}
        	
            
		}

        public boolean changePassword(String newPassword) {
        	
        	boolean result = false;
        	
        	try {
        		ArrayList<String> changePasswordData = new ArrayList<>();
        		changePasswordData.add(newPassword);
        	
        		// change password --> CONTROLLER
	        	// Token valid (SERVER))
	        	 
	        	sendMessage("CHANGE_PASSWORD", changePasswordData, this.token);
	        
	        	// Receive server response case
	    		Message responseLogin = this.parseResponse();
	
	    		// Parse response result
	    		result = Boolean.parseBoolean(responseLogin.getMessageParts().get(1));

	
        	} catch (Exception e) {
	    		System.out.println("[NETWORK-PLUGIN] Exception: " + e.getMessage());
	    		result = false;
    }
        	return result;
        }
        
        public ArrayList<String> getToDo(int ID) {
        	
        	
        	try {
        		ArrayList<String> toDoData = new ArrayList<>();
        		toDoData.add(Integer.toString(ID));
            	
            	
            	sendMessage("GET_TODO", toDoData, this.token);
            	
            	// Receive server response case
        		Message responseLogin = this.parseResponse();
        		
        		ArrayList<String> itemData = responseLogin.getDataParts();
        		return itemData;
        		
        	} catch (Exception e) {
        		System.out.println("[NETWORK-PLUGIN] Exception: " + e.getMessage());
        		return null;
        	}
        	
        	

        }
        
        public boolean deleteToDo(int ID) {
        	
        	boolean result = false;
        	
        	try {
        	
	        	ArrayList<String> deletedToDoData = new ArrayList<>();
	        	deletedToDoData.add(Integer.toString(ID));
	
	        	// toDoController.deleteToDo(); --> SERVER
	        	
	        	// Token-Validation --> SERVER
	        	
	        	sendMessage("DELETE_TODO", deletedToDoData, this.token);
	        	
	        	// Receive server response case
	    		Message responseLogin = this.parseResponse();
	
	    		// Parse response result
	    		result = Boolean.parseBoolean(responseLogin.getMessageParts().get(1));
	    		
	        	
        	} catch (Exception e) {
        		System.out.println("[NETWORK-PLUGIN] Exception: " + e.getMessage());
	    		result = false;
        	}
        	
        	return result;
        	
        }
        
        public ArrayList<String> listToDos() {
        	
        	try {
        		
        		ArrayList<String> listToDos = new ArrayList<>();
            	
        	    sendMessage("LIST_TODOS", listToDos, this.token);
        	    
        	 // Receive server response case
	    		Message responseLogin = this.parseResponse();
	    		
	    		ArrayList<String> resultList = responseLogin.getDataParts();
	    		
	    		return resultList;
	    		
        		
        	} catch (Exception e) {
        		System.out.println("[NETWORK-PLUGIN] Exception: " + e.getMessage());
        		return null;
        	}
        	
        
        	

        }
        
        public boolean ping() {
        	
        	boolean result = false;
        	
        	try {
	        	ArrayList<String> pingData = new ArrayList<>();
	        	
	        	/*
	        	 * Ping can be sent with and without token, 
	        	 * therefore we check if token is not "null"
	        	 */
	        	if (token != null) {
	        		sendMessage("PING", pingData, this.token);
	        	} else {
	        		sendMessage("PING", pingData);
	        	}
	        	
	        	// Receive server response case
	    		Message responseLogin = this.parseResponse();
	
	    		// Parse response result
	    		result = Boolean.parseBoolean(responseLogin.getMessageParts().get(1));
	        	
        	} catch (Exception e) {
        		System.out.println("[NETWORK-PLUGIN] Exception: " + e.getMessage());
	    		result = false;
        	}
        	
        	return result;
        	

        }

		public String getToken() {
			return token;
		}

		public int getPORT() {
			return PORT;
		}

		public Socket getClientSocket() {
			return clientSocket;
		}

		public BufferedReader getInputReader() {
			return inputReader;
		}

		public PrintWriter getOutputWriter() {
			return outputWriter;
		}

		public String getSender() {
			return sender;
		}

		public String getRecipient() {
			return recipient;
		}

		public ServerRunnable getServerRunnable() {
			return serverRunnable;
		}

		public void setClientSocket(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		public void setInputReader(BufferedReader inputReader) {
			this.inputReader = inputReader;
		}

		public void setOutputWriter(PrintWriter outputWriter) {
			this.outputWriter = outputWriter;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public void setServerRunnable(ServerRunnable serverRunnable) {
			this.serverRunnable = serverRunnable;
		}
    
        
}
