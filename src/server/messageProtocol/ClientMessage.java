package server.messageProtocol;

import java.time.LocalDate;

/* The ClientMessage class
 * This class represents a valid message from a client, which the server is able to parse.
 * An object of this class has an object of type ServerMessage tied to it. This means that the servers response
 * is automatically created upon construction of the ClientMessage object. Client message validation is a matter of the
 * constructor of this class. The default token is the SHA256-hashed version of the String "DefaultToken".
 */
public class ClientMessage {

    // Fields
    private ServerMessage response;
    private ClientMessageType messageType;
    private String token;
    private final String defaultToken = "3963c9cae5c5aeaa71f287190774db4d354287c7973e969e9d6c5722c1037a33";
    private String username;
    private String password;
    private int ID;
    private String title;
    private int priority;
    private String description;
    private LocalDate dateOfCreation;
    private LocalDate dueDate;
    private String messageString;

    // Constructor for receiving a client message
    public ClientMessage(String[] messageComponents){

        // Parse token
        this.token = messageComponents[1];

        // Read out components
        ClientMessageType messageType = ClientMessageType.valueOf(messageComponents[0]);
        switch (messageType) {

            case LOGIN -> {

                // Parse username & password
                this.parseUsernameAndPassword(messageComponents[2]);

                break;
            }

            case LOGOUT -> {

                break;
            }

            case CREATE_LOGIN -> {

                // Parse username & password
                this.parseUsernameAndPassword(messageComponents[2]);

                // Check if username already exists

                break;
            }

            case CREATE_TODO -> {

                // Read ot title, priority, description and duedate
                String[] itemDetails = messageComponents[2].split(";");
                this.title = itemDetails[0];
                this.priority = Integer.parseInt(itemDetails[1]);
                this.description = itemDetails[2];
                this.dueDate = LocalDate.parse(itemDetails[3]);

                // Validate data - return fail if invalid

                // If data valid, create new item

                // Return true

                break;
            }

            case CHANGE_PASSWORD -> {

                // Read out & check token

                // Validate new password

                // If new password is valid, store it

                // Return true

                break;
            }

            case GET_TODO -> {

                // Read out & check token

                // If token is valid, read out item-ID and fetch item

                // Return item

                break;
            }

            case DELETE_TODO -> {

                // Read out & check token

                // If token is valid, read out item-ID and delete item

                // Return true

                break;
            }

            case LIST_TODOS -> {

                // Read out & check token

                // If token is valid, read out item-ID and fetch item

                // Return item

                break;
            }

            case PING -> {

                // If default token, return true
                if(this.token.equals(this.defaultToken)) {
                    this.response = new ServerMessage(true, "0", this.defaultToken);
                }

                // If token is valid, return true - otherwise return false

                break;
            }

        }

        // Based on components, create according ServerMessage object



    }

    // Constructor for sending a client message
    public ClientMessage(String command, String token) {

        // Set message type & token
        this.setMessageType(command);
        this.token = token;

        // Try to parse ClientMessageType from command
        switch(messageType) {

            case LOGIN -> {

                break;
            }

            case LOGOUT -> {
                break;
            }

            case CREATE_LOGIN -> {
                break;
            }

            case CREATE_TODO -> {
                break;
            }

            case CHANGE_PASSWORD -> {
                break;
            }

            case GET_TODO -> {
                break;
            }

            case DELETE_TODO -> {
                break;
            }

            case LIST_TODOS -> {
                break;
            }

            case PING -> {
                this.messageString = this.messageType.toString()
                        + "|" + this.token
                        + "|" + "0";
            }

        }
    }

    // Private helper methods
    private void parseUsernameAndPassword(String usernamePasswordString) {

        // Parse username & password - needs to be in format username;password
        String[] usernamePassword = usernamePasswordString.split(";");
        this.username = usernamePassword[0];
        this.password = usernamePassword[1];

    }
    private void setMessageType(String command) {
        try {
            messageType = ClientMessageType.valueOf(command);
            this.messageType = messageType;
        } catch (Exception e) {
            System.out.println("Could not parse command " + command + " to ClientMessageType");
        }
    }


    // Getters
    public ServerMessage getResponse() { return response; }
    public ClientMessageType getMessageType() { return messageType; }
    public String getToken() { return token; }
    public String getDefaultToken() { return defaultToken; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getID() { return ID; }
    public String getTitle() { return title; }
    public int getPriority() { return priority; }
    public String getDescription() { return description; }
    public LocalDate getDateOfCreation() { return dateOfCreation; }
    public LocalDate getDueDate() { return dueDate; }
    public String getMessageString() { return this.messageString; }
}
