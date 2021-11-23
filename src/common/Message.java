package common;

import java.util.Arrays;

public class Message {

    // Fields
    private final Addressor sender;
    private final Addressor recipient;
    private final String[] messageParts;
    private final String[] dataParts;
    private final MessageType messageType;
    private final String messageString;

    // Constructor
    public Message(String sender, String recipient, String messageString) {
        this.sender = Addressor.valueOf(sender);
        this.recipient = Addressor.valueOf(recipient);
        this.messageString = messageString;
        this.messageParts = this.messageString.split("\\|");
        this.dataParts = Arrays.copyOfRange(this.messageParts, 2, (this.messageParts.length - 1));

        // Enforce server message type, since only server can send messages of type RESULT
        if(this.sender == Addressor.SERVER) { this.messageType = MessageType.RESULT; }
        else { this.messageType = MessageType.valueOf(this.messageParts[0]); }

    }

    // Getters
    public Addressor getSender() { return sender; }
    public Addressor getRecipient() { return recipient; }
    public String[] getMessageParts() { return messageParts; }
    public MessageType getMessageType() { return messageType; }

}
