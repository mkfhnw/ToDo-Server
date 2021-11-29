package main;

import client.ToDoClient;
import common.MessageType;

import java.util.ArrayList;

public class ClientRunner {

    public static void main(String[] args) {

        // Sleep to wait for the server to spin up
        try{ Thread.sleep(5000); } catch (Exception e) { System.out.println(e.getMessage()); }

        // Create client
        ToDoClient toDoClient = new ToDoClient();

        // Send PING message
        ArrayList<String> data = new ArrayList<>();
        
        
        toDoClient.sendMessage("PING", data);
//        See MessageType class for command spelling
       // data.add("Username");
       // data.add("Password");
        toDoClient.sendMessage("LOGOUT", data);
     // toDoClient.sendMessage("LIST_TODOS", data);

        try{ Thread.sleep(5000); } catch (Exception e) { System.out.println(e.getMessage()); }

        // Read incoming response
        toDoClient.parseResponse();

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
    	// toDoClient.sendMessage("LOGOUT", data);
    	// Token
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

