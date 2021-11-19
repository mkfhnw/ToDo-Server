package server.models;

import server.messageProtocol.ClientMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        this.inputReader = this.getInputReader(clientSocket);
        this.outputWriter = this.getOutputWriter(clientSocket);

        System.out.println("[CLIENT CONNECTED] "
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

    // Message reading method
    private void parseMessage(String message) {

        // Split message by separator
        // SAMPLE MESSAGE: MessageType|Token|Data
        String[] messageComponents = message.split("\\|");

        // A token always gets transmitted, even with ping-messages.
        // If we don't have 3 components therefore, the message is invalid.
        if(!(messageComponents.length == 3)) {
            this.outputWriter.println("Result|False");
            return;
        }

        // Create a clientMessage object. If the received data is somewhat invalid, the
        ClientMessage clientMessage = new ClientMessage(messageComponents);

        // Send response
        // ... clientMessage.getResponse() -> Client


    }

    @Override
    public void run() {

        try {

            // Read messages and react accordingly
            String message = "";
            while (message != null) {
                message = this.inputReader.readLine();
                this.parseMessage(message);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }



}
