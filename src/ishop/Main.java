package ishop;

import ishop.entity.Good;
import ishop.entity.User;
import java.util.List;
import java.util.Scanner;

import static ishop.service.GoodService.*;
import static ishop.service.UserService.*;

public class Main {
    public static void main(String[] args) {
        String path = "UserList.txt";
        String pathGood = "GoodList.txt";

        //Отладочный код
//        File file = new File(path);
//        System.out.println("Файл существует? " + file.exists());
//        System.out.println("Абсолютный путь к файлу: " + file.getAbsolutePath());

        List<User> userList = getUserListFromFile(path);
        List<Good> goodList = getGoodListFromFile(pathGood);
        System.out.println("deserialized list in main: " + goodList);
        baseMenu(userList, goodList, path, pathGood);
//        menuAdmin(goodList, path);

    }

/** Основное (первое) меню. Разделить функционал "прорисовки" и логики??? (пока не получилось)
 * Избавиться от "лишиних" аргументов метода?? */
    public static void baseMenu(List<User> userList, List<Good> goodList, String path, String pathGood){
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running){
            System.out.println("\nВыберете действие: \n" + "1 - Регистрация пользователя \n" + "2 - Войти в кабинет \n" + "0 - Выход \n");
            String x = scanner.nextLine();
            switch (x) {
                case "1":
                    createUser(userList, path);
                    userList = getUserListFromFile(path);//актуализируем список, после создания первого пользователя-админа
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
                        menuAdmin(goodList, pathGood);
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
    public static void menuAdmin(List<Good> goodList, String path){
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
                    + "10 - Редактировать свой профиль \n"
                    + "11 - Выход в главное меню \n");
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
                    System.out.println("4");//"temp for debag"
                    goodList = getGoodListFromFile(path);//актуализируем список после добавления первого товара
                    addGood(goodList, path);
                    break;
                case "5":
                    System.out.println("5");//"заглушка"
                    break;
                case "6":
                    System.out.println("6");//"заглушка"
                    break;
                case "7":
                    System.out.println("7");//"заглушка"
                    break;
                case "8":
                    System.out.println("8");//"заглушка"
                    break;
                case "9":
                    System.out.println("9");//"заглушка"
                    break;
                case "10":
                    System.out.println("10");//"заглушка"
                    break;
                case "11":
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
    /*Good: id, name, code, brand (Филипс, iphone, Huawei и т.д.), category (Телефоны, стиральные машины и т.д.), price, ограничение по возрасту */
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
