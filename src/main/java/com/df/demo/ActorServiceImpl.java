package com.df.demo;

import com.df.jsonboot.annotation.Component;

@Component
public class ActorServiceImpl implements Service{
    @Override
    public String handler() {
        return "这是接口实现，笨蛋";
    }
}
