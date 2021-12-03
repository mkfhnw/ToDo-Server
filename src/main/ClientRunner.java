package main;

import client.ClientNetworkPlugin;

import java.util.ArrayList;

public class ClientRunner {

    public static void main(String[] args) {

        // Sleep to wait for the server to spin up
        try{ Thread.sleep(5000); } catch (Exception e) { System.out.println(e.getMessage()); }

        // Create client
        ClientNetworkPlugin clientNetworkPlugin = new ClientNetworkPlugin();

        // Send PING message
        ArrayList<String> data = new ArrayList<>();
        
        
        clientNetworkPlugin.sendMessage("PING", data);
//        See MessageType class for command spelling
       // data.add("Username");
       // data.add("Password");
        clientNetworkPlugin.sendMessage("LOGOUT", data);
     // toDoClient.sendMessage("LIST_TODOS", data);

        try{ Thread.sleep(5000); } catch (Exception e) { System.out.println(e.getMessage()); }

        // Read incoming response
        clientNetworkPlugin.parseResponse();

        }
    
    
        }

