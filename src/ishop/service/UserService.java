package ishop.service;

import ishop.constants.Role;
import ishop.entity.User;
import ishop.repository.UserRepository;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ishop.repository.UserRepository.deserialize;

public class UserService {

    //Метод, создающий пользователя-админа
    public void createAdmin() {
        UserRepository userRepository = new UserRepository();

        LocalDate adminBierthday = LocalDate.of(1974, 04, 25);

        User userAdmin = new User(1, "vic_tut", "12345", "Viktor", "Ivanov", adminBierthday, Role.ADMIN);
        List<User> userList = new ArrayList<>();
        userList.add(userAdmin);
        userRepository.serialize(userList);
        System.out.println("Администратор создан.");
    }

    //Метод, получающий список уже существующих пользователей
    public List<User> getUserListFromFile() {
        List<User> tempList = deserialize();//В переменную типа List, в которую десериализуем файл с пользователями
//        System.out.println(tempList);
        return tempList;
    }

    //Метод, сериализующий пользователя
    public void writeUser(List<User> userList) {
        UserRepository userRepository = new UserRepository();
        userRepository.serialize(userList);
    }


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
    /** ************************************************************************************************************* */


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
    /** ************************************************************************************************************* */

    /** ********************* Реализация п.4 меню Client - Редактировать информацию о пользователе *********************/
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
//    public static User findByLogin2(List<User> userList, String login) {
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


    /** ********************* Реализация п.9 меню Admin - Редактировать информацию о пользователе *********************/
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


    /********************************************** Создание пользователей ********************************************/
    //Метод, создающий пользователей
    public void createUser() {

        UserService userService = new UserService();

        //Считывание актуального списка пользователей
        List<User> userExistList = userService.getUserListFromFile();

        //Проверка, если список пустой, то вызываем метод, создающий пользователя-админа
        if (userExistList.isEmpty()) {
            createAdmin();
        }

        /** Сделать через try-catch, чтобы при null выбрасывалось исключение???? */
        //Проверка десериализованного файла: если !null - ввод данных
        Optional<User> idUserMax = findMaxId(userExistList);
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
                for (int i = 0; i < userExistList.size(); i++) {
                    String loginExist = userExistList.get(i).getLogin();
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

            System.out.println("Введите день рождения в формате yyyy-mm-dd");
            boolean running2 = true;
            LocalDate birthdDay = null;
            while (running2) {
                try {
                    String inputBirthday = scanner.nextLine();
                    DateTimeFormatter formatBirthdDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    birthdDay = LocalDate.parse(inputBirthday,formatBirthdDay);//проверяем соответствует ли формат ввода ДР шаблону
                    System.out.println(birthdDay);
                    running2 = false;
                }catch (DateTimeException e){//нашел в классе LocalDate
                    System.out.println("Неверный формат даты. Попробуйте еще раз (yyyy-MM-dd)");
                    throw e;
                }
            }

            //Создание "введенного" пользователя
            User user = new User(id, newLogin, pass, name, surname, birthdDay, Role.USER);

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
            userRepository.serialize(userList);
        }
    }

    //Метод поиска максимального значения в списке
    private Optional<User> findMaxId(List<User> userList) {
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

    //Метод, проверяющий введенный пароль
    /** !!!"Сделать проверку на null!!!*/
    public String checkPassword(List<User> tempList, String pass) {
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
                if(passFound == true){
                    running = false;
                    break;
                }
            }
            return "false";
        }
    }

    public String checkPassword3(String user, String pass) {
        List<User> userList = getUserListFromFile();
        String passFound = pass;
        for(User u : userList){
            if(u.getLogin().equals(user) && u.getPassword().equals(pass)){
                passFound = pass;
                break;
            }else {
                System.out.println("Введен не верный пароль, повторите ввод пароля");
                boolean running = true;
                while (running) {
                    String currentPass = askPassword();
                    if(currentPass.equals(passFound)){
                        running = false;
                        break;
                    }
                }
            }
        }
        return passFound;
    }

    public static String checkPassword2(List<User> tempList, String path) {
        boolean running = true;
        boolean passFound = false;
        while (running) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите пароль");
            String pass = scanner.nextLine();
            for (int i = 0; i < tempList.size(); i++) {
                String passExist = tempList.get(i).getPassword();
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