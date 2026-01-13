package ishop.service;

import ishop.Menu;
import ishop.entity.User;
import ishop.exception.InvalidInputException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Validator {

    public static String validateInput(String input) throws InvalidInputException {
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
    //***************************************************************************************************************//


    /** ************** Блок валидации при регистрации пользователей ********************  */
//    public static Optional<LocalDate> parseDate(String dateString){
//
//        try {
//            DateTimeFormatter formatBirthdDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate birthdDay = LocalDate.parse(dateString, formatBirthdDay);
//            return Optional.of(birthdDay);
//        } catch (DateTimeException e) {
//            return Optional.empty();
//        }
//    }

    public static Optional<String> checkValue2(String valueInput){

        for (int i = 3; i > 0; i--) {

            if (valueInput.isBlank()){
                if (i == 1) {
                    break;
                }
                continue;
            }
        }
        return Optional.empty();
    }

//***************************************************************************************************************//



//    public static LocalDate checkDate() {
//
//        Menu menu = new Menu();
//
//        LocalDate birthdDay = null;
//
//        int n = 3;
//        while (n > 0) {
//            String currentBirthDay = menu.askValue("день рождения в формате yyyy-mm-dd");
//            try {
//                DateTimeFormatter formatBirthdDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                birthdDay = LocalDate.parse(currentBirthDay, formatBirthdDay);//проверяем соответствует ли формат ввода ДР шаблону
//                return birthdDay;
//            } catch (DateTimeException e) {//нашел в классе LocalDate
//                System.out.println("Неверный формат даты. Попробуйте еще раз (yyyy-MM-dd)");
////                    throw e;
//            }
//            n--;
//            if (n == 0) {
//                System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
//                break;
//            } else{
//                System.out.println("Введен не верный пароль, повторите ввод пароля. Количество оставшихся попыток: " + n);
//            }
//        }
//        return null;
//    }

    //Метод проверки логина на "пустоту", пробельные символы и уникальность
    public static String checkLogin(){
//        Menu menu = new Menu();
//        UserService userService = new UserService();
//
//        for (int i = 3; i > 0; i--) {
//            String loginNew = menu.askValue("логин");
//
//            if (loginNew.isBlank()) {
//                System.out.println("Логин не может быть пустым. Введите корректный логин. Осталось попыток: " + (i - 1));
//                continue;
//            }
//
//            List<User> userExistList = userService.getUserListFromFile();
//            boolean loginFound = false;
//            for (User user : userExistList) {
//                if (user.getLogin().equals(loginNew)) {
//                    loginFound = true;
//                    break;
//                }
//            }
//
//            if (loginFound) {
//                System.out.println("Логин '" + loginNew + "' уже существует. Придумайте другой. Осталось попыток: " + (i - 1));
//                continue;
//            }
//            return loginNew;
//        }
//        System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
//        menu.baseMenu();
        return null;
    }
//*******************************************************************************************************************


    /** ************** Валидация при авторизации пользователей ********************  */

}
