package com.petrichor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String filePath = "src\\main\\resources\\data.txt";
        List<User> users = new ArrayList<>();
        Validator validator = new Validator();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                User user = getUser(line);
                if (user.getFullName() == null &&
                        user.getBirthDate() == null &&
                        user.getPhoneNumber() == null &&
                        user.getSalary() == null) {
                    continue;
                }
                users.add(user);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        System.out.println("All users: ");
        users.forEach(System.out::println);

        long maleCount = users.stream().filter(
                u -> u.getFullName() != null && !validator.isFemale(u.getFullName())).count();
        long femaleCount = users.stream().filter(
                u -> u.getFullName() != null && validator.isFemale(u.getFullName())).count();

        System.out.println("Women: " + maleCount);
        System.out.println("Men: " + femaleCount);

        long olderThan25Count = users.stream()
                .filter(u ->
                        u.getBirthDate() != null
                        && Period.between(u.getBirthDate(),
                        LocalDate.now()).getYears() > 25)
                .count();

        System.out.println("Users older than 25: " + olderThan25Count);

        double averageSalary = users.stream()
                .filter(u ->
                        u.getSalary() != null)
                .mapToDouble(User::getSalary)
                .average()
                .orElse(0.0);

        System.out.println("Average salary: " + averageSalary);

        long womenWithValidPhone = users.stream()
                .filter(
                        u -> u.getFullName() != null &&
                                validator.isFemale(u.getFullName()) &&
                                !u.getInvalidFields().contains("phoneNumber"))
                .count();

        System.out.println("Women with valid phone number: " + womenWithValidPhone);

        System.out.println("User with incorrect data: ");
        users.stream()
                .filter(u ->
                        !u.isValid())
                .forEach(u ->
                        System.out.println("User: " + u.getFullName() + ", Invalid fields: " + u.getInvalidFields()));

    }

    private static User getUser(String line) {
        String[] data = line.split(";");
        User user = new User(null, null, null, null);
        List<String> invalidFields = new ArrayList<>();

        if (data.length > 0) {
            user.setFullName(data[0].trim());
        } else {
            invalidFields.add("fio");
        }

        if (data.length > 1) {
            user.setBirthDate(data[1].trim());
        } else {
            invalidFields.add("birthDate");
        }

        if (data.length > 2) {
            user.setPhoneNumber(data[2].trim());
        } else {
            invalidFields.add("phoneNumber");
        }

        if (data.length == 4) {
            user.setSalary(data[3].trim());
        } else {
            invalidFields.add("salary");
        }

        if (!invalidFields.isEmpty()) {
            user.setInvalidFields(invalidFields);
            user.setValid(false);
        } else {
            user.setValid(true);
        }

        return user;
    }
}