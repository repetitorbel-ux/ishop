package ishop.service;

import ishop.entity.Good;
import ishop.repository.GoodRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class GoodService {

    /******************************* Методы слоя Repository *******************************/
    //Метод, получающий список уже существующих товаров
    public List<Good> getGoodListFromFile() {
        GoodRepository goodRepository = new GoodRepository();
        List<Good> goodExistList = goodRepository.getAllGoods();//В переменную типа List, в которую десериализуем файл с товарами
//        System.out.println("Список товаров (getGoodListFromFile): " + goodExistList);
        return goodExistList;
    }

    //Метод, сериализующий товар
    public void writeGood(List<Good> goodList) {
        GoodRepository goodRepository = new GoodRepository();
        goodRepository.saveGood(goodList);
    }
    //***************************************************************************************************************/


    /**
     * ****************************** Реализация п.1 - Показать список всех товаров
     *******************************/
    //Метод, выводящий все товары (1 - Показать список всех товаров)
    public void showGoods() {
        GoodService goodService = new GoodService();
        List<Good> goodList = goodService.getGoodListFromFile();
        System.out.println("Список товаров:");
        for (Good good : goodList) {
            System.out.println(good);
        }
    }
    //***************************************************************************************************************/


    /******************************** Реализация п.2 - Показать товары по категориям********************************/
    //Метод, проверяющий введенной с консоли категории товара (2 - Показать товары по категориям)
    public void checkCategory(String targetCategory) {
        GoodService goodService = new GoodService();
        List<Good> goodList = goodService.getGoodListFromFile();
        System.out.println();
        String categoryNotExist = null;
        for (Good good : goodList) {
            if (good.getCategory().equals(targetCategory)) {
                categoryNotExist = targetCategory;
            }
        }
        if (categoryNotExist == null) {
            System.out.println("Категории товара " + '\'' + targetCategory + '\'' + " не существует. Введите корректное наименование категории");
        } else showGoodsByCategory(targetCategory);
    }

    //Метод, выводящий товары определенной категории (2 - Показать товары по категориям)
    public void showGoodsByCategory(String targetCategory) {
        GoodService goodService = new GoodService();
        List<Good> goodList = goodService.getGoodListFromFile();
        System.out.println("Список товаров в категории " + targetCategory + ": ");
        for (Good good : goodList) {
            if (good.getCategory().equals(targetCategory)) {
                System.out.println("Товары в категории " + targetCategory + ": '\'" + good + '\'');
            }
        }
    }
    //*************************************************************************************************************** */


    /** *************** Реализация п.3 - Показать товары по стоимости и категориям (с сортировкой)**************/
    //Метод, выводящий товары сортированные по категории и затем по цене (3 - Показать товары по стоимости и категориям (с сортировкой))
    public void sortGoodByCategoryAndPrice() {
        GoodService goodService = new GoodService();
        List<Good> goodList;
//        System.out.println("Before sort: " + goodService.getGoodListFromFile().toString());
//        System.out.println("Before sort:");
//        showGoods();
//        System.out.println();

        goodList = goodService.getGoodListFromFile();
        goodList.sort(Comparator.comparing((Good good1) -> good1.getCategory())
                .thenComparing(good -> good.getPrice()));
        showGoods(goodList);
        }
    //** ************************************************************************************************************* */

    public void showGoods(List<Good> goodList) {
        System.out.println("Товары, сортированные по категории и затем по цене:");
        for (Good good : goodList) {
            System.out.println(good);
        }
    }

    /************************************** Реализация п.4 - Добавить товар **************************************/
    //Ввод товара и создание коллекции  (4 - Добавить товар)
    public Good goodEntry(int id) {

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
        writeGood(goodList);//вызов метода, сериализующего товар
        return goodNew; //нигде не используется
    }

    //Метод, добавляющий товар (4 - Добавить товар)
    public void addGood() {
        GoodService goodService = new GoodService();
        List<Good> goodExistList = goodService.getGoodListFromFile();
        if (goodExistList.isEmpty()) {
//            entryGood(goodExistList, path, 1);
            goodEntry(1);
        } else {
            Optional<Good> idGoodMax = findMaxGoodId(goodExistList);
            if (idGoodMax.isPresent()) {
                Good idMax = idGoodMax.get();
                System.out.println("idMax = " + idMax.getId());
                int idNext = idGoodMax.get().getId() + 1;
                System.out.println("Следующий id = " + idNext);

                Good goodNext = goodEntry(idNext);

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
                goodRepository.saveGood(goodList);
            }
        }
    }

    //Метод поиска максимального значения в списке
    private Optional<Good> findMaxGoodId(List<Good> goodList) {
        return goodList.stream().max(Comparator.comparing(good -> {
            return good.getId();
        }));
    }
    //*************************************************************************************************************** */


    /*** ******************************* Реализация п.5 - Обновить товар *******************************************/
    //Метод для выбора товара по id, изменения товара и записи изменений в файл
    public void updateGoodById() {

        List<Good> goodList = getGoodListFromFile();

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
//        message();

        writeGood(goodList); //Сохраняем изменения в файл
    }

    public void message() {
        System.out.println("\nИзменения сохранены.");
    }

    //Метод поиска товара по id
    public Good findById(List<Good> list, int id) {
        for (Good good : list) {
            if (good.getId() == id) return good;
        }
        return null;
    }

    //Метод, изменяющий товар
    public void changeGood(Good good) {
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
    //************************************************************************************************************* */


    /*********************************************** Реализация п. 6 ***********************************************/
    public void delGoodById() {//List<Good> goodList, String path
        GoodService goodService = new GoodService();
        List<Good> goodList;
        goodList = getGoodListFromFile();

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите id товара: ");
        int id = Integer.parseInt(scanner.nextLine());

        Good goodTemp = null;
        Good good = findById(goodList, id);
        if (good == null) {
            System.out.println("Товар не найден.");
        } else {
            List<Good> temp = new ArrayList<>();
            for (Good g : goodList) {
                if (g.getId() == id) {
                    temp.add(good);
                }
                goodTemp = good;
            }
            goodList.removeAll(temp);
            System.out.println("Список после удаления: " + goodList);
        }

        writeGood(goodList); //Сохраняем изменения в файл

        if (!(goodTemp == null)) {
            System.out.println("\nУдаление выполнено.");
        }
    }
    //*************************************************************************************************************/

}

/*
//****************************to Delete***********************************************
    //Пробный - For delete
    public void changeGoodOld(Good good, int id) {
        System.out.println("Наименование товара до изменения: " + '\'' + good.getName() + '\'');
        good.setName("Велосипед");
        System.out.println("Наименование товара после изменения: " + '\'' + good.getName() + '\'');
    }

    //Метод, выводящий товары определенного индентификатора - для удаления
    public void showGoodsById(int id) {
        GoodService goodService = new GoodService();
        List<Good> goodList;
        goodList = getGoodListFromFile();
        System.out.println("Товар c id " + id + ": ");
        for (Good good : goodList) {
            if (good.getId() == id) {
                System.out.println(good);
//                scaner(good, path);
            }
        }
    }

    //Метод scaner: для удаления
    public void scaner(Good good, String path) {
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
 */