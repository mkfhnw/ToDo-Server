package server.messageProtocol;

public enum ClientMessageType {

    // Enum values represent all valid message types from client to server

    LOGIN,
    LOGOUT,
    CREATE_LOGIN,
    CREATE_TODO,
    CHANGE_PASSWORD,
    GET_TODO,
    DELETE_TODO,
    LIST_TODOS,
    PING;

}
