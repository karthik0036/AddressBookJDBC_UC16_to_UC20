package com.bridge;

import java.util.List;
import java.util.Scanner;

public class AddressBookJDBC {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean exit = false;
        while (!exit) {
            System.out.println(" Press\n 1 ->  Retrieve data\n 2 -> Update Address,city,state,zip  by name\n 3 -> exit");
            switch (scanner.nextInt()) {
                case 1:
                    retrieveData();
                    break;
                    case 2:
                        updateCity();
                        break;
                case 3:
                    exit = true;
            }
        }
    }

    private static void retrieveData() {
        AddressBookRepo addressBookRepo = new AddressBookRepo();
        List<Contacts> employeeInfoList = addressBookRepo.retrieveData();
        for (Contacts employee : employeeInfoList) {
            System.out.println(employee + "\n");
        }
    }

    private static void updateCity() {
        AddressBookRepo addressBookRepo = new AddressBookRepo();
        System.out.println("Enter the address,city,state, zip and firsName  to Update");
        addressBookRepo.updateCityByZip(scanner.next(), scanner.next(), scanner.next(), scanner.nextInt(), scanner.next());
    }
}
