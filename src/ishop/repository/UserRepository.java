package ishop.repository;

import ishop.entity.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String USER_PATH = "UserList.txt";

    /** Создать класс(ы) с исключением для вывода теста ошибки???? */

    //Метод, выполняющий сериализацию
    public void saveUser(List<User> userList){

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(USER_PATH))) {
            objectOutputStream.writeObject(userList);
        } catch (IOException e) { //Обрабатываем все возможные ошибки ввода-вывода
            throw new RuntimeException(e); //Выкидываем исходное исключение в RuntimeException, чтобы не скрывать информацию об ошибке
        }
    }


    //Метод, выполняющий десериализацию
    public List<User> getAllUsers(){

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(USER_PATH))) {
            List<User> userList = (List<User>) objectInputStream.readObject();
            return userList;
        } catch (FileNotFoundException e){
            return new ArrayList<>();
        } catch (IOException e) {// Если файл отсутствует или недоступен
            throw new RuntimeException(e);//Выкидываем исходное исключение в RuntimeException, чтобы не скрывать информацию об ошибке
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
