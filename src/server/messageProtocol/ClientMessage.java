package server.messageProtocol;

import java.time.LocalDate;

/* The ClientMessage class
 * This class represents a valid message from a client, which the server is able to parse.
 * An object of this class has an object of type ServerMessage tied to it. This means that the servers response
 * is automatically created upon construction of the ClientMessage object. Client message validation is a matter of the
 * constructor of this class.
 */
public class ClientMessage {

    // Fields
    private ServerMessage response;
    private ClientMessageType messageType;
    private String username;
    private String password;
    private int ID;
    private String title;
    private int priority;
    private String description;
    private LocalDate dateOfCreation;
    private LocalDate dueDate;

    // Constructor
    public ClientMessage(){

    }

    // Getters


}
