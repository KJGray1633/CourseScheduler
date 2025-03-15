package com.java.firebase;

import com.java.model.User;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class FirebaseApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ApplicationContext context = SpringApplication.run(FirebaseApplication.class, args);
        FirebaseService firebaseService = context.getBean(FirebaseService.class);

        User user = firebaseService.getUserByUid(0);
        if (user != null) {
            System.out.println("com.java.model.User found: " + user);
        } else {
            System.out.println("com.java.model.User not found");
        }
    }
}