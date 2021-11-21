package client;

import server.messageProtocol.ClientMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ToDoClient {

    // Fields
    private final int PORT = 9999;
    private Socket clientSocket;
    private BufferedReader inputReader;
    private PrintWriter outputWriter;
    private String token;
    private final String defaultToken = "3963c9cae5c5aeaa71f287190774db4d354287c7973e969e9d6c5722c1037a33";

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

//        // Creates new Socket to the server and prints its output
//        try(Socket socket = new Socket("localhost", PORT)) {
//
//            // Get socket input & output
//            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream());
//
//
//        } catch (UnknownHostException e) {
//            System.out.println(e.getMessage());
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }

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
    public void sendMessage(String command) {

        // Create cient message based on input
        ClientMessage message = new ClientMessage(command, this.defaultToken);

        // Send message
        this.outputWriter.print(message.getMessageString());
        this.outputWriter.flush();
        System.out.println("[CLIENT] Sent message: " + message.getMessageString());
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
            String message = stringBuilder.toString();
            System.out.println("[CLIENT] Received message: " + message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
