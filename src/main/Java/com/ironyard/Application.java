package com.ironyard;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//this need to be above all the packages and classes that use it. It scans below itself
/*
        Application
            |
            v
         Package
            |
            v
          Class
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
