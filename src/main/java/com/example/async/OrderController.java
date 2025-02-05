package com.example.async;



import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/getProduct", produces = "text/event-stream")
    public Flux<Item> getAllProducts() {
        return orderService.fetchAndProcessProducts().delayElements(Duration.ofSeconds(10)).log();
    }
}

