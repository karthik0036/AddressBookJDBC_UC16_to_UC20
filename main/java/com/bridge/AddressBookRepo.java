package com.bridge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookRepo {

    Connection connection;

    private static Connection getConnection() {
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
    public void updateAddress(String address, String city, String state, int zip,String firstName) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "Update addressBook set address=" + "'" + address + "'" + ", " + "city=" +"'" + city + "'" + ", " + "state=" + "'" + state + "'" + ", " + "zip=" + zip + " where firstName=" + firstName + ";";
            int result = statement.executeUpdate(query);
            System.out.println(result);
            if (result > 0) {
                System.out.println("Address Updated Successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public List<Contacts> findAllForParticularDate(LocalDate date) {
        ResultSet resultSet = null;
        List<Contacts> addressBookList = new ArrayList<Contacts>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from AddressBook where date_added between cast(' "+ date + "'" +" as date)  and date(now());";
            resultSet = statement.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {
                Contacts contactInfo = new Contacts();
                contactInfo.setFirstName(resultSet.getString("firstName"));
                contactInfo.setLastName(resultSet.getString("Lastname"));
                contactInfo.setAddress(resultSet.getString("address"));
                contactInfo.setCity(resultSet.getString("city"));
                contactInfo.setState(resultSet.getString("state"));
                contactInfo.setZip(resultSet.getInt("zip"));
                contactInfo.setPhoneNo(resultSet.getString("phoneNo"));
                contactInfo.setEmail(resultSet.getString("email"));
                contactInfo.setAddressBookName(resultSet.getString("addressBookName"));
                contactInfo.setType(resultSet.getString("type"));
                contactInfo.setDateAdded(resultSet.getDate("Date_added").toLocalDate());

                addressBookList.add(contactInfo);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return addressBookList;
    }

    public int countByCiy(String city) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select count(firstName) from AddressBookTable where city=" + "'" + city + "';";
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int count = result.getInt(1);


            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countByState(String state) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select count(firstName) from AddressBookTable where city=" + "'" + state + "';";
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int count = result.getInt(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void insertData(Contacts add) throws SQLException {
        Connection connection = getConnection();
        try {
            if (connection != null) {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                String sql = "insert into addressBook(firstname,lastname,address,city,state,zip,phoneNumber,email,bookName,contactType,date_added)" +
                        "values('" + add.getFirstName() + "','" + add.getLastName() + "','" + add.getAddress() + "','" + add.getCity() +
                        "','" + add.getState() + "','" + add.getZip() + "','" + add.getPhoneNo() + "','" +
                        add.getEmail() + "','" + add.getAddressBookName() + "','" + add.getType() + "','" + add.getDateAdded() + "');";
                int result = statement.executeUpdate(sql);
                connection.commit();
                if (result > 0) {
                    System.out.println("Contact Inserted");
                }
                connection.setAutoCommit(true);
            }
        } catch (SQLException sqlException) {
            System.out.println("Insertion Rollbacked");
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
