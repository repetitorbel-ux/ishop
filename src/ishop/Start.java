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

        //Передаём репозитории, с которыми  будет работать слой Service
        UserService userService = new UserService(userRepository);
        GoodService goodService = new GoodService(goodRepository);

        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(userService, goodService, scanner);
        menu.baseMenu();

    }
}
