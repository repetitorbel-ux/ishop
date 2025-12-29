package ishop.service;

import ishop.Menu;
import ishop.entity.Good;
import ishop.entity.User;
import ishop.repository.GoodRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GoodService {

    /******************************* Методы слоя Repository *******************************/
    //Метод, получающий список уже существующих товаров
    public List<Good> getGoodListFromFile() {
        GoodRepository goodRepository = new GoodRepository();
        List<Good> goodExistList = goodRepository.getAllGoods();//В переменную типа List, в которую десериализуем файл с товарами
        return goodExistList;
    }

    //Метод, сериализующий товар
    public void writeGood(List<Good> goodList) {
        GoodRepository goodRepository = new GoodRepository();
        goodRepository.saveGood(goodList);
    }
    //***************************************************************************************************************/


    /******************************** Реализация п.1 - Показать список всех товаров *******************************/
    //Метод, выводящий все товары (1 - Показать список всех товаров)
    public void showGoods() {
        GoodService goodService = new GoodService();
        List<Good> goodList = goodService.getGoodListFromFile();
        System.out.println("Актуальный писок товаров:");
        for (Good good : goodList) {
            System.out.println(good);
        }
    }
    //***************************************************************************************************************/


    /******************************** Реализация п.2 - Показать товары по категориям ********************************/
    //Метод, проверяющий введенную с консоли категорию товара (2 - Показать товары по категориям)
    public void checkCategory(String targetCategory) {
        GoodService goodService = new GoodService();
        List<Good> goodList = goodService.getGoodListFromFile();

        String categoryNotExist = null;
        for (Good good : goodList) {
            if (good.getCategory().equals(targetCategory)) {
                categoryNotExist = targetCategory;
            }
        }
        if (categoryNotExist == null) {
            System.out.println("\nКатегории товара " + '\'' + targetCategory + '\'' + " не существует. Введите корректное наименование категории");
        } else showGoodsByCategory(targetCategory);
    }

    //Метод, выводящий товары определенной категории (2 - Показать товары по категориям)
    public void showGoodsByCategory(String targetCategory) {
        GoodService goodService = new GoodService();
        List<Good> goodList = goodService.getGoodListFromFile();

        for (Good good : goodList) {
            if (good.getCategory().equals(targetCategory)) {
                System.out.println("Товары в категории " + targetCategory + ": " + good);
            }
        }
    }
    //*************************************************************************************************************** */


    /** *************** Реализация п.3 - Показать товары по стоимости и категориям (с сортировкой)**************/
    //Метод, выводящий товары сортированные по категории и затем по цене (3 - Показать товары по стоимости и категориям (с сортировкой))
    public void sortGoodByCategoryAndPrice() {
        GoodService goodService = new GoodService();
        List<Good> goodList;

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
    //Ввод товара и создание коллекции (4 - Добавить товар)
    public Good goodEntry(int id) {

        Menu menu = new Menu();

        String nameGood = menu.askValue(" название товара");

        String codeGood = menu.askValue(" код товара");

        String brandGood = menu.askValue(" бренд товара");

        String categoryGood = menu.askValue(" категорию товара");

        int priceGood = Integer.parseInt(menu.askValue(" цену товара"));

        int ageLimit = Integer.parseInt(menu.askValue(" ограничение по возрасту"));

        //Создание нового товара и коллекции товаров
        Good goodNew = new Good(id, nameGood, codeGood, brandGood, categoryGood, priceGood, ageLimit);

        List<Good> goodList = new ArrayList<>();//пустая коллекция

        goodList.add(goodNew);//добавление нового (введенного) товара в коллекцию

        System.out.println("\nДобавленный товар: " + goodList);

        writeGood(goodList);//вызов метода, сериализующего товар

        return goodNew;
    }

    //Метод, добавляющий товар (4 - Добавить товар)
    public void addGood() {
        GoodService goodService = new GoodService();
        List<Good> goodExistList = goodService.getGoodListFromFile();

        if (goodExistList.isEmpty()) {
            goodEntry(1);
        } else {
            Optional<Good> idGoodMax = findMaxGoodId(goodExistList);
            if (idGoodMax.isPresent()) {
                Good idMax = idGoodMax.get();
                int idNext = idGoodMax.get().getId() + 1;
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
    //Метод для выбора товара по id и вызова меню для обновления товара
    public void callUpdateGoodById() {

        Menu menu = new Menu();
        GoodRepository goodRepository = new GoodRepository();

        int goodId = Integer.parseInt(menu.askValue("id товара"));
        Good good = goodRepository.findById(goodId);

        if (good == null) {
            System.out.println("Товар не найден. Повторите ввод id");
            return;
        }

        menu.menuCurrentGood(good);
    }

    //Метод изменяющий (обновляющий) товар (String value)
    public void changeCurrentGood(Good goodForChange, String value, String valueChoice) {
        List<Good> goodList = getGoodListFromFile();

        int currentId = goodForChange.getId();
        String choice = valueChoice;
        switch (choice) {
            case "1":
                for (Good good : goodList) {
                    if (currentId == good.getId()) {
                        good.setName(value);
                        break;
                    }
                }
                goodForChange.setName(value);//сохраняем изменения в объект для последующего вывода изменений
                writeGood(goodList);
                break;
            case "2":
                for (Good good : goodList) {
                    if (currentId == good.getId()) {
                        good.setCode(value);
                        break;
                    }
                }
                goodForChange.setCode(value);
                writeGood(goodList);
                break;
            case "3":
                for (Good good : goodList) {
                    if (currentId == good.getId()) {
                        good.setBrand(value);
                        break;
                    }
                }
                goodForChange.setBrand(value);
                writeGood(goodList);
                break;
            case "4":
                for (Good good : goodList) {
                    if (currentId == good.getId()) {
                        good.setCategory(value);
                        break;
                    }
                }
                goodForChange.setCategory(value);
                writeGood(goodList);
                break;
            case "0":
                System.out.println("Выход из меню");
                return;
            default:
                System.out.println("Неверный ввод");
        }
    }

    //Метод изменяющий (обновляющий) товар (int value)
    public void changeCurrentGoodInt(Good goodForChange, int value, String valueChoice) {
        List<Good> goodList = getGoodListFromFile();

        int currentId = goodForChange.getId();
        switch (valueChoice) {
            case "5":
                for (Good good : goodList) {
                    if (currentId == good.getId()) {
                        good.setPrice(value);
                        break;
                    }
                }
                goodForChange.setPrice(value);
                writeGood(goodList);
                break;
            case "6":
                for (Good good : goodList) {
                    if (currentId == good.getId()) {
                        good.setAgeLimit(value);
                        break;
                    }
                }
                goodForChange.setAgeLimit(value);
                writeGood(goodList);
                break;
            case "0":
                System.out.println("Выход из меню");
                return;
            default:
                System.out.println("Неверный ввод");
        }
    }
    //************************************************************************************************************* */


    /*********************************************** Реализация п. 6 ***********************************************/
    //Метод, удаляющий товар
    public void delGoodById() {
        GoodRepository goodRepository = new GoodRepository();
        List<Good> goodList = getGoodListFromFile();
        Menu menu = new Menu();

        int id = Integer.parseInt(menu.askValue(" id товара для удаления"));

        Good goodTemp = null;
        Good goodFound = goodRepository.findById(id);
        if (goodFound == null) {
            System.out.println("Товар не найден.");
        } else {
            List<Good> temp = new ArrayList<>();
            for (Good good : goodList) {
                if (good.getId() == id) {
                    temp.add(goodFound);
                }
                goodTemp = goodFound;
            }
            System.out.print("\nУдаляемый товар: " + goodTemp + "\n");
            goodList.removeAll(temp);
        }

        writeGood(goodList); //Сохраняем изменения в файл

        if (!(goodTemp == null)) {
            System.out.print("\nУдаление выполнено. " );
            showGoods();
        }
    }
    //*************************************************************************************************************/

}
