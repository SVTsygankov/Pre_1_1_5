package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Иван", "Иванов", (byte) 20);
        System.out.println("User с именем – Иван добавлен в базу данных");
        userService.saveUser("Сидор", "Сидоров2", (byte) 25);
        System.out.println("User с именем – Сидор добавлен в базу данных");
        userService.saveUser("Петр", "Петров", (byte) 31);
        System.out.println("User с именем – Петр добавлен в базу данных");
        userService.saveUser("Не_козел", "Козлов", (byte) 38);
        System.out.println("User с именем – Не_козел добавлен в базу данных");

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
