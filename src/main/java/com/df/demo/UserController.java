package com.df.demo;

import com.df.jsonboot.annotation.GetMapping;
import com.df.jsonboot.annotation.PostMapping;
import com.df.jsonboot.annotation.RequestParam;
import com.df.jsonboot.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController(value = "/user")
public class UserController {

    @GetMapping("/hello")
    @PostMapping("/helloWorld")
    public void hello(){

    }

    @PostMapping("/hi")
    public void hi(){

    }

    @GetMapping("/goodThis")
    public String goodThis(@RequestParam(value = "name") String name, int age){
        System.out.println(name);
        System.out.println(age);
        List<String> list = new ArrayList<>();
        list.add("3");
        list.add("4");
        list.add("5");
        return "323414";
    }


}
