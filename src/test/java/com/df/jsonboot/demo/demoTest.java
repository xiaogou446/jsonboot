package com.df.jsonboot.demo;

import com.df.jsonboot.annotation.RestController;
import com.df.jsonboot.scanners.AnnotatedClassScanner;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class demoTest {

    @Test
    public void test(){
        AnnotatedClassScanner annotatedClassScanner = new AnnotatedClassScanner();
        Set<Class<?>> scan = annotatedClassScanner.scan("com.df.jsonboot.demo", RestController.class);
        System.out.println(scan);
    }

}
