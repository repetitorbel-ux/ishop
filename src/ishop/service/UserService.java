package ishop.service;

import ishop.constants.Role;
import ishop.entity.Good;
import ishop.entity.User;
import ishop.repository.GoodRepository;
import ishop.repository.UserRepository;

import java.util.*;

import static ishop.repository.UserRepository.deserialize;
import static ishop.service.GoodService.checkCategory;

public class UserService {

    //Метод, создающий пользователя-админа
    public static void createAdmin(String path) {
        UserRepository userRepository = new UserRepository();
        User userAdmin = new User(1, "vic_tut", "12345", "Viktor", "Ivanov", "1974-04-25", Role.ADMIN);
        List<User> userList = new ArrayList<>();
        userList.add(userAdmin);
        userRepository.serialize(userList, path);
        System.out.println("Администратор создан.");
    }

    //Метод, получающий список уже существующих пользователей
    public static List<User> getUserListFromFile(String path) {
        List<User> tempList = deserialize(path);//В переменную типа List, в которую десериализуем файл с пользователями
//        System.out.println(tempList);
        return tempList;
    }

    //Метод, сериализующий пользователя
    public static void writeUser(List<User> userList, String userPath) {
        UserRepository userRepository = new UserRepository();
        userRepository.serialize(userList, userPath);
    }


    /**************************** Реализация п.7 меню Admin - Показать всех пользователей *****************************/

    //Метод, выводящий всех пользователей (7 - Показать всех пользователей)
    public static void showUsers(List<User> userList, String userPath) {
        userList = getUserListFromFile(userPath);
        System.out.println("Список пользователей:");
        for (User user : userList) {
            System.out.println(user);
        }
    }
    /** ************************************************************************************************************* */


