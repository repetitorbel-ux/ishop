package ishop.service;

import ishop.entity.Good;
import ishop.repository.GoodRepository;
import java.util.*;
import static ishop.repository.GoodRepository.deserializeGood;

public class GoodService {

    //Метод, получающий список уже существующих товаров
    public static List<Good> getGoodListFromFile(String path) {

        List<Good> goodExistList = deserializeGood(path);//В переменную типа List, в которую десериализуем файл с товарами
        System.out.println("Список товаров (getGoodListFromFile): " + goodExistList);
        return goodExistList;
    }

    //Ввод товара и создание коллекции
    public static Good entryGood(String path, int id){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название товара");
        String nameGood = scanner.nextLine();

        System.out.println("Введите код товара");
        String codeGood = scanner.nextLine();

        System.out.println("Введите бренд товара");
        String brandGood = scanner.nextLine();

        System.out.println("Задайте категорию товара");
        String categoryGood = scanner.nextLine();

        System.out.println("Введите цену товара");
        int priceGood = Integer.parseInt(scanner.nextLine());

        System.out.println("Задайте ограничение по возрасту товара (true/false)");
        boolean ageLimit = Boolean.parseBoolean(scanner.nextLine());

        //Создание нового товара и коллекции товаров
        Good goodNew = new Good(id, nameGood, codeGood, brandGood, categoryGood, priceGood, ageLimit);
        List<Good> goodList = new ArrayList<>();//пустая коллекция
//        System.out.println("Пустой список?: " + goodList);
        goodList.add(goodNew);//добавление нового (введенного) товара в коллекцию
        System.out.println("Новый (добавляемый) товар: " + goodList);
        writeGood(goodList, path);//вызов метода, сериализующего товар
        return goodNew; //нигде не используется
    }
    /** Изменить тип entryGood() на void????  */

    //Метод, сериализующий товар
    public static void writeGood(List<Good> goodList, String path){
        GoodRepository goodRepository = new GoodRepository();
        goodRepository.serializeGood(goodList, path);
    }

    public static void addGood(List<Good> goodExistList, String path){
        if (goodExistList.isEmpty()) {
//            entryGood(goodExistList, path, 1);
            entryGood(path, 1);
        }else {
            Optional<Good> idGoodMax = findMaxGoodId(goodExistList);
            if (idGoodMax.isPresent()) {
                Good idMax = idGoodMax.get();
                System.out.println("idMax = " + idMax.getId());
                int idNext = idGoodMax.get().getId() + 1;
                System.out.println("Следующий id = " + idNext);

//                Good goodNext = entryGood(goodExistList, path, idNext);
                Good goodNext = entryGood(path, idNext);

                //Создание коллекции товаров
                List<Good> goodList = new ArrayList<>();

                //Добавление в коллекцию списка из существующих товаров
                for (int i = 0; i < goodExistList.size(); i++) {
                    goodList.add(goodExistList.get(i));
                }

                //Добавление нового товара в список
                goodList.add(goodNext);

                //Создание объекта типа GoodRepository и вызов метода для сериализации списка товаров
                GoodRepository goodRepository = new GoodRepository();
                goodRepository.serializeGood(goodList, path);
            }
        }
    }
    
    //Метод, добавляющий товар - old
    public static void addGoodOld(List<Good> goodExistList, String path){

        if (goodExistList.isEmpty()) {
//            entryGood(goodExistList, path, 1);
        }

        Optional<Good> idGoodMax = findMaxGoodId(goodExistList);
        if (idGoodMax.isPresent()) {
            Good idMax = idGoodMax.get();
            System.out.println("idMax = " + idMax.getId());
            int id = idGoodMax.get().getId() + 1;
            System.out.println("Следующий id = " + id);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите название товара");
            String nameGood = scanner.nextLine();

            System.out.println("Введите код товара");
            String codeGood = scanner.nextLine();

            System.out.println("Введите бренд товара");
            String brandGood = scanner.nextLine();

            System.out.println("Задайте категорию товара");
            String categoryGood = scanner.nextLine();

            System.out.println("Введите цену товара");
            int priceGood = Integer.parseInt(scanner.nextLine());

            System.out.println("Задайте ограничение по возрасту товара (true/false)");
            boolean ageLimit = Boolean.parseBoolean(scanner.nextLine());

            //Создание "введенного" товара
            Good good = new Good(id, nameGood, codeGood, brandGood, categoryGood, priceGood, ageLimit);

            //Создание коллекции товаров
            List<Good> goodList = new ArrayList<>();

            //Добавление в коллекцию списка из существующих товаров
            for (int i = 0; i < goodExistList.size(); i++) {
                goodList.add(goodExistList.get(i));
            }

            //Добавление нового товара в список
            goodList.add(good);

            //Создание объекта типа GoodRepository и вызов метода для сериализации списка
            GoodRepository goodRepository = new GoodRepository();
            goodRepository.serializeGood(goodList, path);
        }

    }

    //Метод поиска максимального значения в списке
    private static Optional<Good> findMaxGoodId(List<Good> goodList) {
        return goodList.stream().max(Comparator.comparing(good -> {
            return good.getId();
        }));
    }

}
/*
1. Создаем методы сериализации и десериализации
2. Получение списка
Первоначально список пустой
 */