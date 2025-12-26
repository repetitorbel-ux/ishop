package ishop;

import ishop.service.GoodService;
import ishop.service.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {

    //Метод, реализующий основное меню
    public void baseMenu() {

        UserService userService = new UserService();

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("\nВыберете действие: \n" + "1 - Регистрация пользователя \n" + "2 - Войти в кабинет \n" + "0 - Выход \n");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    userService.createUser();
                    break;
                case "2":
                    userService.enter();
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
                    goodService.addGood();
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
                    userService.showUsersByLogin();
                    break;
                case "9":
                    Integer idFound = userService.findById();
                    if(idFound != null) menuChangeUser(idFound);
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

    //Метод, изменяющий данные пользователя (администраторский доступ)
    public void menuChangeUser(Integer currentId) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

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
                    System.out.println("Введите новый логин:");
                    userService.changeUserById(currentId, scanner.nextLine(), "1");
//                    userService.changeUserById(scanner.nextLine(),  "1");
//                    user.setLogin(scanner.nextLine());
                    break;
                case "2":
                    System.out.println("Введите новый пароль:");
                    userService.changeUserById(currentId, scanner.nextLine(), "2");
                    break;
                case "3":
                    System.out.println("Введите новое имя:");
                    userService.changeUserById(currentId, scanner.nextLine(), "3");
                    break;
                case "4":
                    System.out.println("Введите новую фамилию:");
                    userService.changeUserById(currentId, scanner.nextLine(), "4");
                    break;
                case "5":
                    System.out.println("Введите день рождения:");
                    userService.changeUserByIdLC(currentId, LocalDate.parse(scanner.nextLine()), "5");
                    break;
                case "0":
                    System.out.println("Выход из меню обновления товара.");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }

    //Метод запроса логина при регистрации пользователя
    public int askId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите id пользователя: ");
        int id = Integer.parseInt(scanner.nextLine());
        return id;
    }

    //Метод запроса логина при регистрации пользователя
    public String askLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите login");
        String login = scanner.nextLine();
        return login;
    }

    //Метод запроса пароля
    public String askPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пароль");
        String passCurrent = scanner.nextLine();
        return passCurrent;
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

    public String entryLoginUser() {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Введите логин пользователя");
        String entryLogin = scanner.nextLine();
        return entryLogin;
    }

    //Метод, реализующий меню клиента (авторизованного пользователя)
    public void menuClient(String login) {//public void menuClient(User user)
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
                    entryCategory();
                    break;
                case "3":
                    goodService.sortGoodByCategoryAndPrice();
                    break;
                case "4":
                    menuCurrentUser(login);
//                    userService.showCurrentUser(login);
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

    public void menuCurrentUser(String login) {//public void changeUserItSelf(User userForChange)
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

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
                    System.out.println("Введите новый логин:");
                    userService.changeCurrentUser(login, scanner.nextLine(),  "1");
                    break;
                case "2":
                    System.out.println("Введите новый пароль:");
                    userService.changeCurrentUser(login, scanner.nextLine(),  "2");
                    break;
                case "3":
                    System.out.println("Введите новое имя:");
                    userService.changeCurrentUser(login, scanner.nextLine(),  "3");
                    break;
                case "4":
                    System.out.println("Введите новую фамилию:");
                    userService.changeCurrentUser(login, scanner.nextLine(),  "4");
                    break;
                case "5":
                    System.out.println("Введите день рождения:");
                    userService.changeCurrentUserLC(login, LocalDate.parse(scanner.nextLine()), "5");
                    break;
                case "0":
                    System.out.println("Выход из меню");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }

}
