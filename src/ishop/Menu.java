package ishop;

import ishop.entity.Good;
import ishop.entity.User;
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
    }

    /** ************************* Блок меню админа *************************************/
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
                    goodService.callUpdateGoodById();
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
                    if(idFound != null) menuChangeUserByAdnin(idFound);
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

    //Метод, изменяющий данные пользователя (администраторский доступ)
    public void menuChangeUserByAdnin(Integer currentId) {

        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выбранный пользователь:");
        userService.showCurrentUser(currentId);

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
                    System.out.println("Текущие данные пользователя:");
                    userService.showCurrentUser(currentId);
                    break;
                case "2":
                    System.out.println("Введите новый пароль:");
                    userService.changeUserById(currentId, scanner.nextLine(), "2");
                    System.out.println("Текущие данные пользователя:");
                    userService.showCurrentUser(currentId);
                    break;
                case "3":
                    System.out.println("Введите новое имя:");
                    userService.changeUserById(currentId, scanner.nextLine(), "3");
                    System.out.println("Текущие данные пользователя:");
                    userService.showCurrentUser(currentId);
                    break;
                case "4":
                    System.out.println("Введите новую фамилию:");
                    userService.changeUserById(currentId, scanner.nextLine(), "4");
                    System.out.println("Текущие данные пользователя:");
                    userService.showCurrentUser(currentId);
                    break;
                case "5":
                    System.out.println("Введите день рождения:");
                    userService.changeUserByIdLC(currentId, LocalDate.parse(scanner.nextLine()), "5");
                    System.out.println("Текущие данные пользователя:");
                    userService.showCurrentUser(currentId);
                    break;
                case "0":
                    System.out.println("Выход из меню обновления товара.");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }

    //Метод показа меню при изменении товара
    public void menuCurrentGood(Good goodForChange) {
        GoodService goodService = new GoodService();
        Scanner scanner = new Scanner(System.in);

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
                    goodService.changeCurrentGood(goodForChange, scanner.nextLine(),  "1");
                    System.out.println("Данные товара после изменения: " + goodForChange);
                    break;
                case "2":
                    System.out.print("Введите новый код товара: ");
                    goodService.changeCurrentGood(goodForChange, scanner.nextLine(),  "2");
                    System.out.println("Данные товара после изменения: " + goodForChange);
                    break;
                case "3":
                    System.out.print("Введите новый бренд товара: ");
                    goodService.changeCurrentGood(goodForChange, scanner.nextLine(),  "3");
                    System.out.println("Данные товара после изменения: " + goodForChange);
                    break;
                case "4":
                    System.out.print("Введите новую категорию товара : ");
                    goodService.changeCurrentGood(goodForChange, scanner.nextLine(),  "4");
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


    /** ************************* Блок меню клиента (не админа) *************************************/
    //Метод, реализующий меню клиента (авторизованного пользователя)
    public void menuClient(User user) {//(User user)String login
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
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

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
                    userService.changeCurrentUser(userForChange, scanner.nextLine(),  "1");
                    System.out.println("Данные пользователя после изменения: " + userForChange);
                    break;
                case "2":
                    System.out.print("Введите новый пароль: ");
                    userService.changeCurrentUser(userForChange, scanner.nextLine(),  "2");
                    System.out.println("Данные пользователя после изменения: " + userForChange);
                    break;
                case "3":
                    System.out.print("Введите новое имя: ");
                    userService.changeCurrentUser(userForChange, scanner.nextLine(),  "3");
                    System.out.println("Данные пользователя после изменения: " + userForChange);
                    break;
                case "4":
                    System.out.print("Введите новую фамилию: ");
                    userService.changeCurrentUser(userForChange, scanner.nextLine(),  "4");
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
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nВведите " + value + ": ");
        return scanner.nextLine();
    }

    //Меню запроса категории товара (2 - Показать товары по категориям)
    public void entryCategory() {
        GoodService goodService = new GoodService();

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nВведите категорию товара: ");
        String entryCategory = scanner.nextLine();
        goodService.checkCategory(entryCategory);
    }

    public String entryLoginUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nВведите логин пользователя: ");
        String entryLogin = scanner.nextLine();
        return entryLogin;
    }

}
