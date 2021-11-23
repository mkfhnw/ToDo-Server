package common;

public enum MessageType {

    // The following values are valid for client-to-server messages
    LOGIN,
    LOGOUT,
    CREATE_LOGIN,
    CREATE_TODO,
    CHANGE_PASSWORD,
    GET_TODO,
    DELETE_TODO,
    LIST_TODOS,
    PING,

    // The following value is valid for server-to-client messages
    RESULT;

}
