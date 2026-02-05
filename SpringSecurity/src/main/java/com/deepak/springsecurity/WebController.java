package com.deepak.springsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/")
    public String home() {
        return "<h1>Welcome Home!</h1><p>This page is open to everyone.</p>";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "<h1>Welcome to your Dashboard!</h1><p>This page should be protected.</p>";
    }
}
