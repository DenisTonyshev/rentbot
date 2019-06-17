package telegrammrentalbot.rentbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class RentbotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(RentbotApplication.class, args);
    }

}
