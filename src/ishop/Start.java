package ishop;

import ishop.service.GoodService;
import ishop.service.UserService;

import java.util.Scanner;

public class Start {

    public static void main(String[] args) {

        UserService userService = new UserService();
        GoodService goodService = new GoodService();
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(userService, goodService, scanner);
        menu.baseMenu();
    }


}
