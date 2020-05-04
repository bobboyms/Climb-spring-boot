package br.com.teste.climbteste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.climb.climbspring")
public class ClimbtesteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClimbtesteApplication.class, args);
    }

}
