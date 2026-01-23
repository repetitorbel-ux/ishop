package ishop;

import ishop.repository.GoodRepository;
import ishop.repository.UserRepository;
import ishop.service.GoodService;
import ishop.service.UserService;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();
        GoodRepository goodRepository = new GoodRepository();

        UserService userService = new UserService(userRepository);// Передаём репозиторий, с которым UserService будет работать
        GoodService goodService = new GoodService(goodRepository);

        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(userService, goodService, scanner);
        menu.baseMenu();

    }
}
