package ishop;

import ishop.service.GoodService;
import ishop.service.UserService;
import java.util.Scanner;

public class Menu {

    //Метод, реализующий основное меню
    public void baseMenu() {

        UserService userService = new UserService();

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("\nВыберете действие: \n" + "1 - Регистрация пользователя \n" + "2 - Войти в кабинет \n" + "0 - Выход \n");
            String x = scanner.nextLine();
            switch (x) {
                case "1":
                    userService.createUser();
                    break;
                case "2":
                    System.out.println("Авторизация в разработке");
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
//        scanner.close();//Нужно закрывать поток????
    }

    //Метод, реализующий меню пользователя с правами администратора
    public void menuAdmin() {
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
                    userService.delay();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный ввод");
            }
        }
//        scanner.close();
    }

    //Меню запроса категории товара (2 - Показать товары по категориям)
    public void entryCategory() {
        GoodService goodService = new GoodService();

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Введите категорию товара");
        String entryCategory = scanner.nextLine();
        goodService.checkCategory(entryCategory);
    }

    //Метод, реализующий меню клиента (авторизованного пользователя)
    public void menuClient(String login) {
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
                    userService.delay();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный ввод");
            }
        }
//        scanner.close();
    }

}
