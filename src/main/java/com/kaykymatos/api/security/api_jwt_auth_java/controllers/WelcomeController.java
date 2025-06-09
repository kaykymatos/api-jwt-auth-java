package com.kaykymatos.api.security.api_jwt_auth_java.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping
    public String welcome() {
        return "Welcome";
    }

    @GetMapping("/users")
    public String users() {
        return "Authorized";
    }
    @GetMapping("/managers")
    public String managers() {
        return "Authorized manager";
    }
}
