package server.services;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {

    // Fields
    private String token;
    private String connectionString;


    // Constructor
    /* This constructor is used to create an initial database for a new account. Upon reception of the CREATE_LOGIN
     * a new database is created with the user's chosen name.
     */
    public DatabaseManager(String accountName) {
        String fileString = System.getProperty("user.dir");
        this.connectionString = fileString + File.separator + "src" + File.separator + "server" + File.separator
                + "database" + File.separator + accountName + ".db";
        this.connectionString = "jdbc:sqlite:" + connectionString;
    }

    // Custom methods

    /* Database initialization method
     * This method gets called after the server receives a CREATE_LOGIN method. It throws the default database schema
     * shown in the picture on the README.
     */
    public void initializeDatabase() {
        try(Connection connection = DriverManager.getConnection(this.connectionString);
            Statement statement = connection.createStatement();) {

            System.out.println("[DATABASE-MANAGER] Initializing database...");

            // Create Accounts table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Accounts (" +
                    "Account_ID INTEGER PRIMARY KEY," +
                    "Name STRING NOT NULL," +
                    "Password STRING NOT NULL)");

            // Create items table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Items (" +
                    "ToDo_ID INTEGER PRIMARY KEY," +
                    "Account_ID INTEGER NOT NULL," +
                    "Title STRING NOT NULL," +
                    "Priority STRING NOT NULL," +
                    "Description STRING," +
                    "DueDate STRING)");

        } catch (Exception e) {
            System.out.println("[DATABASE-MANAGER] EXCEPTION: " + e.getMessage());
        }
    }

    /* Methods to store login credentials
     * Stores the login credentials on the database. Assumes that the input was already validated when this method gets
     * called! Handle with caution.
     */
    public void storeLoginCredentials(String username, String password) {
        try(Connection connection = DriverManager.getConnection(this.connectionString);
            Statement statement = connection.createStatement();) {

            String writeString = "INSERT INTO Accounts (Name, Password) VALUES("
                    + "'" + username + "', "
                    + "'" + password + "'"
                    + ")";
            statement.executeUpdate(writeString);

        } catch (Exception e) {
            System.out.println("[DATABASE-MANAGER] EXCEPTION: " + e.getMessage());
        }
    }

}
