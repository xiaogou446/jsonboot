package com.df.demo;

import com.df.jsonboot.annotation.ioc.Component;

@Component
public class UserService {

    public String getString(){
        return "我很爱写代码";
    }

}
