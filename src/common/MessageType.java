package common;

public enum MessageType {

    // The following values are valid for client-to-server messages
    LOGIN("Login"),
    LOGOUT("Logout"),
    CREATE_LOGIN("Create_Login"),
    CREATE_TODO("Create_Todo"),
    CHANGE_PASSWORD("Change_Password"),
    GET_TODO("Get_ToDo"),
    DELETE_TODO("Delete_ToDo"),
    LIST_TODOS("List_ToDos"),
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
