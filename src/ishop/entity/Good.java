package ishop.entity;

import java.io.Serializable;
import java.util.Objects;
/*Пример набора полей для товара Good: id, name, code, brand (Филипс, iphone, Huawei и т.д.), category (Телефоны, стиральные машины и т.д.), price, ограничение по возрасту */
public class Good implements Serializable {
    private int id;
    private String name;
    private String code;
    private String brand;
    private String category;
    private int price;
    private int ageLimit;

    //Конструктор

    public Good(int id, String name, String code, String brand, String category, int price, int ageLimit) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.ageLimit = ageLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Good good)) return false;
        return id == good.id && price == good.price && ageLimit == good.ageLimit && Objects.equals(name, good.name) && Objects.equals(code, good.code) && Objects.equals(brand, good.brand) && Objects.equals(category, good.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, brand, category, price, ageLimit);
    }

    @Override
    public String toString() {
        return  '{' + "Индентификатор товара: '" + id + '\'' + ", Название товара: '" + name + '\'' +
                ", Код товара: '" + code + '\'' +
                ", Бренд : '" + brand + '\'' +
                ", Категория '" + category + '\'' +
                ", Цена " + price +
                ", Возрастные ограничения: '" + ageLimit + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int isAgeLimit() {
        return ageLimit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }
}
