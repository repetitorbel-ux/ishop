package ishop;

import ishop.entity.Good;
import ishop.entity.User;
import ishop.service.GoodService;
import ishop.service.UserService;

import java.util.List;
import java.util.Scanner;

import static ishop.service.GoodService.*;
import static ishop.service.UserService.*;

public class Main {
    public static void main(String[] args) {
        baseMenu();
    }

    /*** Основное (первое) меню. Разделить функционал "прорисовки" и логики??? (пока не получилось) */
    //Метод, реализующий основное меню
    //public static void baseMenu(List<User> userList, String userPath, List<Good> goodList, String goodPath)
    public static void baseMenu() {
        UserService userService = new UserService();
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("\nВыберете действие: \n" + "1 - Регистрация пользователя \n" + "2 - Войти в кабинет \n" + "0 - Выход \n");
            String x = scanner.nextLine();
            switch (x) {
                case "1":
                    userService.createUser();
//                    userList = getUserListFromFile(userPath);//актуализируем список после создания первого пользователя-админа
                    break;
                case "2":
                    String login = userService.askLogin();
                    String loginExist = userService.findByLogin(login);//возвратили логин

                    String passExist = null;
                    if(!(loginExist == null)){
                        String pass = userService.askPassword();
                        passExist = pass;
                    }
                    String passRight = "null";
                    try {
                        String passGet = userService.checkPassword(loginExist, passExist);
                        passRight = passGet;
                    }catch (RuntimeException e){
                        baseMenu();
                    }

//                    String passRight2 = checkPassword2(userList, userPath);

                    if (loginExist.equals("vic_tut") && passRight.equals(passExist)) {
                        menuAdmin();
                    }
                    else {
//                        askPassword();
                        menuClient(loginExist);
                    }
                    break;
                case "0":
                    System.out.println("Выходим из программы...");
                    delay();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный ввод");
            }
        }
//        scanner.close();
    }

    //Метод, реализующий меню пользователя с правами администратора
    public static void menuAdmin() {
        UserService userService = new UserService();
        GoodService goodService = new GoodService();
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
                    goodService.showGoods();
                    break;
                case "2":
                    entryCategory();
                    break;
                case "3":
                    goodService.sortGoodByCategoryAndPrice();
                    break;
                case "4":
//                    goodList = getGoodListFromFile();//актуализируем список после добавления первого товара
//                    goodService.addGood(goodList);
                    break;
                case "5":
                    goodService.updateGoodById();
                    break;
                case "6":
                    goodService.delGoodById();
                    break;
                case "7":
                    userService.showUsers();
                    break;
                case "8":
                    userService.entryLoginUser();
                    break;
                case "9":
                    userService.updateUserById();
                    break;
                case "0":
                    System.out.println("Выходим из программы...");
                    delay();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный ввод");
            }
        }
//        scanner.close();
    }

    //Меню запроса категории товара (2 - Показать товары по категориям)
    public static void entryCategory() {
        GoodService goodService = new GoodService();
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Введите категорию товара");
        String entryCategory = scanner.nextLine();
        goodService.checkCategory(entryCategory);
    }

    //Метод, реализующий меню клиента (авторизованного пользователя)
    public static void menuClient(String login) {
        UserService userService = new UserService();
        GoodService goodService = new GoodService();
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
                    goodService.showGoods();
                    break;
                case "2":
//                    entryCategory(goodList, goodPath);
                    break;
                case "3":
                    goodService.sortGoodByCategoryAndPrice();
                    break;
                case "4":
                    userService.updateCurrentUser(login);
                    break;
                case "0":
                    System.out.println("Выходим из программы...");
                    delay();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный ввод");
            }
        }
//        scanner.close();
    }

    //Метод, реализующий задержку при выходе из программы
    public static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
/*
//Temp
//public static void entryCost(List<Good> goodList, String path){
//    Scanner scanner = new Scanner(System.in);
//    System.out.println();
//    System.out.println("Введите стоимость товара");
//    String entryCost = scanner.nextLine();
/// /        showGoodsByCategory(goodList, entryCost);
//}


//Меню запроса полей товара - для удаления
public static void chooseFieldOfGood(List<Good> goodList, String path){
    Scanner scanner = new Scanner(System.in);
    System.out.println();
    System.out.println("\nВыберете действие: \n" + "1 - id товара \n"
            + "2 - Наименование товара \n");
    String entry = scanner.nextLine();
    switch (entry) {
        case "1":
//                entryId(goodList, path);
            break;
        case "2":
//                entryCategory(goodList, path);
            break;
        default:
            System.out.println("Неверный ввод");
    }
}

//для удаления
public static void entryId(List<Good> goodList, String path){
    Scanner scanner = new Scanner(System.in);
    System.out.println();
    System.out.println("Введите id товара");
    String entryId = scanner.nextLine();
    showGoodsById(goodList, path, Integer.parseInt(entryId));
}

        //Отладочный код
//        File file = new File(path);
//        System.out.println("Файл существует? " + file.exists());
//        System.out.println("Абсолютный путь к файлу: " + file.getAbsolutePath());

case "5":
                    boolean res = checkAccess(userList, userPath);
                    if (res) updateGoodById(goodList, goodPath);
                    break;
 */