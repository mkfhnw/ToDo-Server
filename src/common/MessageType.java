package common;

public enum MessageType {

    // The following values are valid for client-to-server messages
    LOGIN("Login"),
    LOGOUT("Logout"),
    CREATE_LOGIN("CreateLogin"),
    CREATE_TODO("CreateToDo"),
    CHANGE_PASSWORD("ChangePassword"),
    GET_TODO("GetToDo"),
    DELETE_TODO("DeleteToDo"),
    LIST_TODOS("ListToDos"),
    PING("Ping"),

    // The following value is valid for server-to-client messages
    RESULT("Result");

    // Fields of the enum values
    private String name;

    // Constructor to pass the names
    MessageType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
