package com.irem.gcs.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        return "hello," + name;
    }

    @PostMapping("/user")
    public String postUser(@RequestBody Map<String, Integer> user){
        Integer iremAge = user.get("irem");
        return "Irem's age is: " + iremAge;
    }

}
