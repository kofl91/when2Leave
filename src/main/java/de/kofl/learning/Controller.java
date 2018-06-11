package de.kofl.learning;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.Callable;

@org.springframework.stereotype.Controller
public class Controller {

    private String hallo;

    @RequestMapping("/")
    public String index() {
        String value = "Hans";
        return "index";
    }
}
