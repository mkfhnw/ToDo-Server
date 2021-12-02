package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    // Fields
    private final Addressor sender;
    private final Addressor recipient;
    private final String token;
    private final ArrayList<String> messageParts;
    private final ArrayList<String> dataParts;
    private final MessageType messageType;
    private final String messageString;

    // Constructor used by the client to send a message
    public Message(String sender, String recipient, String token, String command, ArrayList<String> data) {
        this.sender = Addressor.valueOf(sender.toUpperCase());
        this.recipient = Addressor.valueOf(recipient.toUpperCase());
        this.token = token;
        this.messageType = MessageType.valueOf(command.toUpperCase());
        this.messageString = this.buildMessageString(this.messageType, this.token, data);
        this.messageParts = new ArrayList<>(Arrays.asList(this.messageString.split("\\|")));
        this.dataParts = this.parseDataParts(data);
    }


    // Constructor used by the server to receive a message
    public Message(String sender, String recipient, String token, String messageString) {
        this.sender = Addressor.valueOf(sender.toUpperCase());
        this.recipient = Addressor.valueOf(recipient.toUpperCase());
        this.token = token;
        this.messageString = messageString;
        this.messageParts = new ArrayList<>(Arrays.asList(this.messageString.split("\\|")));

        // Parse data parts - but only if message has more than 2 parts (the first 2 parts are always MessageType & Token - at least for now)
        if(messageParts.size() > 2) {
            this.dataParts = new ArrayList<>(this.messageParts.subList(2, (this.messageParts.size() -1)));
        } else {
            this.dataParts = new ArrayList<>();
        }

        // Enforce server message type, since only server can send messages of type RESULT
        if(this.sender == Addressor.SERVER) { this.messageType = MessageType.RESULT; }
        else { this.messageType = MessageType.valueOf(this.messageParts.get(0)); }

    }

    // Constructor used to parse message from a messageString
    public Message(String messageString) {
        String[] stringParts = messageString.split("\\|");
        this.messageString = messageString;
        this.messageType = MessageType.valueOf(stringParts[0]);
        this.messageParts = new ArrayList<>(Arrays.asList(stringParts));
        this.dataParts = new ArrayList<>(this.messageParts.subList(2, (this.messageParts.size() -1)));
        this.token = stringParts[1];
        
        // Parse sender & Recipient
        if (this.messageType == MessageType.RESULT) {
            this.sender = Addressor.SERVER;
            this.recipient = Addressor.CLIENT;
        } else {
            this.sender = Addressor.CLIENT;
            this.recipient = Addressor.SERVER;
        }
    }


    // Custom methods
    private String buildMessageString(MessageType messageType, String token, ArrayList<String> data) {

        // Return MessageType & Token if data is empty
        if(data.size() == 0) { return messageType.toString() + "|" + token; }

        // If data is not empty, build messagestring
        StringBuilder stringBuilder = new StringBuilder();
        for(String dataString : data) { stringBuilder.append(dataString).append("|"); }

        // Delete last |-char
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return messageType.toString() + "|" + token + "|" +stringBuilder.toString();
    }
    private ArrayList<String> parseDataParts(ArrayList<String> data) {

        // If data is empty, return empty arrayList
        if(data.size() == 0) { return new ArrayList<>(); }

        // Otherwise, return the list as is
        return data;

    }

    // Getters
    public Addressor getSender() { return sender; }
    public Addressor getRecipient() { return recipient; }
    public ArrayList<String> getMessageParts() { return messageParts; }
    public MessageType getMessageType() { return messageType; }
    public String getToken() { return token; }
    public ArrayList<String> getDataParts() { return dataParts; }
    public String getMessageString() { return messageString + "\n"; }
}
