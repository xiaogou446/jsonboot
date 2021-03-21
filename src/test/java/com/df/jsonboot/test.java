package com.df.jsonboot;

import com.df.jsonboot.annotation.RestController;
import com.df.jsonboot.core.scanners.AnnotatedClassScanner;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class test {

    @Test
    public void test(){
        AnnotatedClassScanner annotatedClassScanner = new AnnotatedClassScanner();
        Set<Class<?>> scan = annotatedClassScanner.scan("com.df.jsonboot.core", RestController.class);
        System.out.println(scan);
    }


}
