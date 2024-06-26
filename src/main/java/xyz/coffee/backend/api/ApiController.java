package xyz.coffee.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class ApiController {
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
