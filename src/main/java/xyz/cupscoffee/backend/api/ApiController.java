package xyz.cupscoffee.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {
    // Hello world
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
