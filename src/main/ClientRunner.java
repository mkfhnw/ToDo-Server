package main;

import client.ToDoClient;

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

        // Read incoming response
        toDoClient.parseResponse();

    }


}
