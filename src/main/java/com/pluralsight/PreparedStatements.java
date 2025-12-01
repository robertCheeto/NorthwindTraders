package com.pluralsight;

import java.sql.*;

public class PreparedStatements {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "yearup";


        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                "FROM Products WHERE ProductID = ? OR ProductName LIKE ?";
        try {
            // Establishing connection
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, 1); //auto sanitize mal inputs
            statement.setString(2, "%%");

            // Executing query
            ResultSet results = statement.executeQuery();

            System.out.println("ProductID \t ProductName \t UnitPrice \t UnitsInStock");
            System.out.println("-----------------------------------------------------------");
            while (results.next()) {
                int prodID = results.getInt("ProductID");
                String prodName = results.getString("ProductName");
                int unitPrice = results.getInt("UnitPrice");
                int unitStock = results.getInt("UnitsInStock");
                System.out.println(prodID + "\t" + prodName + "\t" + unitPrice + "\t" + unitStock);

            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
