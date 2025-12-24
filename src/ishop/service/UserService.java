package ishop.service;

import ishop.Menu;
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
    //Метод начала авторизации
    public void enter(){
        UserService userService = new UserService();
        Menu menu = new Menu();

        String login = menu.askLogin();
        User userCurrent = userService.findUserByLogin(login);//возвратили user

        User userAuth = userService.checkPassword(userCurrent);

        if (userAuth.getLogin().equals("vic_tut")) {
            menu.menuAdmin();
        }else {
//            menu.menuClient(userAuth);
            menu.menuClient(login);
        }
    }

    //Метод поиска пользователя по введенному логину
    public User findUserByLogin(String login) {
        List<User> userList = getUserListFromFile();
        boolean check = false;
        User userExist = null;
        String newLogin = login;//переменная для того, чтобы передать значение за пределы цикла
        for (int i = 0; i < userList.size(); i++) {
            String loginExist = userList.get(i).getLogin();
            if (newLogin.equals(loginExist)) {
                userExist = userList.get(i);
                check = true;
            }
        }
        if (check) {
            System.out.println("Пользователь с логином " + newLogin + " найден");

        } else {
            System.out.println("Пользователя с логином " + "'" + newLogin + "'" + " не существует." +
                    "\nВыберете действие: \n" + "1 - Регистрация пользователя \n" + "2 - Повторный ввод логина \n");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    createUser();
                    break;
                case "2":
                    enter();
            }
        }
        return userExist;
    }

    //Метод запроса пароля
    public String askPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пароль");
        String passCurrent = scanner.nextLine();
        return passCurrent;
    }

    //Метод проверки введенного пароля
    public User checkPassword(User user){
        boolean running = true;
        while (running) {
            String currentPass = askPassword();
            if (user.getPassword().equals(currentPass)) {
                running = false;
                break;
            } else {
                System.out.println("Введен не верный пароль, повторите ввод пароля");
            }
        }
        return user;
    }
    //************************************************************************************************************* */


    /********************** Реализация п.4 меню Client - Редактировать информацию о пользователе ********************/
    public void changeCurrentUser(String loginCurrent, String value, String valueChoice) {//public void writeChange(User userForChange, String value, String valueChoice)
        List<User> userList = getUserListFromFile();
        String choice = valueChoice;
        switch (choice) {
            case "1":
                for(User u : userList){
                    if(loginCurrent.equals(u.getLogin())){
                        u.setLogin(value);
                        break;
                    }
                }
                writeUser(userList);
            case "2":
                for(User u : userList){
                    if(loginCurrent.equals(u.getLogin())){
                        u.setPassword(value);
                        break;
                    }
                }writeUser(userList);
            case "3":
                for(User u : userList){
                    if(loginCurrent.equals(u.getLogin())){
                        u.setFirstname(value);
                        break;
                    }
                }writeUser(userList);
            case "4":
                for(User u : userList){
                    if(loginCurrent.equals(u.getLogin())){
                        u.setLastname(value);
                        break;
                    }
                }writeUser(userList);
            case "0":
                System.out.println("Выход из меню");
                return;
            default:
                System.out.println("Неверный ввод");
        }
    }

    public void changeCurrentUserLC(String loginCurrent, LocalDate value, String valueChoice) {//public void writeChange(User userForChange, String value, String valueChoice)
        List<User> userList = getUserListFromFile();
        switch (valueChoice) {
            case "5":
                for(User u : userList){
                    if(loginCurrent.equals(u.getLogin())){
                        u.setBirthday(value);
                        break;
                    }
                }writeUser(userList);
            case "0":
                System.out.println("Выход из меню");
                return;
            default:
                System.out.println("Неверный ввод");
        }
    }

    public void showCurrentUser(String loginCurrent) {
        List<User> userList = getUserListFromFile();
        for (User u : userList) {
            if (loginCurrent.equals(u.getLogin())) {
                System.out.println(u);
                break;
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
    //Метод проверки введенного логина
    public void showUsersByLogin() {//String targetLogin
        List<User> userList = getUserListFromFile();
        Menu menu = new Menu();
        String loginExist = null;

        boolean running = true;
        while (running) {
            String targetLogin = menu.entryLoginUser();
            for (User user : userList) {
                if (user.getLogin().equals(targetLogin)) {
                    loginExist = targetLogin;
                    System.out.println(user);
                    running = false;
                }
            }
            if (loginExist == null) {
                System.out.println("Пользователя с логином " + '\'' + targetLogin + '\'' + " не существует. Введите корректный логин");
            }
        }
    }
    //*****************************************************************************************************************/


    /********************** Реализация п.9 меню Admin - Редактировать информацию о пользователе*********************/
    //Метод для выбора товара по id, изменения информации о пользователе и записи изменений в файл
    public void updateUserById() {//List<User> userList, String userPath

        Menu menu = new Menu();
        List<User> userList = getUserListFromFile();

        Scanner scanner = new Scanner(System.in);
//        System.out.println("\nВведите id пользователя: ");
//        int id = Integer.parseInt(scanner.nextLine());
        int id = menu.askId();

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
                    + "0 - Выход \n");

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


}
