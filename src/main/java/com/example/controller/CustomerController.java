package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestController
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/info")
    public ResponseEntity<String> getInfo(){
        logger.info("Application is working fine........");
        return ResponseEntity.ok( "Kubernetes configuration is done");
    }
}
