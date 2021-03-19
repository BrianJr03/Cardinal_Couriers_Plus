package edu.bsu.cs222.finalProject;

import javafx.scene.control.Label;
import java.sql.*;

public class Database {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb",
                "root",
                "Whymakethechange79" );
    }

    private ResultSet verify_NoUserDuplicates( String username) throws SQLException {
        String command = "SELECT USERNAME FROM USER_ACCOUNTS WHERE USERNAME = ?";
        PreparedStatement preppedStatement = getConnection().prepareStatement( command );
        preppedStatement.setString( 1, username );
        return preppedStatement.executeQuery();
    }

    public boolean canCreateUser( String username, String password) throws SQLException {
        ResultSet resultSet = verify_NoUserDuplicates( username );
        if ( !resultSet.next() ) {
            String command = "INSERT INTO USER_ACCOUNTS VALUES(?,?)";
            PreparedStatement preppedStatement = getConnection().prepareStatement( command );
            preppedStatement.setString( 1, username );
            preppedStatement.setString( 2, password);
            preppedStatement.executeUpdate();
            preppedStatement.close();
            resultSet.close();
            return true;
        }
        else { resultSet.close(); return false; }
    }

    @SuppressWarnings( "unused" ) // might use later
    public void deleteUser(String username) throws SQLException {
        String command = "DELETE FROM USER_ACCOUNTS WHERE USERNAME = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement( command );
        preparedStatement.setString( 1, username );
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void logInCurrentUser(String username, String password) throws SQLException {
        String command = "INSERT INTO logged_in_user VALUES(?,?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement( command );
        preparedStatement.setString( 1, username );
        preparedStatement.setString( 2, password );
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void logOutCurrentUser() throws SQLException {
        String command = "DELETE FROM logged_in_user";
        PreparedStatement preparedStatement = getConnection().prepareStatement( command );
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public String getCurrentUsername() throws SQLException {
        String command = "SELECT * FROM logged_in_user";
        PreparedStatement preppedStatement = getConnection().prepareStatement( command );
        ResultSet resultSet = preppedStatement.executeQuery();
        if ( resultSet.next() ) {
            String username = resultSet.getString("username");
            preppedStatement.close();
            return username;
        }
        else { preppedStatement.close(); return null; }
    }

    public void showCurrentUsername(Label currentUsername_Label) throws SQLException {
        currentUsername_Label.setText( getCurrentUsername() );
        currentUsername_Label.setVisible( true );
    }

    public String getCurrentPassword(String username) throws SQLException {
        String command = "SELECT PASSWORD FROM USER_ACCOUNTS WHERE USERNAME = ?";
        PreparedStatement preppedStatement = getConnection().prepareStatement( command );
        preppedStatement.setString( 1, username );
        ResultSet resultSet = preppedStatement.executeQuery();
        if ( resultSet.next() )
        { preppedStatement.close(); return resultSet.getString("password"); }
        else { preppedStatement.close(); return null; }
    }

    public boolean isValid_UserCredentials(String username, String password) throws SQLException {
        String command = "SELECT * FROM USER_ACCOUNTS WHERE USERNAME = ? AND PASSWORD = ?";
        PreparedStatement preppedStatement = getConnection().prepareStatement( command );
        preppedStatement.setString( 1, username );
        preppedStatement.setString( 2, password);
        ResultSet resultSet = preppedStatement.executeQuery();
        if ( resultSet.next() )
        { preppedStatement.close(); return true; }
        else { preppedStatement.close(); return false;}
    }
}