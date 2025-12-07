package ishop.service;

import ishop.constants.Role;
import ishop.entity.User;
import ishop.repository.UserRepository;
import java.util.*;
import static ishop.repository.UserRepository.deserialize;

public class UserService {

    //Метод, создающий пользователя-админа
    public static void createAdmin(){
        UserRepository userRepository = new UserRepository();
        String nameOfFile = "UserList.txt";
        User userAdmin = new User(1, "vic_tut", "12345","Viktor", "Ivanov", "1974-04-25", Role.ADMIN);
        List<User> userList = new ArrayList<>();
        userList.add(userAdmin);
        userRepository.serialize(userList,nameOfFile);
    }

    //Метод, создающий пользователей
    public static void createUser(){
        //Сохраняем в переменную имя файла для хранения пользователей
        String nameOfFile = "UserList.txt";

        //Создаем переменную типа List, в которую десериализуем файл с пользователями
        List<User> tempList = deserialize(nameOfFile);
        System.out.println(tempList);

//        tempList.clear(); //"костыль" для того, чтобы записать первым админа

        //Проверка, если список пустой, то вызываем метод, создающий пользователя-админа
        if(tempList.isEmpty()){
            createAdmin();
        }

        //Сделать через try-catch, чтобы при null выбрасывалось исключение????
        //Проверка десериализованного файла: если !null - ввод данных
        Optional<User> idUserMax = findMaxId(tempList);
        if (idUserMax.isPresent()) {
            User idMax = idUserMax.get();
            System.out.println("idMax = " + idMax.getId());
            int id = idUserMax.get().getId() + 1;
            System.out.println("Следующий id = " + id);

            //Обработка введенных данных с клавиатуры
            Scanner scanner = new Scanner(System.in);

//        System.out.println("Введите id");
//        int id = Integer.parseInt(scanner.nextLine());
            String newLogin = "";
            boolean running = true;
            while (running){
                boolean loginFound = false;
                System.out.println("Введите login");
                String login = scanner.nextLine();
                newLogin = login;
                for(int i = 0; i < tempList.size(); i++){
                    String loginExist = tempList.get(i).getLogin();
                    if(newLogin.equals(loginExist)){
                        loginFound = true;
                    }
                }
                if(loginFound == true){
                    System.out.println("Логин " + "'" + newLogin + "'" + " уже существует. Придумайте другой логин");
                }else {
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

            //Добавление в коллекцию списка из десериализованного файла
            for(int i = 0; i < tempList.size(); i++){
                userList.add(tempList.get(i));
            }

            //Добавление нового пользователя в список
            userList.add(user);

            //Создание объекта типа UserRepository и вызов метода для сериализации списка
            UserRepository userRepository = new UserRepository();
            userRepository.serialize(userList,nameOfFile);
        }
    }

    //Поиск максимального значения в списке
    private static Optional<User> findMaxId(List<User> userList) {
        return userList.stream().max(Comparator.comparing(user -> {
            return user.getId();
        }));
    }

    //Метод проверки логина (в перспективе)
    private static String checkLogin(List<User> tempList, String login) {
        String loginExist = "";


        return loginExist;
    }

}
