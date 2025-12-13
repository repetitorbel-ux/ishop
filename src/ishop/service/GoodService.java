package ishop.service;

import ishop.entity.Good;
import ishop.repository.GoodRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static ishop.repository.GoodRepository.deserializeGood;

public class GoodService {

    //Метод, получающий список уже существующих товаров
    public static List<Good> getGoodListFromFile(String path) {

        List<Good> goodExistList = deserializeGood(path);//В переменную типа List, в которую десериализуем файл с товарами
//        System.out.println("Список товаров (getGoodListFromFile): " + goodExistList);
        return goodExistList;
    }

    //Метод, сериализующий товар
    public static void writeGood(List<Good> goodList, String path) {
        GoodRepository goodRepository = new GoodRepository();
        goodRepository.serializeGood(goodList, path);
    }

    //Метод поиска максимального значения в списке
    private static Optional<Good> findMaxGoodId (List < Good > goodList) {
        return goodList.stream().max(Comparator.comparing(good -> {
            return good.getId();
        }));
    }

    /** ******************************** Реализация п.1 - Показать список всех товаров ****************************** */
    //Метод, выводящий все товары (1 - Показать список всех товаров)
    public static void showGoods(List<Good> goodList, String path) {
        goodList = getGoodListFromFile(path);
        System.out.println("Список товаров:");
        for (Good good : goodList) {
            System.out.println(good);
        }
    }
    /** ************************************************************************************************************* */

     /** ******************************* Реализация п.2 - Показать товары по категориям******************************* */
    //Метод, проверяющий введенной с консоли категории товара (2 - Показать товары по категориям)
    public static void checkCategory(List<Good> goodList, String path, String targetCategory) {
        goodList = getGoodListFromFile(path);
        System.out.println();
        String categoryNotExist = null;
        for (Good good : goodList) {
            if (good.getCategory().equals(targetCategory)) {
                categoryNotExist = targetCategory;
            }
        }
        if (categoryNotExist == null) {
            System.out.println("Категории товара " + '\'' + targetCategory + '\'' + " не существует. Введите корректное наименование категории");
        } else showGoodsByCategory(goodList, targetCategory);
    }

    //Метод, выводящий товары определенной категории (2 - Показать товары по категориям)
    public static void showGoodsByCategory(List<Good> goodList, String targetCategory) {
        System.out.println("Список товаров в категории " + targetCategory + ": ");
        for (Good good : goodList) {
            if (good.getCategory().equals(targetCategory)) {
                System.out.println(good);
            }
        }
    }
    /** ************************************************************************************************************* */


    /** ***************** Реализация п.3 - Показать товары по стоимости и категориям (с сортировкой) ***************** */
    //Метод, выводящий товары сортированные по категории и затем по цене (3 - Показать товары по стоимости и категориям (с сортировкой))
    public static void sortGoodByCategoryAndPrice (List < Good > goodList, String path){
        System.out.println("Before sort: " + goodList);
        System.out.println();
        goodList = getGoodListFromFile(path);
        goodList.sort(Comparator.comparing((Good good1) -> good1.getCategory())
                .thenComparing(good -> good.getPrice()));
        System.out.println("After sort: " + goodList);
    }
    /** ************************************************************************************************************* */

    /** ************************************* Реализация п.4 - Добавить товар ************************************* */
    //Ввод товара и создание коллекции  (4 - Добавить товар)
    public static Good entryGood(String path, int id) {

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

        System.out.println("Задайте ограничение по возрасту товара");
        int ageLimit = Integer.parseInt(scanner.nextLine());

        /**      * Изменить тип entryGood() на void????    */
        //Создание нового товара и коллекции товаров
        Good goodNew = new Good(id, nameGood, codeGood, brandGood, categoryGood, priceGood, ageLimit);
        List<Good> goodList = new ArrayList<>();//пустая коллекция
//        System.out.println("Пустой список?: " + goodList);
        goodList.add(goodNew);//добавление нового (введенного) товара в коллекцию
        System.out.println("Новый (добавляемый) товар: " + goodList);
        writeGood(goodList, path);//вызов метода, сериализующего товар
        return goodNew; //нигде не используется
    }

