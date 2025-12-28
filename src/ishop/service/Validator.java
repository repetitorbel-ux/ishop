package ishop.service;

import ishop.Menu;
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

    public LocalDate validateInputDate(LocalDate input) throws InvalidInputException {
        if (input == null) {
            throw new InvalidInputException("Ввод не может быть null. Повторите операцию.");
        }
        return input;
    }

    //Метод проверки введенного значения (почти универсальный)
    public String checkValue(String value) {

        Menu menu = new Menu();
        UserService userService = new UserService();

        int n = 3;
        while (n > 0) {
            if(value.equals("id")){
                userService.showUsers();
            }
            String valueInput = menu.askValue(value);
            validateInput(valueInput);//Проверка на null
            if (!valueInput.isBlank()) {
                return valueInput;
            }
            n--;
            if (n == 0) {
                System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
                menu.baseMenu();
            } else{
                System.out.println("Поле" + "'" + valueInput + "'" + " не может быть пустым. Введите корректное значение. Количество оставшихся попыток: " + n);
            }
        }
        return null;
    }

    //Old - для удаления
    public Integer checkValueInt(String fieldName) {
        Scanner scanner = new Scanner(System.in);
        Integer newValue = null;
        boolean running = true;
        while (running) {
            System.out.println("Введите " + fieldName);
            Integer value = Integer.parseInt(scanner.nextLine());
//            validateInputInt(value);//Проверка на null
            newValue = value;
            if (value instanceof Integer) {
                running = false;
            } else {
                System.out.println("Поле" + " '" + fieldName + "'" + " не может быть пустым. Введите корректное значение");
            }
        }
        return newValue;
    }
    //***************************************************************************************************************//

    /** ************** Блок валидации при регистрации пользователей ********************  */
    public LocalDate checkDate() {

        Menu menu = new Menu();

        LocalDate birthdDay = null;

        int n = 3;
        while (n > 0) {
            String currentBirthDay = menu.askValue("день рождения в формате yyyy-mm-dd");
            try {
                DateTimeFormatter formatBirthdDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                birthdDay = LocalDate.parse(currentBirthDay, formatBirthdDay);//проверяем соответствует ли формат ввода ДР шаблону
                return birthdDay;
            } catch (DateTimeException e) {//нашел в классе LocalDate
                System.out.println("Неверный формат даты. Попробуйте еще раз (yyyy-MM-dd)");
//                    throw e;
            }
            n--;
            if (n == 0) {
                System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
                break;
            } else{
                System.out.println("Введен не верный пароль, повторите ввод пароля. Количество оставшихся попыток: " + n);
            }
        }
        return null;
    }

    //Метод проверки логина на "пустоту", пробельные символы и уникальность
    public String checkLogin2(){
        Menu menu = new Menu();
        UserService userService = new UserService();

        for (int i = 3; i > 0; i--) {
            String loginNew = menu.askValue("логин");

            if (loginNew.isBlank()) {
                System.out.println("Логин не может быть пустым. Введите корректный логин. Осталось попыток: " + (i - 1));
                continue;
            }

            List<User> userExistList = userService.getUserListFromFile();
            boolean loginFound = false;
            for (User user : userExistList) {
                if (user.getLogin().equals(loginNew)) {
                    loginFound = true;
                    break;
                }
            }

            if (loginFound) {
                System.out.println("Логин '" + loginNew + "' уже существует. Придумайте другой. Осталось попыток: " + (i - 1));
                continue;
            }
            return loginNew;
        }
        System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
        menu.baseMenu();
        return null;
    }

    //Метод проверки логина на "пустоту", пробельные символы и уникальность - OLD (не правильно работает - для удаления)
    public String checkLogin() {

        Menu menu = new Menu();
        UserService userService = new UserService();
        List<User> userExistList = userService.getUserListFromFile();

        String newLogin = "tempLogin";

        int n = 3;
        while (n > 0) {
            String currentLogin = menu.askValue("логин");
            boolean loginFound = false;

            newLogin = currentLogin;
            if (!currentLogin.isBlank()) {
                for (int i = 0; i < userExistList.size(); i++) {
                    String loginExist = userExistList.get(i).getLogin();
                    if (newLogin.equals(loginExist)) {
                        loginFound = true;
                    }
                }
                n--;
                if (n == 0) {
                    System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
                }else {
                    if (loginFound == true) {
                        System.out.println("Логин " + "'" + newLogin + "'" + " уже существует. Придумайте другой логин");
                    } else {
                        break;
                    }
                }
            } else {
                System.out.println("Логин " + "'" + newLogin + "'" + " не может быть пустым. Введите корректный логин");
            }
        }
        return newLogin;
    }
//*******************************************************************************************************************


    /** ************** Валидация при авторизации пользователей ********************  */
    //Метод проверки введенного пароля при авторизации
    public User checkPassword(User user) {
        Menu menu = new Menu();
        int n = 3;
        while (n > 0) {
            String currentPass = menu.askValue("пароль");
            if (user.getPassword().equals(currentPass)) {
                return user;
            }
            n--;
            if (n == 0) {
                System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
                menu.baseMenu();
            } else{
                System.out.println("Введен не верный пароль, повторите ввод пароля. Количество оставшихся попыток: " + n);
            }
        }
        return null;
    }
}
