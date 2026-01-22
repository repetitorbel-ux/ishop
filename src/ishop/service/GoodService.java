package ishop.service;

import ishop.entity.Good;
import ishop.repository.GoodRepository;

import java.util.*;

public class GoodService {
    private final GoodRepository goodRepository;

    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    /******************************* Методы для работы со слоем Repository *******************************/
    //Метод, получающий список уже существующих товаров
    public List<Good> getGoodListFromFile() {
        List<Good> goodExistList = goodRepository.getAllGoods();//В переменную типа List, в которую десериализуем файл с товарами
        return goodExistList;
    }

    //Метод, сериализующий товар
    public void writeGood(List<Good> goodList) {
        goodRepository.saveGood(goodList);
    }
    //***************************************************************************************************************/


    /******************************** Блок для получения списка товаров *******************************/
    //Метод, получающий список всех товаров (1 - Показать список всех товаров)
    public List<Good> getGoods() {

        List<Good> goodList = getGoodListFromFile();
        return goodList;
    }

    //Метод, получающий список товаров заданной категории
    public Optional<List<Good>> getGoodsByCategory(String category) {

        Optional<List<Good>> goodListByCategory = Optional.empty();
        List<Good> goodList = getGoodListFromFile();
        List<Good> goodListNew = new ArrayList<>();

        for (Good good : goodList) {
            if (good.getCategory().equals(category)) {
                goodListNew.add(good);
                goodListByCategory = Optional.of(goodListNew);
            }
        }
        return goodListByCategory;
    }

    //Метод, получающий список товаров сортированных по категории и затем по цене (3 - Показать товары по стоимости и категориям (с сортировкой))
        public Optional<List<Good>> getGoodsByCategoryAndPrice() {

        List<Good> goodList = getGoodListFromFile();

        goodList.sort(Comparator.comparing((Good good1) -> good1.getCategory())
                .thenComparing(good2 -> good2.getPrice()));
        return Optional.of(goodList);
    }
    //**************************************************************************************************************** */


    /************************************** Реализация п.4 - Добавить товар **************************************/
        //Метод, добавляющий товар (4 - Добавить товар)
    public void addGood(int id, String goodname, String goodCode, String goodBrand, String goodCategory, int goodPrice, int goodAgeLimit) {
        List<Good> goodExistList = getGoodListFromFile();

        //Создание коллекции товаров
        List<Good> goodList = new ArrayList<>();

        //Создаем новый товар
        Good goodNew = new Good(id, goodname, goodCode, goodBrand, goodCategory, goodPrice, goodAgeLimit);

        //Добавление в коллекцию списка из существующих товаров
        for (int i = 0; i < goodExistList.size(); i++) {
            goodList.add(goodExistList.get(i));
        }

        //Добавление нового товара в список
        goodList.add(goodNew);

        goodRepository.saveGood(goodList);

    }

    //Метод поиска максимального значения в списке
    private Optional<Good> findMaxGoodId(List<Good> goodList) {
        return goodList.stream().max(Comparator.comparing(good -> {
            return good.getId();
        }));
    }

    public Optional<Good> findMaxGoodId2() {
        List<Good> goodExistList = getGoodListFromFile();
        return goodExistList.stream().max(Comparator.comparing(good -> {
            return good.getId();
        }));
    }
    //*************************************************************************************************************** */


    /*** ******************************* Реализация п.5 - Обновить товар *******************************************/
    //Метод для выбора товара по id и вызова меню для обновления товара
    public void callUpdateGoodById() {

//        Menu menu = new Menu();
//        GoodRepository goodRepository = new GoodRepository();
//
//        int goodId = Integer.parseInt(menu.askValue("id товара"));
//        Good good = goodRepository.findById(goodId);
//
//        if (good == null) {
//            System.out.println("Товар не найден. Повторите ввод id");
//            return;
//        }
//
//        menu.menuCurrentGood(good);
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
//        Menu menu = new Menu();
//
//        int id = Integer.parseInt(menu.askValue(" id товара для удаления"));
//
//        Good goodTemp = null;
//        Good goodFound = goodRepository.findById(id);
//        if (goodFound == null) {
//            System.out.println("Товар не найден.");
//        } else {
//            List<Good> temp = new ArrayList<>();
//            for (Good good : goodList) {
//                if (good.getId() == id) {
//                    temp.add(goodFound);
//                }
//                goodTemp = goodFound;
//            }
//            System.out.print("\nУдаляемый товар: " + goodTemp + "\n");
//            goodList.removeAll(temp);
//        }
//
//        writeGood(goodList); //Сохраняем изменения в файл
//
//        if (!(goodTemp == null)) {
//            System.out.print("\nУдаление выполнено. " );
//            showGoods();
//        }
    }
    //*************************************************************************************************************/

}
