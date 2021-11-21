package server.messageProtocol;

/* The ServerMessage class
 * This class represents a valid message from the server to the client, which the client is able to parse.
 * An object of this class is always tied to a ClientMessage. Upon creation of a ClientMessage, an instance of an
 * object of this class is always generated
 */

public class ServerMessage {

    // Fields
    private ServerMessageType messageType;
    private boolean isSuccessful;
    private String returnData;
    private String token;

    // Constructor
    public ServerMessage(boolean isSuccessful, String returnData, String token) {
        this.messageType = ServerMessageType.RESULT;
        this.isSuccessful = isSuccessful;
        this.returnData = returnData;
        this.token = token;
    }

    // Getter

    // ToString
    @Override
    public String toString() {
        return this.messageType.toString()
                + "|" + this.token
                + "|" + String.valueOf(this.isSuccessful)
                + "|" + this.returnData;
    }

}
