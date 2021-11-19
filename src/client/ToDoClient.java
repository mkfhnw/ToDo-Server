package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ToDoClient {

    // Fields
    private static final int PORT = 9999;

    public static void main(String[] args) {

        // Creates new Socket to the server and prints its output
        try(Socket socket = new Socket("localhost", PORT)) {

            // Get socket input
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(inputReader.readLine());

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
