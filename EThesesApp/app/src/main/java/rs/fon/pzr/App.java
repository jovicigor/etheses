package rs.fon.pzr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "rs.fon.pzr")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
