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
        try{
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
        try{
            this.outputWriter.write(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
                    // this.reactToLogin()
                    break;
                }

            }

         // Socket gets closed
           this.clientSocket.close();
           // System.out.println(clientSocket.isClosed());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    	}

        
    }



}
