package ishop.constants;

public enum Role {

    //Статические переменные
    ADMIN ("Администратор"),
    USER ("Пользователь");

    //Объявляем поле
    private String nameOfRole;

    Role(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

    public String getNameOfRole() {
        return nameOfRole;
    }
}
