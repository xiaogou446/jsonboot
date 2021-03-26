package com.df.demo;

import com.df.demo.entity.User;
import com.df.jsonboot.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @Qualifier("TeacherServiceImpl")
    @Autowired
    Service service;

    @GetMapping("/hello")
    public void hello(){
        System.out.println(userService.getString());
    }

    @GetMapping(value = "/helloImpl")
    public void helloImpl(){
        System.out.println(service.handler());
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

    @PostMapping(value = "/testHttpPost")
    public String testHttpPost(@RequestBody User user, String alex){
        System.out.println(user);
        System.out.println(alex);
        return "success";
    }

    @GetMapping(value = "/exo/{name}")
    public String testPathVariable(@PathVariable("name") String name){
        System.out.println(name);
        return name;
    }

}
