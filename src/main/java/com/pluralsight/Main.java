package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                "root",
                "yearup");

        Statement statement = connection.createStatement();
        //Execute a query to select all products.
        //Display the name of each product sold by Northwind to the screen.
        String query = "SELECT * FROM Products";
        // 2. Execute your query
        ResultSet results = statement.executeQuery(query);
        // process the results
        while (results.next()) {
            String name = results.getString("ProductName");
            System.out.println(name);
        }
        // 3. Close the connection
        connection.close();
    }
}
