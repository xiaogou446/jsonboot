package com.df.demo;

import com.df.jsonboot.annotation.Component;

@Component("TeacherServiceImpl")
public class TeacherServiceImpl implements Service{
    @Override
    public String handler() {
        return "这是教师实现类";
    }
}