    //Метод, добавляющий товар (4 - Добавить товар)
    public static void addGood(List<Good> goodExistList, String path) {
        if (goodExistList.isEmpty()) {
//            entryGood(goodExistList, path, 1);
            entryGood(path, 1);
        } else {
            Optional<Good> idGoodMax = findMaxGoodId(goodExistList);
            if (idGoodMax.isPresent()) {
                Good idMax = idGoodMax.get();
                System.out.println("idMax = " + idMax.getId());
                int idNext = idGoodMax.get().getId() + 1;
                System.out.println("Следующий id = " + idNext);

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
    /** ************************************************************************************************************* */

    /** ******************************** Реализация п.5 - Обновить товар ******************************** */
    //Метод для выбора товара по id, изменения товара и записи изменений в файл
    public static void updateGoodById(List<Good> goodList, String path) {
        goodList = getGoodListFromFile(path);

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите id товара: ");
        int id = Integer.parseInt(scanner.nextLine());

        Good good = findById(goodList, id);
        if (good == null) {
            System.out.println("Товар не найден.");
            return;
        }

        System.out.println("Выбранный товар: " + good);

        changeGood(good);  //Вызываем метод, который изменяет объект внутри списка

        writeGood(goodList, path); //Сохраняем изменения в файл

        System.out.println("\nИзменения сохранены.");
    }

    //Метод поиска товара по id
    public static Good findById(List<Good> list, int id) {
        for (Good g : list) {
            if (g.getId() == id) return g;
        }
        return null;
    }

    //Метод, изменяющий товар
    public static void changeGood(Good good) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберете действие: \n" + "1 - Изменить название товара \n"
                    + "2 - Изменить код товара \n"
                    + "3 - Изменить бренд товара \n"
                    + "4 - Изменить категорию товара \n"
                    + "5 - Изменить цену товара \n"
                    + "6 - Изменить возрастное ограничение товара \n"
                    + "0. Выход \n");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Введите новое название:");
                    good.setName(scanner.nextLine());
                    break;
                case "2":
                    System.out.println("Введите новый код:");
                    good.setCode(scanner.nextLine());
                    break;
                case "3":
                    System.out.println("Введите новый бренд:");
                    good.setBrand(scanner.nextLine());
                    break;
                case "4":
                    System.out.println("Введите новую категорию:");
                    good.setCategory(scanner.nextLine());
                    break;
                case "5":
                    System.out.println("Введите новую цену:");
                    good.setPrice(Integer.parseInt(scanner.nextLine()));
                    break;
                case "6":
                    System.out.println("Введите новое возрастное ограничение:");
                    good.setAgeLimit(Integer.parseInt(scanner.nextLine()));
                    break;
                case "0":
                    System.out.println("Выход из меню обновления товара.");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }
    /** ************************************************************************************************************* */


    /** ********************************************** Реализация п. 6 ********************************************** */
    public static void delGoodById(List<Good> goodList, String path) {
        goodList = getGoodListFromFile(path);

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите id товара: ");
        int id = Integer.parseInt(scanner.nextLine());

        Good goodTemp = null;
        Good good = findById(goodList, id);
        if (good == null) {
            System.out.println("Товар не найден.");
        }else {
            List<Good> temp = new ArrayList<>();
            for(Good g : goodList){
                if (g.getId() == id){
                    temp.add(good);
                }
                goodTemp = good;
            }
            goodList.removeAll(temp);
            System.out.println("Список после удаления: " + goodList);
        }

        writeGood(goodList, path); //Сохраняем изменения в файл

        if (!(goodTemp == null)) {
            System.out.println("\nУдаление выполнено.");
        }
    }
    /** ************************************************************************************************************* */


//****************************to Delete***********************************************
    //Пробный - For delete
    public static void changeGoodOld(Good good, int id){
        System.out.println("Наименование товара до изменения: " + '\'' + good.getName() + '\'');
        good.setName("Велосипед");
        System.out.println("Наименование товара после изменения: " + '\'' + good.getName() + '\'');
    }

    //Метод, выводящий товары определенного индентификатора - для удаления
    public static void showGoodsById(List<Good> goodList, String path, int id) {
        goodList = getGoodListFromFile(path);
        System.out.println("Товар c id " + id + ": ");
        for (Good good : goodList) {
            if (good.getId() == id) {
                System.out.println(good);
//                scaner(good, path);
            }
        }
    }

    //Метод scaner: для удаления
    public static void scaner(Good good, String path) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("\nВыберете действие: \n" + "1 - Изменение названия товара \n"
                + "2 - Изменение категории товара \n"
                + "3 - Изменение цены товара \n");
        String entry = scanner.nextLine();
        switch (entry) {
            case "1":
//                changeGood(good, path);
                break;
            case "2":
//                entryCategory(goodList, path);
                break;
            default:
                System.out.println("Неверный ввод");

        }
    }

}
//        Optional<Good> idGoodMax = findMaxGoodId(goodExistList);
//        if (idGoodMax.isPresent()) {
//            Good idMax = idGoodMax.get();
//            System.out.println("idMax = " + idMax.getId());
//            int id = idGoodMax.get().getId() + 1;
//            System.out.println("Следующий id = " + id);
//
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("Введите название товара");
//            String nameGood = scanner.nextLine();
//
//            System.out.println("Введите код товара");
//            String codeGood = scanner.nextLine();
//
//            System.out.println("Введите бренд товара");
//            String brandGood = scanner.nextLine();
//
//            System.out.println("Задайте категорию товара");
//            String categoryGood = scanner.nextLine();
//
//            System.out.println("Введите цену товара");
//            int priceGood = Integer.parseInt(scanner.nextLine());
//
//            System.out.println("Задайте ограничение по возрасту товара (true/false)");
//            boolean ageLimit = Boolean.parseBoolean(scanner.nextLine());
//
//            //Создание "введенного" товара
//            Good good = new Good(id, nameGood, codeGood, brandGood, categoryGood, priceGood, ageLimit);
//
//            //Создание коллекции товаров
//            List<Good> goodList = new ArrayList<>();
//
//            //Добавление в коллекцию списка из существующих товаров
//            for (int i = 0; i < goodExistList.size(); i++) {
//                goodList.add(goodExistList.get(i));
//            }
//
//            //Добавление нового товара в список
//            goodList.add(good);
//
//            //Создание объекта типа GoodRepository и вызов метода для сериализации списка
//            GoodRepository goodRepository = new GoodRepository();
//            goodRepository.serializeGood(goodList, path);
//        }
//
//    }
// Метод, добавляющий товар - old
//    public static void addGoodOld(List<Good> goodExistList, String path){
//
//        if (goodExistList.isEmpty()) {
//            entryGood(goodExistList, path, 1);
//       }
//

/*
public static Good showGoods(List<Good> goodList, String path) {
        goodList = getGoodListFromFile(path);
        System.out.println("Список товаров:");
        Good goods = null;
        for (Good good : goodList) {
            System.out.println(good);
            goods = good;
        }
        return goods;
    }
 */