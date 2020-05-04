package br.com.teste.climbteste.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ClimbController {

    @Autowired
    private ClimbService climbService;

    @GetMapping
    public String helloWord() {
        return "Ola mundo";
    }

}
