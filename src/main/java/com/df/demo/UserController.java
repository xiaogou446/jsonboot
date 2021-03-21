package com.df.demo;

import com.df.jsonboot.annotation.GetMapping;
import com.df.jsonboot.annotation.PostMapping;
import com.df.jsonboot.annotation.RestController;

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
    public void goodThis(){

    }


}
