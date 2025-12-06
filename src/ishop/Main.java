package ishop;

import java.util.Scanner;

import static ishop.service.UserService.createUser;

public class Main {
    public static void main(String[] args) {
        baseMenu();
    }

    //Основное (первое) меню. Разделить функционал "прорисовки" и логики??? (пока не получилось)
    public static void baseMenu(){
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running){
            System.out.println("\nВыберете действие: \n" + "1 - Регистрация пользователя \n" + "2 - Войти в кабинет \n" + "0 - Выход \n");
            String x = scanner.nextLine();
            switch (x) {
                case "1":
                    createUser();
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
                    createUser();
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
                    createUser();
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
