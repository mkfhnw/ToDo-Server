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

    // Constructor
    public ServerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.inputReader = this.getInputReader(this.clientSocket);
        this.outputWriter = this.getOutputWriter(this.clientSocket);

        System.out.println("[SERVER] Client connected: "
                + clientSocket.getInetAddress().getHostAddress()
                + ":" + clientSocket.getPort());
    }

    // Private helper methods to keep constructor clean of try/catch-clauses
    private BufferedReader getInputReader(Socket clientSocket) {
        try{
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
    private void sendMessage(String message) {
        try{
            this.outputWriter.write(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Message reading method
//    private Message parseMessage(String message) {
//
//        // Split message by separator
//        // SAMPLE MESSAGE: MessageType|Token|Data
//        String[] messageComponents = message.split("\\|");
//
//        // A token always gets transmitted, even with ping-messages.
//        // If we don't have 3 components therefore, the message is invalid.
//        if(!(messageComponents.length == 3)) {
//            this.sendMessage("Result|False");
//            return null;
//        }
//
//        // Create a clientMessage object
////        Message clientMessage = new Message();
//
//        // Check if thread needs to exit
//
////        return clientMessage;
//
//    }

    @Override
    public void run() {

        try {
            System.out.println("[SERVER-RUNNABLE] Listening for messages...");


            // Build string from sent message
            StringBuilder stringBuilder = new StringBuilder();
            String inputString;
            while((inputString = this.inputReader.readLine()) != null && inputString.length() != 0) {
                stringBuilder.append(inputString);
            }
            String message = stringBuilder.toString();
            System.out.println("[SERVER-RUNNABLE] Received message: " + message);

            // Parse message & answer request
//            ClientMessage clientMessage = this.parseMessage(message);
//            this.outputWriter.write(clientMessage.getResponse().toString());
//            this.outputWriter.flush();
//            System.out.println("[SERVER-RUNNABLE] Sent message: " + clientMessage.getResponse().toString());


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }



}
