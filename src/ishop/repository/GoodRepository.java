package ishop.repository;

import ishop.entity.Good;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GoodRepository {
    private static final String GOOD_PATH = "GoodList.txt";

    //Метод, выполняющий сериализацию товаров
    public void saveGood(List<Good> goodList){

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(GOOD_PATH))) {
            objectOutputStream.writeObject(goodList);
        } catch (IOException e) { //Обрабатываем все возможные ошибки ввода-вывода
            throw new RuntimeException(e); //Выкидываем исходное исключение в RuntimeException, чтобы не скрывать информацию об ошибке
        }
    }

    //Метод, выполняющий десериализацию файла с товарами
    public List<Good> getAllGoods(){

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(GOOD_PATH))) {
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

    public Good findById(int id) {

        List<Good> goodList = getAllGoods();

        for (Good good : goodList) {
            if (good.getId() == id) return good;
        }
        return null;
    }


}