    /*************************** Реализация п.8 меню Admin - Найти пользователя по логину ******************************/
    //Меню запроса логина
    public static void entryLoginUser(List<User> userList, String userPath) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Введите логин пользователя");
        String entryLogin = scanner.nextLine();
        checkLogin(userList, userPath, entryLogin);
    }

    public static void checkLogin(List<User> userList, String userPath, String targetLogin) {
        userList = getUserListFromFile(userPath);
        System.out.println();
        String loginNotExist = null;
        for (User user : userList) {
            if (user.getLogin().equals(targetLogin)) {
                loginNotExist = targetLogin;
            }
        }
        if (loginNotExist == null) {
            System.out.println("Пользователя с логином " + '\'' + targetLogin + '\'' + " не существует. Введите корректный логин");
        } else showUsersByLogin(userList, targetLogin);
    }

    //Метод, выводящий пользователя с заданным логином (8 - Найти пользователя по логину)
    public static void showUsersByLogin(List<User> userList, String targetLogin) {
        System.out.println("Пользователь для редактирования " + targetLogin + ": ");
        for (User user : userList) {
            if (user.getLogin().equals(targetLogin)) {
                System.out.println(user);
            }
        }
    }
    /** ************************************************************************************************************* */


    /** ********************* Реализация п.9 меню Admin - Редактировать информацию о пользователе *********************/
    //Метод для выбора товара по id, изменения информации о пользователе и записи изменений в файл
    public static void updateUserById(List<User> userList, String userPath) {
        userList = getUserListFromFile(userPath);

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите id пользователя: ");
        int id = Integer.parseInt(scanner.nextLine());

        User user = findById(userList, id);
        if (user == null) {
            System.out.println("Товар не найден.");
            return;
        }

        System.out.println("Выбранный товар: " + user);

        changeUser(user);  //Вызываем метод, который изменяет объект внутри списка

        writeUser(userList, userPath); //Сохраняем изменения в файл

        System.out.println("\nИзменения сохранены.");
    }

    //Метод поиска пользователя по id
    public static User findById(List<User> userList, int id) {
        for (User user : userList) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    //Метод, изменяющий информацию о пользователе
    public static void changeUser(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберете действие: \n" + "1 - Изменить логин пользователя \n"
                    + "2 - Изменить пароль пользователя \n"
                    + "3 - Изменить имя пользователя \n"
                    + "4 - Изменить фамилию пользователя \n"
                    + "5 - Изменить день рождения пользователя \n"
                    + "0. Выход \n");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Введите логин:");

                    user.setLogin(scanner.nextLine());
                    break;
                case "2":
                    System.out.println("Введите новый пароль:");
                    user.setPassword(scanner.nextLine());
                    break;
                case "3":
                    System.out.println("Введите новое имя:");
                    user.setFirstname(scanner.nextLine());
                    break;
                case "4":
                    System.out.println("Введите новую фамилию:");
                    user.setLastname(scanner.nextLine());
                    break;
                case "5":
                    System.out.println("Введите день рождения:");
                    user.setBirthday(scanner.nextLine());
                    break;
                case "0":
                    System.out.println("Выход из меню обновления товара.");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }
    /** ************************************************************************************************************* */


    /********************************************** Создание пользователей ********************************************/
    //Метод, создающий пользователей
    public static void createUser(List<User> tempList, String path) {

        //Проверка, если список пустой, то вызываем метод, создающий пользователя-админа
        if (tempList.isEmpty()) {
            createAdmin(path);
        }

        /** Сделать через try-catch, чтобы при null выбрасывалось исключение???? */
        //Проверка десериализованного файла: если !null - ввод данных
        Optional<User> idUserMax = findMaxId(tempList);
        if (idUserMax.isPresent()) {
            User idMax = idUserMax.get();
            System.out.println("idMax = " + idMax.getId());
            int id = idUserMax.get().getId() + 1;
            System.out.println("Следующий id = " + id);

            Scanner scanner = new Scanner(System.in);

            /** Вынести в отдельный метод???? */
            //********Блок проверки на уникальность логина
            String newLogin = "";
            boolean running = true;
            while (running) {
                boolean loginFound = false;
                System.out.println("Введите login");
                String login = scanner.nextLine();
                newLogin = login;
                for (int i = 0; i < tempList.size(); i++) {
                    String loginExist = tempList.get(i).getLogin();
                    if (newLogin.equals(loginExist)) {
                        loginFound = true;
                    }
                }
                if (loginFound == true) {
                    System.out.println("Логин " + "'" + newLogin + "'" + " уже существует. Придумайте другой логин");
                } else {
                    running = false;
                }
            }

            System.out.println("Введите password");
            String pass = scanner.nextLine();

            System.out.println("Введите name");
            String name = scanner.nextLine();

            System.out.println("Введите surname");
            String surname = scanner.nextLine();

            System.out.println("Введите birthday");
            String birthdDay = scanner.nextLine();

            //Создание "введенного" пользователя
            User user = new User(id, newLogin, pass, name, surname, birthdDay, Role.USER);

            //Создание коллекции пользователей
            List<User> userList = new ArrayList<>();

            //Добавление в коллекцию списка из существубщих пользователей
            for (int i = 0; i < tempList.size(); i++) {
                userList.add(tempList.get(i));
            }

            //Добавление нового пользователя в список
            userList.add(user);

            //Создание объекта типа UserRepository и вызов метода для сериализации списка
            UserRepository userRepository = new UserRepository();
            userRepository.serialize(userList, path);
        }
    }

    //Метод поиска максимального значения в списке
    private static Optional<User> findMaxId(List<User> userList) {
        return userList.stream().max(Comparator.comparing(user -> {
            return user.getId();
        }));
    }

    //Метод запроса логина при регистрации пользователя
    public static String askLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите login");
        String login = scanner.nextLine();
        return login;
    }

    //Метод запроса пароля при регистрации пользователя
    public static String askPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пароль");
        String pass = scanner.nextLine();
        return pass;
    }

    //Метод, отвечающий за поиск пользователя по логину
    public static String findByLogin(List<User> tempList, String login) {

        boolean loginFound = false;
        String newLogin = login;//для того, чтобы передать значение за пределы цикла
        for (int i = 0; i < tempList.size(); i++) {
            String loginExist = tempList.get(i).getLogin();
            if (newLogin.equals(loginExist)) {
                loginFound = true;
            }
        }
        if (loginFound == true) {
            return newLogin;
        } else {
            System.out.println("Пользователя с логином " + "'" + newLogin + "'" + " не существует. Пройдите регистрацию");
            return null;
        }
    }

    //Метод, проверяющий введенный пароль
    public static String checkPassword(List<User> tempList, String pass) {

        boolean passFound = false;
        String passFromUser = pass;
        for (int i = 0; i < tempList.size(); i++) {
            String passExist = tempList.get(i).getPassword();
            if (passFromUser.equals(passExist)) {
                passFound = true;
            }
        }
        if (passFound == true) {
            return "true";
        } else {
            System.out.println("Введен не верный пароль, повторите ввод пароля");
            return "false";
        }
    }

}
/*
//Лишний метод
    public static boolean checkAccess(List<User> userList, String userPath) {
        userList = getUserListFromFile(userPath);
        boolean result = false;
        for (User user : userList) {
            if (user.getRole() == Role.ADMIN) {
                result = true;
                break;
            }else result = false;
        }
        return result;
    }
 */