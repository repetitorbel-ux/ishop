package ishop;

import ishop.entity.User;
import ishop.service.UserService;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static ishop.service.UserService.*;

public class Main {
    public static void main(String[] args) {
        String path = "UserList.txt";

        //Отладочный код
//        File file = new File(path);
//        System.out.println("Файл существует? " + file.exists());
//        System.out.println("Абсолютный путь к файлу: " + file.getAbsolutePath());

        List<User> userList = getUserListFromFile(path);
        baseMenu(userList, path);
    }

/** Основное (первое) меню. Разделить функционал "прорисовки" и логики??? (пока не получилось) */
    public static void baseMenu(List<User> userList, String path){
//        String path = "UserList.txt";
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
//                    List<User> userList2 = getUserListFromFile(path);
                    String login = askLogin();
                    findByLogin(userList, login);
                    String loginExist = findByLogin(userList, login);

                    String pass = askPassword();
                    checkPassword(userList, pass);
                    String passRight = checkPassword(userList, pass);

                    if(!loginExist.equals("vic_tut") && passRight.equals("true")){
                        menuClient();
                    }else {
                        menuAdmin();
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
    public static void menuAdmin(){
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
//                    createUser();
                    break;
                case "2":
                    System.out.println("2");//"заглушка"
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
        scanner.close();
    }

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
//                    createUser();
                    break;
                case "2":
                    System.out.println("2");//"заглушка"
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
        scanner.close();

    }

    public static void delay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
