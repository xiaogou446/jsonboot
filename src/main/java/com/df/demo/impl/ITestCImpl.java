package com.df.demo.impl;

import com.df.demo.interfaces.ITestA;
import com.df.demo.interfaces.ITestC;
import com.df.jsonboot.annotation.ioc.Autowired;
import com.df.jsonboot.annotation.ioc.Component;

@Component
public class ITestCImpl implements ITestC {

    @Autowired
    ITestA iTestA;

    @Override
    public void testC() {
        System.out.println("ITestC");
    }
}
