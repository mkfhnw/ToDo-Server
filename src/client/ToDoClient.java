package client;

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

    // Constructor
    public ToDoClient() {

        try {
            this.clientSocket = new Socket("localhost", this.PORT);
            this.inputReader = this.getInputReader(this.clientSocket);
            this.outputWriter = this.getOutputWriter(this.clientSocket);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Creates new Socket to the server and prints its output
        try(Socket socket = new Socket("localhost", PORT)) {

            // Get socket input & output
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream());


        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
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
    public void sendMessage(String command) {

        // Send command based on input
        switch(command) {

            case "PING" -> {
                System.out.println("Write code to send a PING to the server");
                System.out.println("Watch the message protocol");
                break;
            }

        }

    }

    // Message reading method
    public void parseResponse() {

        // Parse the server response

    }
}
