package ishop;

import ishop.entity.Good;
import ishop.entity.User;
import ishop.service.GoodService;
import ishop.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private final UserService userService;
    private final GoodService goodService;
    private final Scanner scanner;

    public Menu(UserService userService, GoodService goodService, Scanner scanner) {
        this.userService = userService;
        this.goodService = goodService;
        this.scanner = scanner;
    }

    /** ****************************** Основное меню *********************************************************/
    //Метод, реализующий основное меню
    public void baseMenu() {

        if (userService.isAdmin()) {
            System.out.println("Администратор создан.");
        }

        boolean running = true;

        while (running) {
            System.out.println("\nВыберете действие: \n" + "1 - Регистрация пользователя \n" + "2 - Войти в кабинет \n" + "0 - Выход \n");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    creatingUser();
                    break;
                case "2":
                    loginUser2();
                    break;
                case "0":
                    System.out.println("Выходим из программы...");
                    userService.delay();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный ввод");
            }
        }
    }
    //**************************************************************************************************************//


    /*********************** Блок создания пользователя ***************************************************************/
    //Метод, создающий поля для создания пользователя
    public void creatingUser() {

        Optional<String> loginOptinal = checkLogin("логин");
        if (loginOptinal.isEmpty()) {
            System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
            return;
        }
        String login = String.valueOf(loginOptinal.get());

        Optional<String> passwordOptinal = checkValue("пароль");
        if (passwordOptinal.isEmpty()) {
            System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
            return;
        }
        String password = String.valueOf(passwordOptinal.get());

        Optional<String> nameOptinal = checkValue("имя");
        if (nameOptinal.isEmpty()) {
            System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
            return;
        }
        String name = String.valueOf(nameOptinal.get());

        Optional<String> surnameOptinal = checkValue("фамилию");
        if (surnameOptinal.isEmpty()) {
            System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
            return;
        }
        String surname = String.valueOf(surnameOptinal.get());

        Optional<LocalDate> birthdayOptinal = checkBirthday();
        if (birthdayOptinal.isEmpty()) {
            System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
            return;
        }
        LocalDate birthday = birthdayOptinal.get();

        userService.createUser(login, password, name, surname, birthday);
    }

    public Optional<String> checkLogin(String value) {

        for (int i = 3; i > 0; i--) {
            String valueInput = askValue(value);

            if (valueInput.isBlank()) {
                System.out.println("Поле '" + value + "' не может быть пустым. Повторите ввод. Осталось попыток: " + (i - 1));
                continue;
            }

            Optional<User> loginOptinal = userService.getUserByLogin(valueInput);
            if (loginOptinal.isPresent()) {
                System.out.println("Логин '" + valueInput + "' уже существует. Придумайте другой. Осталось попыток: " + (i - 1));
                continue;
            }
            return Optional.of(valueInput);
        }
        return Optional.empty();
    }

    public Optional<String> checkValue(String value) {

        for (int i = 3; i > 0; i--) {
            String valueInput = askValue(value);

            if (valueInput.isBlank()) {
                System.out.println("Поле '" + value + "' не может быть пустым. Повторите ввод. Осталось попыток: " + (i - 1));
                continue;
            }
            return Optional.of(valueInput);
        }
        return Optional.empty();
    }

    //Метод проверяющий день рождения на соответствие формату
    public Optional<LocalDate> checkBirthday() {

        for (int i = 3; i > 0; i--) {
            String birthdayEntry = askValue("день рождения в формате yyyy-mm-dd");
            if (birthdayEntry.isBlank()) {
                System.out.println("Поле '" + "день рождения" + "' не может быть пустым. Повторите ввод. Осталось попыток: " + (i - 1));
                continue;
            }

            Optional<LocalDate> birthdayOptinal = userService.parseDate(birthdayEntry);
            if (!birthdayOptinal.isPresent()) {
                System.out.println("Не корректный формат дня рождения. Повторите ввод. Осталось попыток: " + (i - 1));
                continue;
            }
            return birthdayOptinal;
        }
        return Optional.empty();
    }
    //**************************************************************************************************************//


    /************************************* Блок авторизации *************************************/
    //Метод начала авторизации - точка входа
    public void loginUser() {

        Optional<String> loginOptinal = checkLoginAuth("логин");
        if (loginOptinal.isEmpty()) {
            System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
            return;
        }
        String login = loginOptinal.get();

        Optional<User> userCurrent = Optional.empty();
        for (int i = 3; i > 0; i--) {

            String password = askValue("пароль");

            userCurrent = userService.authenticate(login, password);
            if (userCurrent.isPresent()) {
                break;
            } else {
                if (i == 1) {
                    System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
                    return;
                } else {
                    System.out.println("Пароль '" + password + "' не верный. Повторите ввод. Осталось попыток: " + (i - 1));
                    continue;
                }
            }
        }

        if (userCurrent.isPresent() && userCurrent.get().getLogin().equals("vic_tut")) {
            menuAdmin();
        } else {
            menuClient(userCurrent.get());
        }
    }

    //Метод начала авторизации - точка входа
    public void loginUser2() {

        Optional<String> loginOptinal = checkLoginAuth("логин");
        if (loginOptinal.isEmpty()) {
            System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
            return;
        }
        String login = loginOptinal.get();

        Optional<User> userCurrent = Optional.empty();
        for (int i = 3; i > 0; i--) {

            String password = askValue("пароль");

            userCurrent = userService.authenticate(login, password);
            if (userCurrent.isPresent()) {
                break;
            } else {
                System.out.println("Пароль '" + password + "' не верный. Повторите ввод. Осталось попыток: " + (i - 1));
                continue;
            }
        }

        if(userCurrent.isPresent()){
            if (userCurrent.get().getLogin().equals("vic_tut")) {
                menuAdmin();
            } else {
                menuClient(userCurrent.get());
            }
        }else {
            System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
        }
    }

    public Optional<String> checkLoginAuth(String value) {

        for (int i = 3; i > 0; i--) {
            String valueInput = askValue(value);

            if (valueInput.isBlank()) {
                System.out.println("Поле '" + value + "' не может быть пустым. Повторите ввод. Осталось попыток: " + (i - 1));
                continue;
            }

            Optional<User> loginOptinal = userService.getUserByLogin(valueInput);
            if (loginOptinal.isEmpty()) {
                System.out.println("Логин '" + valueInput + "' не существует. Повторите ввод. Осталось попыток: " + (i - 1));
                continue;
            }
            return Optional.of(valueInput);
        }
        return Optional.empty();
    }
    //**************************************************************************************************************//


    /******************************* Меню админа*************************************/
    //Метод, реализующий меню пользователя с правами администратора
    public void menuAdmin() {

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("\nВыберете действие: \n" + "1 - Показать список всех товаров \n"
                    + "2 - Показать товары по категориям \n"
                    + "3 - Показать товары по стоимости и категориям (с сортировкой) \n"
                    + "4 - Добавить товар \n"
                    + "5 - Обновить товар \n"
                    + "6 - Удалить товар \n"
                    + "7 - Показать всех пользователей \n"
                    + "8 - Найти пользователя по логину \n"
                    + "9 - Редактировать информацию о пользователе \n"
                    + "0 - Выход в главное меню \n");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    goodsShow();
                    break;
                case "2":
                    showGoodsByCategory();
                    break;
                case "3":
                    showSortGoods();
                    break;
                case "4":
                    goodService.addGood();
                    break;
                case "5":
                    goodService.callUpdateGoodById();
                    break;
                case "6":
                    goodService.delGoodById();
                    break;
                case "7":
                    usersShow();
                    break;
                case "8":
                    showUserByLogin();
                    break;
                case "9":
                    changeUserByAdmin();
                    break;
                case "0":
                    System.out.println("Выходим из программы...");
                    userService.delay();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный ввод");
            }
        }
    }
    //*************************************************************************************************************** */


    /******************************** Показать товары (п.п. 1, 2, 3) ********************************/
    //Метода, выводящий список всех товаров
    public void goodsShow(){

        List<Good> goodList = goodService.getGoods();
        System.out.println("Актуальный список товаров:");
        for (Good good : goodList) {
            System.out.println(good);
        }
    }

    //Метод поиска товара по категории
    public void showGoodsByCategory(){

        String category = askValue("категорию");

        Optional<Good> categoryOptinalFound = goodService.getGoodsByCategory(category);
        if(categoryOptinalFound.isPresent()){
            Good categoryCurrent = categoryOptinalFound.get();
            System.out.println("\nТовар(ы) категории '" + category + "\':\n" + categoryCurrent);
        }else System.out.println("\nТовар(ы) категории '" + category + "\' отсутствуют. Введете корректную категорию");
    }


    public void showSortGoods() {

        Optional<List<Good>> goodSortListOptinal = goodService.getGoodsByCategoryAndPrice();

        if(goodSortListOptinal.isPresent()){
            List<Good> goodSortList = goodSortListOptinal.get();
            System.out.println("Товары, сортированные по категории и затем по цене:\n");
            for (Good good : goodSortList){
                System.out.println(good);
            }
        }else System.out.println("\nТовары не найдены");
    }
    //*************************************************************************************************************** */


    /******************************** Блок работы с пользователями ********************************/
    //Метод, реализующий меню для изменения пользователя (администраторский доступ)
    public void menuChangeUserByAdmin(int currentId) {

        Optional<User> currentUser = userShow(currentId);
        System.out.println("Выбранный пользователь: " + currentUser.get());

        while (true) {
            System.out.println("\nВыберете действие: \n" + "1 - Изменить логин пользователя \n"
                    + "2 - Изменить пароль пользователя \n"
                    + "3 - Изменить имя пользователя \n"
                    + "4 - Изменить фамилию пользователя \n"
                    + "5 - Изменить день рождения пользователя \n"
                    + "0 - Выход \n");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введите новый логин: ");
                    userService.changeUserById(currentId, scanner.nextLine(), "1");
                    Optional<User> currentUser1 = userShow(currentId);
                    System.out.println("\nТекущие данные пользователя: "+ currentUser1.get());

                    break;
                case "2":
                    System.out.print ("Введите новый пароль: ");
                    userService.changeUserById(currentId, scanner.nextLine(), "2");
                    System.out.print ("Текущие данные пользователя: ");
                    Optional<User> currentUser2 = userShow(currentId);
                    System.out.println("\nТекущие данные пользователя: "+ currentUser2.get());
                    break;
                case "3":
                    System.out.println("Введите новое имя:");
                    userService.changeUserById(currentId, scanner.nextLine(), "3");
                    System.out.println("Текущие данные пользователя:");
                    Optional<User> currentUser3 = userShow(currentId);
                    System.out.println("\nТекущие данные пользователя: "+ currentUser3.get());
                    break;
                case "4":
                    System.out.print("Введите новую фамилию: ");
                    userService.changeUserById(currentId, scanner.nextLine(), "4");
                    System.out.print ("Текущие данные пользователя: ");
                    Optional<User> currentUser4 = userShow(currentId);
                    System.out.println("\nТекущие данные пользователя: "+ currentUser4.get());
                    break;
                case "5":
                    System.out.print("Введите день рождения: ");
                    userService.changeUserByIdLC(currentId, LocalDate.parse(scanner.nextLine()), "5");
                    System.out.print("Текущие данные пользователя: ");
                    Optional<User> currentUser5 = userShow(currentId);
                    System.out.println("\nТекущие данные пользователя: "+ currentUser5.get());
                    break;
                case "0":
                    System.out.println("Выход из меню обновления товара.");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }

    //Метод, выводящий актуальный список пользователей
    public void usersShow(){

        List<User> userList = userService.getAllUsers();
        System.out.println("Список пользователей:");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    //Метод поиска пользователя по логину
    public void showUserByLogin(){

        String login = askValue("логин");

        Optional<User> loginOptinalFound = userService.getUserByLogin(login);
        if(loginOptinalFound.isPresent()){
            User loginCurrent = loginOptinalFound.get();
            System.out.println("\nПользователь с логином '" + login + "\':\n" + loginCurrent);
        }else System.out.println("\nПользователь с логином '" + login + "\' отсутствует. Введете корректный логин");
    }

    //Метод поиска пользователя по id
    public void changeUserByAdmin(){

        try {
            int idInput = askId();
            Optional<User> idOptinal = userService.findById(idInput);

            if (idOptinal.isEmpty()) {
                System.out.println("\nПользователь с id '" +  idInput + "' отсутствует. Введите корректный id");
                return;
            }
            int id = idOptinal.get().getId();
            menuChangeUserByAdmin(id);
        }catch (NumberFormatException e){
            System.out.println("Неверный формат id. Введите число.");
        }
    }

    //Метод выводящий пользователей по ig
    Optional<User> userShow(int currentId){
        Optional<User> currentUser = userService.findById(currentId);
        if(currentUser.isPresent()){
            return currentUser;
        }
        return Optional.empty();
    }
    //*************************************************************************************************************** */



    //Метод, реализующий меню при изменении товара
    public void menuCurrentGood(Good goodForChange) {

        System.out.println("Выбранный товар:" + goodForChange);

        while (true) {
            System.out.println("\nВыберете действие: \n"
                    + "1 - Изменить название товара \n"
                    + "2 - Изменить код товара \n"
                    + "3 - Изменить бренд товара \n"
                    + "4 - Изменить категорию товара \n"
                    + "5 - Изменить цену товара \n"
                    + "6 - Изменить возрастное ограничение товара \n"
                    + "0 - Выход \n");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введите новое название товара: ");
                    goodService.changeCurrentGood(goodForChange, scanner.nextLine(), "1");
                    System.out.println("Данные товара после изменения: " + goodForChange);
                    break;
                case "2":
                    System.out.print("Введите новый код товара: ");
                    goodService.changeCurrentGood(goodForChange, scanner.nextLine(), "2");
                    System.out.println("Данные товара после изменения: " + goodForChange);
                    break;
                case "3":
                    System.out.print("Введите новый бренд товара: ");
                    goodService.changeCurrentGood(goodForChange, scanner.nextLine(), "3");
                    System.out.println("Данные товара после изменения: " + goodForChange);
                    break;
                case "4":
                    System.out.print("Введите новую категорию товара : ");
                    goodService.changeCurrentGood(goodForChange, scanner.nextLine(), "4");
                    System.out.println("Данные товара после изменения: " + goodForChange);
                    break;
                case "5":
                    System.out.print("Введите новую цену товара: ");
                    goodService.changeCurrentGoodInt(goodForChange, Integer.parseInt(scanner.nextLine()), "5");
                    System.out.println("Данные товара после изменения: " + goodForChange);
                    break;
                case "6":
                    System.out.print("Введите новые возрастные ограничения товара: ");
                    goodService.changeCurrentGoodInt(goodForChange, Integer.parseInt(scanner.nextLine()), "6");
                    System.out.println("Данные товара после изменения: " + goodForChange);
                    break;
                case "0":
                    System.out.println("Выход из меню");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }
    //*****************************************************************************************************************//


    /************************** Блок меню клиента (не админа) *************************************/
    //Метод, реализующий меню клиента (авторизованного пользователя)
    public void menuClient(User user) {//(User user)String login

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("\nВыберете действие: \n" + "1 - Показать список всех товаров \n"
                    + "2 - Показать товары по категориям: выбор категории → вывод товаров \n"
                    + "3 - Показать товары по стоимости и категориям (с сортировкой) \n"
                    + "4 - Редактировать свой профиль \n"
                    + "0 - Выход в главное меню \n");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
//                    goodService.showGoods();
                    break;
                case "2":
                    entryCategory();
                    break;
                case "3":
                    goodService.sortGoodByCategoryAndPrice();
                    break;
                case "4":
                    menuCurrentUser(user);
                    break;
                case "0":
                    System.out.println("Выходим из программы...");
                    userService.delay();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный ввод");
            }
        }
    }

    //Метод, реализующий меню для выбора изменяемых полей клиента
    public void menuCurrentUser(User userForChange) {

        System.out.println("Текущий пользователь:" + userForChange);

        while (true) {
            System.out.println("\nВыберете действие: \n"
                    + "1 - Изменить логин пользователя \n"
                    + "2 - Изменить пароль пользователя \n"
                    + "3 - Изменить имя пользователя \n"
                    + "4 - Изменить фамилию пользователя \n"
                    + "5 - Изменить день рождения пользователя \n"
                    + "0 - Выход \n");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введите новый логин: ");
                    userService.changeCurrentUser(userForChange, scanner.nextLine(), "1");
                    System.out.println("Данные пользователя после изменения: " + userForChange);
                    break;
                case "2":
                    System.out.print("Введите новый пароль: ");
                    userService.changeCurrentUser(userForChange, scanner.nextLine(), "2");
                    System.out.println("Данные пользователя после изменения: " + userForChange);
                    break;
                case "3":
                    System.out.print("Введите новое имя: ");
                    userService.changeCurrentUser(userForChange, scanner.nextLine(), "3");
                    System.out.println("Данные пользователя после изменения: " + userForChange);
                    break;
                case "4":
                    System.out.print("Введите новую фамилию: ");
                    userService.changeCurrentUser(userForChange, scanner.nextLine(), "4");
                    System.out.println("Данные пользователя после изменения: " + userForChange);
                    break;
                case "5":
                    System.out.print("Введите день рождения: ");
                    userService.changeCurrentUserLC(userForChange, LocalDate.parse(scanner.nextLine()), "5");
                    System.out.println("Данные пользователя после изменения: " + userForChange);
                    break;
                case "0":
                    System.out.println("Выход из меню");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }
    //***************************************************************************************************************//


    /************************************ Блок scanner *********************************/
    public String askValue(String value) {
        System.out.print("Введите " + value + ": ");
        return scanner.nextLine();
    }

    public int askId() {
        System.out.print("Введите id: ");
        return Integer.parseInt(scanner.nextLine());
    }

    //Меню запроса категории товара (2 - Показать товары по категориям)
    public void entryCategory() {

//        System.out.print("\nВведите категорию товара: ");
//        String entryCategory = scanner.nextLine();
//        goodService.checkCategory(entryCategory);
    }



}
