package main;

import client.ToDoClient;

public class ClientRunner {

    public static void main(String[] args) {

        // Sleep to wait for the server to spin up
        try{ Thread.sleep(5000); } catch (Exception e) { System.out.println(e.getMessage()); }

        // Create client
        ToDoClient toDoClient = new ToDoClient();

        // Send message
        toDoClient.sendMessage("PING");

        // Read incoming response
        toDoClient.parseResponse();

    }


}
