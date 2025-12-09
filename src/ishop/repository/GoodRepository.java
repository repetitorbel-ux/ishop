package ishop.repository;

import ishop.entity.Good;
import ishop.entity.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GoodRepository {

    //Метод, выполняющий сериализацию товаров
    public static void serializeGood(List<Good> goodList, String path){

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(goodList);
        } catch (IOException e) { //Обрабатываем все возможные ошибки ввода-вывода
            throw new RuntimeException(e); //Выкидываем исходное исключение в RuntimeException, чтобы не скрывать информацию об ошибке
        }
    }

    //Метод, выполняющий десериализацию файла с товарами
    public static List<Good> deserializeGood(String path){

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))) {
            List<Good> goodList = (List<Good>) objectInputStream.readObject();
            return goodList;
        } catch (FileNotFoundException e){
            return new ArrayList<>();
        } catch (IOException e) {// Если файл отсутствует или недоступен
            throw new RuntimeException(e);//Выкидываем исходное исключение в RuntimeException, чтобы не скрывать информацию об ошибке
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
