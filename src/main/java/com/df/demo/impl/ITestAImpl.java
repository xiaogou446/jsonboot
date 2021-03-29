package com.df.demo.impl;

import com.df.demo.interfaces.ITestA;
import com.df.demo.interfaces.ITestB;
import com.df.jsonboot.annotation.ioc.Autowired;
import com.df.jsonboot.annotation.ioc.Component;

@Component
public class ITestAImpl implements ITestA {

    @Autowired
    ITestB iTestB;

    @Override
    public void testA() {
        System.out.println("ITestA");
    }
}
