package com.gigaspaces.monitor.website.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/welcome")
public class HelloWorld {

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String printWelcome() {
        return "hello world!";

    }

}