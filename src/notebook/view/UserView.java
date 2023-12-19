package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;
import notebook.util.mapper.UserValidation;

import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com;

        while (true) {
            String command = prompt("Введите команду: ");
            com = Commands.valueOf(command);
            if (com == Commands.EXIT) return;
            switch (com) {
                case CREATE -> {
                    User u = createUser();
                    userController.saveUser(u);
                }
                case READ -> {
                    String id = prompt("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case READ_ALL -> System.out.println(userController.readAll());
                case UPDATE -> {
                    String userId = prompt("Enter user id: ");
                    userController.updateUser(userId, createUser());}
                case DELETE -> {
                    String idUser = prompt("Enter user id: ");
                    userController.deleteUser(idUser);
                                   }
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private User createUser() {
        UserValidation userValidation = new UserValidation();

        String firstName = prompt("Имя: ");
        String lastName = prompt("Фамилия: ");
        String phone = prompt("Номер телефона: ");
        User user = new User(firstName, lastName, phone);
        if (userValidation.valid(user)){
            return user;
        } else {
            throw new IllegalArgumentException("Введены некорректные данные");
        }
    }
}
