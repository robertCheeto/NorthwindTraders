package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Exercise4Categories {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "yearup";
        Scanner keyboard = new Scanner(System.in);

        System.out.println("What table do you want to see?");
        System.out.println("1) Display All Product Data");
        System.out.println("2) Display ALL Customer Data");
        System.out.print("Enter input here: ");
        int userInput = keyboard.nextInt();
        keyboard.nextLine();

        if (userInput == 1) {
            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                    "FROM Products WHERE ProductID = ? OR ProductName LIKE ?";
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setInt(1, 1); //auto sanitize mal inputs
                statement.setString(2, "%%");

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
        else if (userInput == 2) {
            String query = "SELECT CompanyName, ContactName, City, Country, Phone " +
                    "FROM Customers WHERE CustomerID = ? OR CompanyName LIKE ? " +
                    "ORDER BY Country";
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setInt(1, 1); //auto sanitize mal inputs
                statement.setString(2, "%%");

                ResultSet results = statement.executeQuery();
                // CustomerID, ContactName, CompanyName, City, Country, Phone
                System.out.println("CustomerID \t ContactName \t CompanyName \t City \t Country \t Phone");
                System.out.println("-----------------------------------------------------------");
                while (results.next()) {
                    String contactName = results.getString("ContactName");
                    String companyName = results.getString("CompanyName");
                    String city = results.getString("City");
                    String country = results.getString("Country");
                    String phone = results.getString("Phone");
                    System.out.println(contactName + "\t" + companyName +
                            "\t" + city + "\t" + country + "\t" + phone);

                }
                results.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (userInput == 3) {
            String query = "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID";
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setInt(1, 1); //auto sanitize mal inputs
                statement.setString(2, "%%");

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
        else {
            System.out.println("Enter in a correct input next time!");
        }
    }
}
