package project;

import org.springframework.boot.CommandLineRunner;
import project.service.UserService;

public class ShoeShopApplicationInit implements CommandLineRunner {

    private final UserService userService;


    public ShoeShopApplicationInit(UserService userService) {
        this.userService = userService;

    }

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();

    }
}
