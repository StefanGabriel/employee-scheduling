package com.presence.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DatabaseConnection {
    //definim o variabila in care stocam link-ul catre baza de date, dar si datele de logare
    //aceste date se pot gasi si in fisierul cu proprietati ale aplicatiei (application.proprietes)
    private static String DB_URL = "jdbc:h2:mem:testdb;USER=sa;PASSWORD=password";
    //cream o instanta pentru conexiunea cu baza de date care se va realiza la pornirea aplicatiei
    //vom lucra cu o baza de date In Memory
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //urmatoarea metoda va salva intr-o lista rezultatul returnat de baza de date in urma unei interogari
    //de tip SELECT
    public static List<HashMap<Integer, String>> getMultipleColumns(String query, int numberOfColumns) {
        List<HashMap<Integer, String>> response = new ArrayList<>();
        HashMap<Integer, String> hashMap;
        try {
            //injectam query si salvam rezultatul
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                hashMap = new HashMap<>();
                for (int i = 1; i <= numberOfColumns; i++) {
                    hashMap.put(i, resultSet.getString(i));
                }
                response.add(hashMap);
            }
        } catch (SQLException e) {
            System.out.println("SQL error!");
            e.printStackTrace();
        }
        return response;
    }

    //folosim urmatoarea metoda pentru a executa query care nu returneaza
    //niciun rezultate, si anume INSERT/DELETE/UPDATE
    public static void executeQuery(String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("SQL error!");
            e.printStackTrace();
        }
    }
}
