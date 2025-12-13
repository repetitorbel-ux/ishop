package ishop;

import ishop.entity.Good;
import ishop.entity.User;
import java.util.List;
import java.util.Scanner;

import static ishop.service.GoodService.*;
import static ishop.service.UserService.*;

public class Main {
    public static void main(String[] args) {
        String userPath = "UserList.txt";
        String goodPath = "GoodList.txt";

        List<User> userList = getUserListFromFile(userPath);
        List<Good> goodList = getGoodListFromFile(goodPath);
//        System.out.println("deserialized list in main: " + goodList);
        baseMenu(userList, userPath, goodList, goodPath);
//        menuAdmin(goodList, path);

    }

/** Основное (первое) меню. Разделить функционал "прорисовки" и логики??? (пока не получилось)
 * Избавиться от "лишиних" аргументов метода?? */
    public static void baseMenu(List<User> userList, String userPath, List<Good> goodList, String goodPath){
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running){
            System.out.println("\nВыберете действие: \n" + "1 - Регистрация пользователя \n" + "2 - Войти в кабинет \n" + "0 - Выход \n");
            String x = scanner.nextLine();
            switch (x) {
                case "1":
                    createUser(userList, userPath);
                    userList = getUserListFromFile(userPath);//актуализируем список, после создания первого пользователя-админа
                    break;
                case "2":
                    String login = askLogin();
                    findByLogin(userList, login);
                    String loginExist = findByLogin(userList, login);

                    String pass = askPassword();
                    checkPassword(userList, pass);
                    String passRight = checkPassword(userList, pass);

                    if(!loginExist.equals("vic_tut") && passRight.equals("true")){
                        menuClient();
                    }else {
                        menuAdmin(userList, userPath, goodList, goodPath);
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

    //Метод
    public static void menuAdmin(List<User> userList, String userPath, List<Good> goodList, String goodPath){
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running){
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
                    showGoods(goodList, goodPath);
                    break;
                case "2":
                    entryCategory(goodList, goodPath);
                    break;
                case "3":
                    sortGoodByCategoryAndPrice(goodList, goodPath);
                    break;
                case "4":
                    goodList = getGoodListFromFile(goodPath);//актуализируем список после добавления первого товара
                    addGood(goodList, goodPath);
                    break;
                case "5":
                    System.out.println("5 - Обновление товара");//"заглушка"
                    updateGoodById(goodList, goodPath);
                    break;
                case "6":
                    delGoodById(goodList, goodPath);
                    break;
                case "7":
                    showUsers(userList, userPath);
                    break;
                case "8":
                    entryLoginUser(userList, userPath);
                    break;
                case "9":
                    updateUserById(userList, userPath);
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
    public static void entryCategory(List<Good> goodList, String path){
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Введите категорию товара");
        String entryCategory = scanner.nextLine();
        checkCategory(goodList, path, entryCategory);
    }

    //Меню клиента
    public static void menuClient(){
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running){
            System.out.println("\nВыберете действие: \n" + "1 - Показать список всех товаров: просто вывод списка \n"
                    + "2 - Показать товары по категориям: выбор категории → вывод товаров \n"
                    + "3 - Показать товары по стоимости и категориям (с сортировкой): фильтры + сортировка \n"
                    + "4 - Редактировать свой профиль \n"
                    + "5 - Выход в главное меню \n");
            String x = scanner.nextLine();
            switch (x) {
                case "1":
                    System.out.println("1");//"заглушка"
                    break;
                case "2":
                    System.out.println("2");//"заглушка"
                    break;
                case "3":
                    System.out.println("3");//"заглушка"
                    break;
                case "4":
                    System.out.println("4");//"заглушка"
                    break;
                case "5":
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

    public static void delay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
//Temp
//public static void entryCost(List<Good> goodList, String path){
//    Scanner scanner = new Scanner(System.in);
//    System.out.println();
//    System.out.println("Введите стоимость товара");
//    String entryCost = scanner.nextLine();
////        showGoodsByCategory(goodList, entryCost);
//}

/*
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
 */