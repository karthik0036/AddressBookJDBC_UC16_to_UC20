package com.bridge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookRepo {

    Connection connection;

    private Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/address_book_services";
        String username = "root";
        String password = "password";
        Connection connection = null;
        try {

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Contacts> retrieveData() {
        ResultSet resultSet = null;
        List<Contacts> addressBookList = new ArrayList<Contacts>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from AddressBookTable";
            resultSet = statement.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {
                Contacts contactInfo = new Contacts();
                contactInfo.setFirstName(resultSet.getString("firstName"));
                contactInfo.setLastName(resultSet.getString("lastname"));
                contactInfo.setAddress(resultSet.getString("address"));
                contactInfo.setCity(resultSet.getString("city"));
                contactInfo.setState(resultSet.getString("state"));
                contactInfo.setZip(resultSet.getInt("zip"));
                contactInfo.setPhoneNo(resultSet.getString("phoneNo"));
                contactInfo.setEmail(resultSet.getString("email"));
                contactInfo.setType(resultSet.getString("type"));
                contactInfo.setAddressBookName(resultSet.getString("addressBookName"));


                addressBookList.add(contactInfo);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return addressBookList;

    }
}
