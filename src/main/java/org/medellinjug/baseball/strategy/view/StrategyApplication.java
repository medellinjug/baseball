package org.medellinjug.baseball.strategy.view;


import java.util.Optional;
import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Created by Hilmer on 25/06/17.
 * MedellinJUG.org
 */
@SpringBootApplication
public class StrategyApplication {

    // Get PORT and HOST from Environment or set default
    public static final Optional<String> host;
    public static final Optional<String> port;
    public static final Properties myProps = new Properties();

    static {
        host = Optional.ofNullable(System.getenv("HOSTNAME"));
        port = Optional.ofNullable(System.getenv("PORT"));
    }

    public static void main(String[] args) {
        // Set properties

        myProps.setProperty("server.address", host.orElse("localhost"));
        myProps.setProperty("server.port", port.orElse("8080"));

        SpringApplication app = new SpringApplication(StrategyApplication.class);
        app.setDefaultProperties(myProps);
        app.run(args);

        System.out.println("http://localhost:8080/index.html");

    }
}
