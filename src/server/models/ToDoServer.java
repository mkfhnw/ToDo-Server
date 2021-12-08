package server.models;

import common.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoServer {

    // Fields
    private ServerSocket serverSocket;
    private final int PORT = 50002;
    private boolean isActive;
    private ArrayList<Thread> clientThreads;
    private volatile ArrayList<Token> activeTokens;

    // Constructor
    public ToDoServer() {
        this.createServerSocket();
        this.isActive = false;
        this.clientThreads = new ArrayList<>();
        this.activeTokens = new ArrayList<>();
        System.out.println("[SERVER] New ToDoServer created.");
    }

    // Run method
    public void listen() {

        // Listens for new incoming connections as long as isActive is true
        try {
            Socket clientSocket = this.serverSocket.accept();

            // Create new thread out of clientRunnable & append it to the threadList
            ServerRunnable serverRunnable = new ServerRunnable(clientSocket, this);
            Thread clientThread = new Thread(serverRunnable);
            this.clientThreads.add(clientThread);

            clientThread.setDaemon(true);
            clientThread.start();


        } catch (Exception e) {
            // "Crashes" the listening method on any exception
            this.isActive = false;
            System.out.println(e.getMessage());
        }


    }


    // ServerSocket setup method - outsourced as helper method to keep constructor clear
    private void createServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.PORT);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    // Cleanup method for server shutdown
    public void cleanUp() {
        for(Thread clientThread : this.clientThreads) {
            clientThread.interrupt();
        }
    }


    // Methods to update the token list
    public synchronized void insertToken(Token token) {
        this.activeTokens.add(token);
    }

    public synchronized void deleteToken(String tokenString) {
        List<Token> tokenList = this.activeTokens
                .stream()
                .filter(t -> t.getTokenString().equals(tokenString))
                .collect(Collectors.toList());
        Token token = tokenList.get(0);
        this.activeTokens.remove(token);
    }

    // Getter & Setter
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

}
