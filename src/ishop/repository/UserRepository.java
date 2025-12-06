package ishop.repository;

import ishop.entity.User;
import java.io.*;
import java.util.List;

public class UserRepository {


    public static void serialize(List<User> userList, String nameOfFile){
//        String nameOfFile = "UserList";

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(nameOfFile))) {
            objectOutputStream.writeObject(userList);
        } catch (IOException e) { //Обрабатываем все возможные ошибки ввода-вывода
            throw new RuntimeException(e); //Выкидываем исходное исключение в RuntimeException, чтобы не скрывать информацию об ошибке
        }
    }

    //Создать класс с исключением для вывода теста ошибки, если файл отсутствует????

    public static List<User> deserialize(String nameOfFile){

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(nameOfFile))) {
            List<User> userList = (List<User>) objectInputStream.readObject();
            return userList;
        }catch (IOException e) {// Если файл отсутствует или недоступен
            throw new RuntimeException(e);//Выкидываем исходное исключение в RuntimeException, чтобы не скрывать информацию об ошибке
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
/*
1.Сначала сделать в репозитории метод по добавлению пользователя
2.Потом в сервисе создать этот репозитории и вызвать на нем метод по добавлению пользователя
 */