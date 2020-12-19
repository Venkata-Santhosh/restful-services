package com.cocument.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocument.models.UserDetails;

@RestController
public class HelloWorldController {

    // URI /helloworld
    // which method we need to access GET/POST/PUT/DELETE
    //
    // @RequestMapping(method = RequestMethod.GET, path = "/helloworld")

    @GetMapping(path = "/helloworld")
    public String helloWorld() {
        return "Hello World";
    }


    @GetMapping("helloworld-bean")
    public UserDetails helloWorldBean(){
        return new UserDetails("Santhosh", "Piduri", "Burlington");
    }
}

