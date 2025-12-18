package ishop;

public class Readme {
    /*
    public void writeGood() сделать не void, а List<Good>???
    private static Optional<Good> findMaxGoodId() - сделать not static?
     */


    /*
    1) сделать проверку возраста
    2) правильные exception
    3) exception в других проверках?


    5)check pass != null

    6) авторизация: введен не правильный логин, есть сообщение, но дальше запрашивает пароль и после ввода - exception
    ввел проверку, вроде работает

    7) при вводе не правильного пароля пишет: "Введен не верный пароль, повторите ввод пароля", и загружает меню админа,
    при этом пользователь не админ
    исп-ть while?
    Введите login user2
Введите пароль 333
Введен не верный пароль, повторите ввод пароля
Введите пароль 3333
Показывает меню админа

    8) объединить askPassword() и checkPassword()

    9) Пользователя с логином 'iser' не существует. Пройдите регистрацию
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "passFromUser" is null

    8)есть логика в baseMenu() - это норм?



     */
}
