package ishop.service;

import ishop.constants.Role;
import ishop.entity.User;
import ishop.repository.UserRepository;

import java.time.LocalDate;
import java.util.*;

public class UserService {
    private static final String ADMIN = "vic_tut";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /******************************* Методы для работы со слоем Repository *******************************/
    //Метод, получающий список существующих пользователей
    public List<User> getUserListFromFile() {
//        UserRepository userRepository = new UserRepository();
        List<User> tempList = userRepository.getAllUsers();//В переменную типа List, в которую десериализуем файл с пользователями
//        System.out.println(tempList);
        return tempList;
    }

    //Метод, сериализующий пользователя
    public void writeUser(List<User> userList) {
//        UserRepository userRepository = new UserRepository();
        userRepository.saveUser(userList);
    }
    //*****************************************************************************************************************/


    /******************************* Вспомогательные методы *******************************/
    //Метод, реализующий задержку при выходе из программы
    public void delay() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //*****************************************************************************************************************/


    /**********************************************1 - Регистрация (создание) пользователя********************************************/
    public boolean isAdmin(){

        //Считывание актуального списка пользователей
        List<User> userExistList = getUserListFromFile();

        //Проверка, если список пустой, то вызываем метод, создающий пользователя-админа
        if (userExistList.isEmpty()) {
            createAdmin();
            return true;
        }
        return false;
    }
    //Метод, создающий пользователя-админа
    public void createAdmin() {

        LocalDate adminBirthday = LocalDate.of(1974, 4, 25);

        User userAdmin = new User(0, ADMIN, "12345", "Viktor", "Ivanov", adminBirthday, Role.ADMIN);
        List<User> userList = new ArrayList<>();
        userList.add(userAdmin);
        userRepository.saveUser(userList);
//        UserRepository userRepository1 = new UserRepository();
//        userRepository1.saveUser(userList);
    }

    //Метод поиска максимального значения в списке
    private Optional<User> findMaxId(List<User> userList) {
        return userList.stream().max(Comparator.comparing(user -> {
            return user.getId();
        }));
    }

    //Метод поиска существующего логина в списке пользователей
    public Optional<User> findUserByLogin(String login){

        List<User> userList = getUserListFromFile();
        return userList.stream().filter(user -> user.getLogin().equals(login)).findFirst();
    }

    //Метод, создающий пользователя
    public void createUser(String login, String password, String name, String surname, LocalDate birthday){

        //Считывание актуального списка пользователей
        List<User> userExistList = getUserListFromFile();

        //Проверка десериализованного файла: если !null - ввод данных
        Optional<User> idUserMax = findMaxId(userExistList);
        if (idUserMax.isPresent()) {
            User idMax = idUserMax.get();
//            System.out.println("idMax = " + idMax.getId());
            int id = idUserMax.get().getId() + 1;
            System.out.println("Следующий id = " + id + " (данная информация чисто для отладки)");

            //Создание "введенного" пользователя
            User user = new User(id, login, password, name, surname, birthday, Role.USER);

            //Создание коллекции пользователей
            List<User> userList = new ArrayList<>();

            //Добавление в коллекцию списка из существующих пользователей
            for (int i = 0; i < userExistList.size(); i++) {
                userList.add(userExistList.get(i));
            }

            //Добавление нового пользователя в список
            userList.add(user);

            //Создание объекта типа UserRepository и вызов метода для сериализации списка
            userRepository.saveUser(userList);
        }
    }
    //*****************************************************************************************************************/


    /******************************** Реализация п.2 baseMenu() - Авторизация *******************************/
    //Метод поиска а в списке пользователей
    public Optional<User> authenticate(String login, String password){

        List<User> userList = getUserListFromFile();
        return userList.stream().filter(user -> user.getLogin().equals(login)).findFirst()
                .filter(user -> user.getPassword().equals(password));
    }

    public Optional<User> findUserPassword(String password){

        List<User> userList = getUserListFromFile();
        return userList.stream().filter(user -> user.getPassword().equals(password)).findFirst();
    }

    //Метод поиска пользователя по введенному логину
//    public User findUserByLogin(String login) {
//        List<User> userList = getUserListFromFile();

//        String currentLogin = login;
//        int n = 3;

//        while (n > 0) {
//            // Ищем пользователя в списке
//            for (User user : userList){
//                if (user.getLogin().equals(currentLogin)){
//                    System.out.println("Пользователь с логином '" + currentLogin + "' найден.");
//                    return user; // Если нашли, возвращаем пользователя
//                }
//            }
//            // Если пользователь не найден
//            n--;
//            if (n > 0){
//                System.out.println("Пользователь с таким логином не найден. Осталось попыток: " + n);
////                currentLogin = menu.askLogin(); // Запрашиваем логин снова
//                currentLogin = menu.askValue("логин");
//            }else {
//                System.out.println("Пользователь не найден. Попытки исчерпаны.");
//            }
//        }
//        return null;// Если все попытки исчерпаны, возвращаем null
//    }
    //************************************************************************************************************* */


