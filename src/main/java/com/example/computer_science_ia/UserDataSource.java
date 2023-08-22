package com.example.computer_science_ia;

import java.sql.*;
import java.util.ArrayList;

public class UserDataSource {

    private static final String DATABASE_NAME = "user_database.db";
    private static final String BASE_DIRECTORY = System.getProperty("user.dir");
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + BASE_DIRECTORY + "\\" + DATABASE_NAME;
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String TABLE_USERS = "users";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
            COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
            COLUMN_PASSWORD + " TEXT" +
            ");";

    private static Connection connection;

    //Method to open the connection to the database and check if the connection is successful
    public void openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(CONNECTION_STRING);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to close the connection to the database and check if closing the connection was successful
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to create the database if it does not exist
    public static void createUserDatabase(){
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE_USERS);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to insert a new user into the database
    public void insertUser(User user) throws SQLException {
        Statement statement = connection.createStatement();
        String encryptedPassword = PasswordEncryption.encrypt(user.getPassword()); //Uses the encryption method from the
        statement.execute("INSERT INTO " + TABLE_USERS +
                " (" + COLUMN_USERNAME + ", " +
                       COLUMN_PASSWORD + ") VALUES ('" + user.getUsername() + "', '" + encryptedPassword + "' )");
        statement.close(); //Ensures that the statement closes to prevent data leaks;
    }

    public static String getHashedPassword(String username) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USERNAME + " = '" + username + "'");
        resultSet.next();
        String hashedPassword = resultSet.getString(COLUMN_PASSWORD);
        resultSet.close();
        statement.close();
        return hashedPassword;
    }

    //Method to update the password of a user in the database
    public void updatePassword(User user, String newPassword) throws SQLException {
        Statement statement = connection.createStatement();
        String encryptedPassword = PasswordEncryption.encrypt(newPassword); //Uses the encryption method from the encryption class to secure passwords
        statement.execute("UPDATE " + TABLE_USERS +
                " SET " + COLUMN_PASSWORD + " = '" + encryptedPassword + "' WHERE " + COLUMN_USERNAME + " = '" + user.getUsername() + "'");
        statement.close(); //Ensures that the statement closes to prevent data leaks;
    }

    //Method to verify the password of a user in the database
    public boolean verifyPassword(User user) {
        ArrayList<User> users = getUsers();
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername()) && PasswordEncryption.verify(user.getPassword(), u.getPassword())) { //Checks if entered User matches existing User in the database
                return true;
            }
        }
        return false;
    }

    public void updateUsername(User user, String newUsername) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("UPDATE " + TABLE_USERS +
                " SET " + COLUMN_USERNAME + " = '" + newUsername + "' WHERE " + COLUMN_USERNAME + " = '" + user.getUsername() + "'");
        statement.close(); //Ensures that the statement closes to prevent data leaks;
    }

    public void deleteUser(String username) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USERNAME + " = '" + username + "'");
        statement.close(); //Ensures that the statement closes to prevent data leaks;
    }

    public boolean userExists(String username) {
        ArrayList<User> users = getUsers();
        for (User u : users) {
            if (u.getUsername().equals(username)) { //Checks if enter User matches existing User in the database
                return true;
            }
        }
        return false;
    }


    //Returns all the users in the database
    private static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT " + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + " FROM " + TABLE_USERS)) {

            while (resultSet.next()) {
                String username = resultSet.getString(COLUMN_USERNAME);
                String hashedPassword = resultSet.getString(COLUMN_PASSWORD);

                User user = new User(username, hashedPassword);
                users.add(user);
            }
        } catch (SQLException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
        return users;
    }

}
