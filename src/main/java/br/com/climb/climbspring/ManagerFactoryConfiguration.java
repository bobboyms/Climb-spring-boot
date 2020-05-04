package br.com.climb.climbspring;

import br.com.climb.core.ClimbORM;
import br.com.climb.core.interfaces.ManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerFactoryConfiguration {

    @Bean
    public ManagerFactory getManagerFactory() {
        return ClimbORM.createManagerFactory("climb.properties");
    }

}
