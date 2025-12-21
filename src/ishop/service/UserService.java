package ishop.service;

import ishop.constants.Role;
import ishop.entity.User;
import ishop.repository.UserRepository;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserService {
    private static final String ADMIN = "vic_tut";

    /******************************* Методы слоя Repository *******************************/
    //Метод, получающий список существующих пользователей
    public List<User> getUserListFromFile() {
        UserRepository userRepository = new UserRepository();
        List<User> tempList = userRepository.getAllUsers();//В переменную типа List, в которую десериализуем файл с пользователями
//        System.out.println(tempList);
        return tempList;
    }

    //Метод, сериализующий пользователя
    public void writeUser(List<User> userList) {
        UserRepository userRepository = new UserRepository();
        userRepository.saveUser(userList);
    }
    //*****************************************************************************************************************/


    /******************************* Вспомогательные методы *******************************/
    //Метод, реализующий задержку при выходе из программы
    public void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //*****************************************************************************************************************/

    /**********************************************1 - Регистрация (создание) пользователя********************************************/
    //Метод, создающий пользователя-админа
    public void createAdmin() {
        UserRepository userRepository = new UserRepository();

        LocalDate adminBirthday = LocalDate.of(1974, 04, 25);

        User userAdmin = new User(0, ADMIN, "12345", "Viktor", "Ivanov", adminBirthday, Role.ADMIN);
        List<User> userList = new ArrayList<>();
        userList.add(userAdmin);
        userRepository.saveUser(userList);
        System.out.println("Администратор создан.");
    }

    //Метод поиска максимального значения в списке
    private Optional<User> findMaxId(List<User> userList) {
        return userList.stream().max(Comparator.comparing(user -> {
            return user.getId();
        }));
    }

    //Метод, создающий пользователей
    public void createUser() {

        UserService userService = new UserService();
        Validator validator = new Validator();

        //Считывание актуального списка пользователей
        List<User> userExistList = userService.getUserListFromFile();

        //Проверка, если список пустой, то вызываем метод, создающий пользователя-админа
        if (userExistList.isEmpty()) {
            createAdmin();
        }

        //Проверка десериализованного файла: если !null - ввод данных
        Optional<User> idUserMax = findMaxId(userExistList);
        if (idUserMax.isPresent()) {
            User idMax = idUserMax.get();
            System.out.println("idMax = " + idMax.getId());
            int id = idUserMax.get().getId() + 1;
            System.out.println("Следующий id = " + id);

            //Проверка логина на уникальность и null - Не нужна проверка, так как Optional???
//            checkLoginNull(newLogin);//Old метод проверки на null
            String newLogin = validator.checkLogin();

            String newPass = validator.checkValue("пароль");

            String newName = validator.checkValue("имя");

            String newSurname = validator.checkValue("фамилию");

            LocalDate birthdDay = validator.checkDate();

            //Создание "введенного" пользователя
            User user = new User(id, newLogin, newPass, newName, newSurname, birthdDay, Role.USER);

            //Создание коллекции пользователей
            List<User> userList = new ArrayList<>();

            //Добавление в коллекцию списка из существубщих пользователей
            for (int i = 0; i < userExistList.size(); i++) {
                userList.add(userExistList.get(i));
            }

            //Добавление нового пользователя в список
            userList.add(user);

            //Создание объекта типа UserRepository и вызов метода для сериализации списка
            UserRepository userRepository = new UserRepository();
            userRepository.saveUser(userList);
        }
    }
    //*****************************************************************************************************************/


    /********************** Реализация п.2 baseMenu() - Авторизация ********************/
    //Метод запроса логина при регистрации пользователя
    public String askLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите login");
        String login = scanner.nextLine();
        return login;
    }

    //Метод, отвечающий за поиск пользователя по логину
    public String findByLogin(String login) {
        List<User> userList = getUserListFromFile();
        boolean loginFound = false;
        String newLogin = login;//переменная для того, чтобы передать значение за пределы цикла
        for (int i = 0; i < userList.size(); i++) {
            String loginExist = userList.get(i).getLogin();
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

    //Метод запроса пароля при регистрации пользователя
    public String askPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пароль");
        String pass = scanner.nextLine();
        return pass;
    }


    /********************** Реализация п.4 меню Client - Редактировать информацию о пользователе********************/
    public void updateCurrentUser(String targetLogin) {//List<User> userList, String userPath,

        List<User> userList = getUserListFromFile();

        User currentUser = null;
        for (User user : userList) {
            if (user.getLogin().equals(targetLogin)) {
                currentUser = user;
                System.out.println(currentUser);
            }
        }

        changeUserItSelf(currentUser);  //Вызываем метод, который изменяет объект внутри списка

        writeUser(userList); //Сохраняем изменения в файл

        System.out.println("\nИзменения сохранены.");
    }

    //Метод поиска пользователя по имени
//    public User findByLogin2(List<User> userList, String login) {
//        for (User user : userList) {
//            if (user.getLogin().equals(login)) return user;
//        }
//        return null;
//    }

    //Метод, изменяющий информацию о пользователе (администраторский доступ)
    public void changeUserItSelf(User user) {
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
                    System.out.println("Введите новый логин:");
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
                    user.setBirthday(LocalDate.parse(scanner.nextLine()));
                    break;
                case "0":
                    System.out.println("Выход из меню обновления товара.");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }
    //************************************************************************************************************* */

    /**************************** Реализация п.7 меню Admin - Показать всех пользователей *****************************/
    //Метод, выводящий всех пользователей (7 - Показать всех пользователей)
    public void showUsers() {
//        UserService userService = new UserService();
        List<User> userList = getUserListFromFile();
        System.out.println("Список пользователей:");
        for (User user : userList) {
            System.out.println(user);
        }
    }
    //*****************************************************************************************************************/


    /*************************** Реализация п.8 меню Admin - Найти пользователя по логину ******************************/
    //Меню запроса логина
    public void entryLoginUser() {//List<User> userList, String userPath
//        List<User> userList = getUserListFromFile();
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Введите логин пользователя");
        String entryLogin = scanner.nextLine();
        checkLogin(entryLogin);
    }

    public void checkLogin(String targetLogin) {
        List<User> userList = getUserListFromFile();
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
    public void showUsersByLogin(List<User> userList, String targetLogin) {
        System.out.println("Пользователь для редактирования " + targetLogin + ": ");
        for (User user : userList) {
            if (user.getLogin().equals(targetLogin)) {
                System.out.println(user);
            }
        }
    }
    //*****************************************************************************************************************/


    /********************** Реализация п.9 меню Admin - Редактировать информацию о пользователе*********************/
    //Метод для выбора товара по id, изменения информации о пользователе и записи изменений в файл
    public void updateUserById() {//List<User> userList, String userPath

        List<User> userList = getUserListFromFile();

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите id пользователя: ");
        int id = Integer.parseInt(scanner.nextLine());

        User user = findById(userList, id);
        if (user == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        System.out.println("Выбранный пользователь: " + user);
        changeUser(user);  //Вызываем метод, который изменяет объект внутри списка

        writeUser(userList); //Сохраняем изменения в файл

        System.out.println("\nИзменения сохранены.");
    }

    //Метод поиска пользователя по id
    public User findById(List<User> userList, int id) {
        for (User user : userList) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    //Метод, изменяющий информацию о пользователе (администраторский доступ)
    public void changeUser(User user) {
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
                    user.setBirthday(LocalDate.parse(scanner.nextLine()));
                    break;
                case "0":
                    System.out.println("Выход из меню обновления товара.");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }
    //************************************************************************************************************** */


    /**
     * Проверка пароля Доделать
     */
    //Метод, проверяющий введенный пароль
    public String checkPassword(String login, String pass) {
        List<User> userList = getUserListFromFile();
        boolean passFound = false;
        String passFromUser = pass;
        for (int i = 0; i < userList.size(); i++) {
            String passExist = userList.get(i).getPassword();
            if (passFromUser.equals(passExist)) {
                passFound = true;
            }
        }
        if (passFound == true) {
            return "true";
        } else {
            System.out.println("Введен не верный пароль, повторите ввод пароля");
            boolean running = true;
            while (running) {
                askPassword();
                if (passFound == true) {
                    running = false;
                    break;
                }
            }
            return "false";
        }
    }

    public String checkPassword4(String login, String pass) {
        List<User> userList = getUserListFromFile();
        String currentPass = pass;
        boolean running = true;
        for (User u : userList) {
        while (running) {
            String currentPass2 = askPassword();
                if (u.getLogin().equals(login) && u.getPassword().equals(currentPass2)) {
                    currentPass = currentPass2;
                    running = false;
                    break;
                } else {
                    System.out.println("Введен не верный пароль, повторите ввод пароля");
//                    String currentPass = askPassword();
//                    passFound = currentPass;
                }
            }
        }
        return currentPass;//checkPassword3(user, currentPass);
    }

    public String findLogin(String login){
        List<User> userList = getUserListFromFile();
        String loginExist = null;
        for (User user : userList) {
            if (user.getLogin().equals(login)) {
                loginExist = login;
            }
        }
        return loginExist;
    }

    public String checkPassword3(String login, String pass) {
        List<User> userList = getUserListFromFile();
        String passFound = pass;//!!!!
        String loginFound = login;
        for (User u : userList) {

            if (u.getLogin().equals(login) && u.getPassword().equals(passFound)) {
                passFound = pass;
                loginFound = login;
                break;
            } else {
                System.out.println("Введен не верный пароль, повторите ввод пароля");
                passFound = checkPassword4(login, pass);
                break;
            }
        }
        return passFound;
    }

    public String checkPassword2(String user, String pass) {
        List<User> userList = getUserListFromFile();
        boolean running = true;
        boolean passFound = false;
        while (running) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите пароль");
            String pass2 = scanner.nextLine();
            for (int i = 0; i < userList.size(); i++) {
                String passExist = userList.get(i).getPassword();
                if (pass.equals(passExist)) {
                    passFound = true;
                }
            }
            if (passFound == true) {
                running = false;
                return "true";
            } else {
                System.out.println("Введен не верный пароль, повторите ввод пароля");
                return "false";
            }
        }
        return "true";
    }


}
