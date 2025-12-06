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
                    System.out.println("1");//"заглушка"
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
