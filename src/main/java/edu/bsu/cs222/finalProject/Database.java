package edu.bsu.cs222.finalProject;

import java.sql.*;

public class Database {

    public static void main( String[] args ) throws SQLException {
        Database db = new Database();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb",
                "root",
                "Whymakethechange79" );
    }

    public boolean canCreateUser( String username, String password) throws SQLException {
        try {
            String command = "INSERT INTO USERS VALUES(?,?)";
            PreparedStatement preppedStatement = getConnection().prepareStatement( command ); // <-- Change USERS to
            // USER_ACCOUNTS
            preppedStatement.setString( 1, username );
            preppedStatement.setString( 2, password);
            preppedStatement.executeUpdate();
            preppedStatement.close();
            return true;
        }
        catch ( SQLIntegrityConstraintViolationException e )
        { return false; }
    }

    public void deleteUser(String username) throws SQLException {
        String command = "DELETE FROM USERS WHERE NAME=?"; // <-- CHANGE NAME TO USERNAME
        PreparedStatement preparedStatement = getConnection().prepareStatement( command );
        preparedStatement.setString( 1, username );
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public boolean isValid_UserCredentials(String username, String password) throws SQLException {
        String command = "SELECT * FROM USERS where name = ? and password = ?";
        PreparedStatement preppedStatement = getConnection().prepareStatement( command );
        preppedStatement.setString( 1, username );
        preppedStatement.setString( 2, password);
        ResultSet resultSet = preppedStatement.executeQuery();
        if ( resultSet.next() )
        { preppedStatement.close(); return true; }
        else { preppedStatement.close(); return false;}
    }
}