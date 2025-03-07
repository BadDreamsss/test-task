package com.petrichor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String fullName;
    private LocalDate birthDate;
    private String phoneNumber;
    private Double salary;
    private boolean isValid = true;

    private List<String> invalidFields = new ArrayList<>();

    private final Validator validator = new Validator();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public User() {
    }

    public User(String fio, LocalDate birthDate, String phoneNumber, Double salary) {
        this.fullName = fio;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Double getSalary() {
        return salary;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<String> getInvalidFields() {
        return invalidFields;
    }

    public void setFullName(String fullName) {
        if (validator.isValidFullName(fullName)) {
            this.fullName = fullName;
        } else {
            this.fullName = null;
            this.isValid = false;
            this.invalidFields.add("fullName: ");
        }
    }

    public void setBirthDate(String birthDateStr) {
        try {
            this.birthDate = LocalDate.parse(birthDateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            this.birthDate = null;
            this.isValid = false;
            this.invalidFields.add("birthDate");
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (validator.isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = null;
            this.isValid = false;
            this.invalidFields.add("phoneNumber");
        }
    }

    public void setSalary(String salaryStr) {
        try {
            this.salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException e) {
            this.salary = null;
            this.isValid = false;
            this.invalidFields.add("salary");
        }
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setInvalidFields(List<String> invalidFields) {
        this.invalidFields = invalidFields;
    }

    @Override
    public String toString() {
        return "User{" +
                "fio='" + fullName + '\'' +
                ", birthDate=" + (birthDate != null ? birthDate.format(DATE_FORMATTER) : null) +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", salary=" + salary +
                ", isValid=" + isValid +
                ", invalidFields=" + invalidFields +
                '}';
    }

}

