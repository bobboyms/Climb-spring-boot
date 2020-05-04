package br.com.climb.climbspring;

import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.exception.SgdbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClimbConnectionConfiguration {

    @Bean
    @Autowired
    public ClimbConnection getConnection(ManagerFactory managerFactory) throws SgdbException {
        return managerFactory.getConnection();
    }

}
