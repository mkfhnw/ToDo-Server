package main;

import client.ToDoClient;

public class ClientRunner {

    public static void main(String[] args) {

        // Create client
        ToDoClient toDoClient = new ToDoClient();

        // Send message
        toDoClient.sendMessage("PING");

    }


}