    /********************** Реализация п.4 меню Client - Редактировать информацию о пользователе ********************/
    //Метод, изменяющий данные авторизованного пользователя
    public void changeCurrentUser(User userForChange, String value, String valueChoice) {
        List<User> userList = getUserListFromFile();

        int currentId = userForChange.getId();
        String choice = valueChoice;
        switch (choice) {
            case "1":
                for (User u : userList) {
                    if (currentId == u.getId()) {
                        u.setLogin(value);
                        break;
                    }
                }
                userForChange.setLogin(value);//сохраняем изменения в объект для последующего вывода изменений
                writeUser(userList);
                break;
            case "2":
                for (User u : userList) {
                    if (currentId == u.getId()) {
                        u.setPassword(value);
                        break;
                    }
                }
                userForChange.setPassword(value);
                writeUser(userList);
                break;
            case "3":
                for (User u : userList) {
                    if (currentId == u.getId()) {
                        u.setFirstname(value);
                        break;
                    }
                }
                userForChange.setFirstname(value);
                writeUser(userList);
                break;
            case "4":
                for (User u : userList) {
                    if (currentId == u.getId()) {
                        u.setLastname(value);
                        break;
                    }
                }
                userForChange.setLastname(value);
                writeUser(userList);
                break;
            case "0":
                System.out.println("Выход из меню");
                return;
            default:
                System.out.println("Неверный ввод");
        }
    }

    public void changeCurrentUserLC(User userForChange, LocalDate value, String valueChoice) {
        List<User> userList = getUserListFromFile();
        int currentId = userForChange.getId();
        switch (valueChoice) {
            case "5":
                for (User u : userList) {
                    if (currentId == u.getId()) {
                        u.setBirthday(value);
                        break;
                    }
                }
                userForChange.setBirthday(value);
                writeUser(userList);
                break;
            case "0":
                System.out.println("Выход из меню");
                return;
            default:
                System.out.println("Неверный ввод");
        }
    }

    public void showCurrentUser(int id) {
        List<User> userList = getUserListFromFile();
        for (User u : userList) {
            if (id == u.getId()) {
                System.out.println(u);
                break;
            }
        }
    }
    //************************************************************************************************************* */


    /**************************** Реализация п.7 меню Admin - Показать всех пользователей *****************************/
    //Метод, выводящий всех пользователей (7 - Показать всех пользователей)
    public void showUsers() {
        List<User> userList = getUserListFromFile();
        System.out.println("Список пользователей:");
        for (User user : userList) {
            System.out.println(user);
        }
    }
    //*****************************************************************************************************************/


    /*************************** Реализация п.8 меню Admin - Найти пользователя по логину ******************************/
    //Метод проверки введенного логина
    public void showUsersByLogin() {
        List<User> userList = getUserListFromFile();
//        Menu menu = new Menu();
//        String loginExist = null;
//
//        boolean running = true;
//        while (running) {
//            String targetLogin = menu.entryLoginUser();
//            for (User user : userList) {
//                if (user.getLogin().equals(targetLogin)) {
//                    loginExist = targetLogin;
//                    System.out.println(user);
//                    running = false;
//                }
//            }
//            if (loginExist == null) {
//                System.out.println("Пользователя с логином " + '\'' + targetLogin + '\'' + " не существует. Введите корректный логин");
//            }
//        }
    }
    //*****************************************************************************************************************/


    /********************** Реализация п.9 меню Admin - Редактировать информацию о пользователе*********************/
    //Метод поиска пользователя по id
    public Integer findById() {
        Validator validator = new Validator();
        List<User> userList = getUserListFromFile();
        int n = 3;
        while (n > 0) {
            Integer id = Integer.valueOf(validator.checkValue("id"));
            for (User user : userList) {
                if (user.getId() == id) {
                    return id;
                }
            }
            n--;
            if (n == 0) {
                System.out.println("Количество попыток исчерпано. Пройдите процедуру заново");
            } else
                System.out.println("Пользователь с id = " + id + " не найден. Повторите ввод. Количество оставшихся попыток: " + n);
        }
        return null;
    }

    //Метод, непосредственно изменяющий пользователя (поля, кроме дня рождения) - администраторский доступ
    public void changeUserById(Integer id, String valueNew, String valueChoice) {

        List<User> userList = getUserListFromFile();

        String choice = valueChoice;
        switch (choice) {
            case "1":
                for (User u : userList) {
                    if (id == u.getId()) {
                        u.setLogin(valueNew);
                        break;
                    }
                }
                writeUser(userList);
                break;
            case "2":
                for (User u : userList) {
                    if (id == u.getId()) {
                        u.setPassword(valueNew);
                        break;
                    }
                }
                writeUser(userList);
                break;
            case "3":
                for (User u : userList) {
                    if (id == u.getId()) {
                        u.setFirstname(valueNew);
                        break;
                    }
                }
                writeUser(userList);
                break;
            case "4":
                for (User u : userList) {
                    if (id == u.getId()) {
                        u.setLastname(valueNew);
                        break;
                    }
                }
                writeUser(userList);
                break;
            case "0":
                System.out.println("Выход из меню");
                return;
            default:
                System.out.println("Неверный ввод");
        }
    }

    //Метод изменяющий день рождения
    public void changeUserByIdLC(Integer id, LocalDate newDate, String valueChoice) {
        List<User> userList = getUserListFromFile();
        switch (valueChoice) {
            case "5":
                for (User u : userList) {
                    if (id == u.getId()) {
                        u.setBirthday(newDate);
                        break;
                    }
                }
                writeUser(userList);
            case "0":
                System.out.println("Выход из меню");
                return;
            default:
                System.out.println("Неверный ввод");
        }
    }
    //************************************************************************************************************** */


}
