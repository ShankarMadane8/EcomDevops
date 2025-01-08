package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;


@RestController
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/info")
    public ResponseEntity<String> getInfo(){
        logger.info("Application is working fine........");
        return ResponseEntity.ok( "Kubernetes configuration is done");
    }

    @GetMapping("/getData")
    public ResponseEntity<?> getData(){
        HashMap<String,String> data= new HashMap<>();
        data.put("name","kiran");
        data.put("email","kiran@gmial.com");
        data.put("phone","8806325334");
        logger.info("data: {}", data);
        return ResponseEntity.ok( data);
    }

    @GetMapping("/gedata/1")
    public ResponseEntity<?> getDataUser(){
        HashMap<String,String> data= new HashMap<>();
        data.put("name","Shankar");
        data.put("email","SHankar@gmial.com");
        data.put("phone","8806325334");
        logger.info("data: {}", data);
        return ResponseEntity.ok( data);
    }
}
