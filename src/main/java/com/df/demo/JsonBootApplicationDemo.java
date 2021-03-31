package com.df.demo;

import com.df.jsonboot.JsonBootApplication;
import com.df.jsonboot.annotation.boot.ComponentScan;

@ComponentScan("com.df.demo")
public class JsonBootApplicationDemo {
    public static void main(String[] args) {
        JsonBootApplication.run(JsonBootApplicationDemo.class, args);
    }
}
