package com.df.demo;

import com.df.jsonboot.annotation.ioc.Component;

@Component
public class ActorServiceImpl implements Service{
    @Override
    public String handler() {
        return "这是主播实现类，笨蛋";
    }
}
