package com.df.demo.controller;


import com.df.demo.interfaces.ITestA;
import com.df.demo.interfaces.ITestB;
import com.df.demo.interfaces.ITestC;
import com.df.jsonboot.annotation.ioc.Autowired;
import com.df.jsonboot.annotation.springmvc.GetMapping;
import com.df.jsonboot.annotation.springmvc.RestController;

@RestController("/test")
public class TestController {

    @Autowired
    private ITestA testA;

    @Autowired
    private ITestB testB;

    @Autowired
    private ITestC testC;

    @GetMapping("/test")
    public void test(){
        testA.testA();
        testB.testB();
        testC.testC();
    }
}
