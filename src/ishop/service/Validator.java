package ishop.service;

import ishop.entity.User;
import ishop.exception.InvalidInputException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Validator {

    public String validateInput(String input) throws InvalidInputException {
        if (input == null) {
            throw new InvalidInputException("Ввод не может быть null. Повторите операцию.");
        }
        return input;
    }

    public Integer validateInputInt(Integer input) throws InvalidInputException {
        if (input == null) {
            throw new InvalidInputException("Ввод не может быть null. Повторите операцию.");
        }
        return input;
    }

    public String checkValue(String fieldName){

        Scanner scanner = new Scanner(System.in);
        String newValue = "tempValue";
        boolean running = true;
        while (running) {
            System.out.println("Введите " + fieldName);
            String value = scanner.nextLine();
            validateInput(value);//Проверка на null
            newValue = value;
            if (!value.isBlank()) {
                running = false;
            } else {
                System.out.println("Поле" + "'" + fieldName + "'" + " не может быть пустым. Введите корректное значение");
            }

        }
        return newValue;
    }

    public Integer checkValueInt(String fieldName){
        Scanner scanner = new Scanner(System.in);
        Integer newValue = null;
        boolean running = true;
        while (running) {
            System.out.println("Введите " + fieldName);
            Integer value = Integer.parseInt(scanner.nextLine());
            validateInputInt(value);//Проверка на null
            newValue = value;
            if (value instanceof Integer) {
                running = false;
            } else {
                System.out.println("Поле" + " '" + fieldName + "'" + " не может быть пустым. Введите корректное значение");
            }
        }
        return newValue;
    }

    public LocalDate checkDate(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите день рождения в формате yyyy-mm-dd");
        boolean running2 = true;
        LocalDate birthdDay = null;
        while (running2) {
            try {
                String inputBirthday = scanner.nextLine();
                DateTimeFormatter formatBirthdDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                birthdDay = LocalDate.parse(inputBirthday, formatBirthdDay);//проверяем соответствует ли формат ввода ДР шаблону
                System.out.println(birthdDay);
                running2 = false;
            } catch (DateTimeException e) {//нашел в классе LocalDate
                System.out.println("Неверный формат даты. Попробуйте еще раз (yyyy-MM-dd)");
//                    throw e;
            }
        }
        return birthdDay;
    }

    //Метод проверки логина на "пустоту", пробельные символы и уникальность
    public String checkLogin() {
        UserService userService = new UserService();
        List<User> userExistList = userService.getUserListFromFile();
        Scanner scanner = new Scanner(System.in);
        String newLogin = "tempLogin";
        boolean running = true;
        while (running) {
            boolean loginFound = false;
            System.out.println("Введите login");
            String login = scanner.nextLine();

            newLogin = login;
            if (!login.isBlank()) {//if (!login.isBlank())
                for (int i = 0; i < userExistList.size(); i++) {
                    String loginExist = userExistList.get(i).getLogin();
                    if (newLogin.equals(loginExist)) {
                        loginFound = true;
                    }
                }
                if (loginFound == true) {
                    System.out.println("Логин " + "'" + newLogin + "'" + " уже существует. Придумайте другой логин");
                } else {
                    running = false;
                }
            } else {
                System.out.println("Логин " + "'" + newLogin + "'" + " не может быть пустым. Введите корректный логин");
            }
        }
        return newLogin;
    }


}
