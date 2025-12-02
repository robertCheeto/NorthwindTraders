package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Exercise4Categories {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/northwind";
    private static final String user = "root";
    private static final String password = "yearup";
    private static Connection connection = null;
    private static final Scanner keyboard = new Scanner(System.in);
    private static boolean running = true;

    public static void main(String[] args) {
        while (running) {
            System.out.println("\n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");
            System.out.println("What table do you want to see?");
            System.out.println("1) Display All Product Data");
            System.out.println("2) Display ALL Customer Data");
            System.out.println("3) Display ALL Categories");
            System.out.println("99) Exit Program");
            System.out.print("Enter input here: ");

            loadConnection();

            int userInput = keyboard.nextInt();
            keyboard.nextLine();

            switch (userInput) {
                case 1 -> productData();
                case 2 -> customerData();
                case 3 -> {
                    categoryData();
                    productSearchByCategoryID();
                }
                case 99 -> running = false;
                default -> System.out.println("Please enter a valid input.");
            }
        }
        keyboard.close();
        System.exit(0);
    }

    public static void productData() {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try (PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery()) {

            System.out.println("ProductID \t ProductName \t UnitPrice \t UnitsInStock");
            System.out.println("-----------------------------------------------------------");
            while (results.next()) {
                int prodID = results.getInt("ProductID");
                String prodName = results.getString("ProductName");
                int unitPrice = results.getInt("UnitPrice");
                int unitStock = results.getInt("UnitsInStock");
                System.out.println(prodID + "\t" + prodName + "\t" + unitPrice + "\t" + unitStock);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void customerData() {
        String query = "SELECT CompanyName, ContactName, City, Country, Phone FROM Customers ORDER BY Country";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()){

            System.out.println("CustomerID \t ContactName \t CompanyName \t City \t Country \t Phone");
            System.out.println("-----------------------------------------------------------");
            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");
                System.out.println(contactName + "\t" + companyName + "\t" + city + "\t" + country + "\t" + phone);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void categoryData() {
        String query = "SELECT CategoryID, CategoryName FROM Categories";

        ResultSet results = null;
        PreparedStatement statement = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(query);
            results = statement.executeQuery();

            System.out.println("CategoryID \t CategoryName");
            System.out.println("-----------------------------");
            while (results.next()) {
                int catID = results.getInt("CategoryID");
                String catName = results.getString("CategoryName");
                System.out.println(catID + "\t" + catName);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void productSearchByCategoryID() {
        System.out.println("Do you want to search for a product based on CategoryID?");
        System.out.println("1 = Yes | 2 = No");
        int userInput = keyboard.nextInt();
        keyboard.nextLine();

        if (userInput == 1) {
            System.out.print("Enter the CategoryID you want to search for: ");
            int categoryID = keyboard.nextInt();
            keyboard.nextLine();

            String query = "SELECT CategoryID, ProductID, ProductName, UnitPrice, UnitsInStock " +
                    "FROM Products WHERE CategoryID = ?";

            ResultSet results = null;
            PreparedStatement statement = null;
            Connection connection = null;

            try {
                connection = DriverManager.getConnection(url, user, password);
                statement = connection.prepareStatement(query);
                statement.setInt(1, categoryID);
                results = statement.executeQuery();

                System.out.println("CategoryID \t ProductID \t ProductName \t UnitPrice \t UnitsInStock");
                System.out.println("-----------------------------------------------------------");
                while (results.next()) {
                    int catID = results.getInt("CategoryID");
                    int prodID = results.getInt("ProductID");
                    String prodName = results.getString("ProductName");
                    int unitPrice = results.getInt("UnitPrice");
                    int unitStock = results.getInt("UnitsInStock");
                    System.out.println(catID + "\t" + prodID + "\t" + prodName + "\t" + unitPrice + "\t" + unitStock);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                if (results != null) {
                    try {
                        results.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        else if (userInput == 2) {
            System.out.println("Have a nice day!");
        }

        else {
            System.out.println("Enter a valid input.");
        }
    }

    public static void loadConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("error when loading connection");
            e.printStackTrace();
        }
    }

}
