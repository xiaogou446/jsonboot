package com.df.demo.impl;

import com.df.demo.interfaces.ITestB;
import com.df.demo.interfaces.ITestC;
import com.df.jsonboot.annotation.ioc.Autowired;
import com.df.jsonboot.annotation.ioc.Component;

@Component
public class ITestBImpl implements ITestB {

    @Autowired
    ITestC iTestC;

    @Override
    public void testB() {
        System.out.println("ITestB");
    }
}
