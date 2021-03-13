package edu.bsu.cs222.finalProject;

import java.sql.*;

public class Database {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb",
                "root",
                "Whymakethechange79" );
    }

    public void createUser(String username, String password) throws SQLException {
        PreparedStatement preppedStatement = getConnection().prepareStatement("INSERT INTO USERS VALUES(?,?)");
        preppedStatement.setString( 1, username );
        preppedStatement.setString( 2, password);
        preppedStatement.execute();
        preppedStatement.close();
    }
}