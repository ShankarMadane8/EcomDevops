package com.example.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@EnableAsync
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final WebClient.Builder webClientBuilder;

    public OrderService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;

    }

    // Fetch product data asynchronously
    public Flux<Item> fetchAndProcessProducts() {
        Flux<Item> productDetails = webClientBuilder.build()
                .method(HttpMethod.GET)
                .uri("http://localhost:8182/test")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToFlux(Item.class);

        // Process each item asynchronously
        productDetails
                .doOnTerminate(() -> System.err.println("All product processing completed on thread: " + Thread.currentThread().getName()))
                .subscribe(
                        item -> {
                            System.err.println("Fetched item: " + item + " on thread: " + Thread.currentThread().getName());
                            processItemAsync(item); // Call async processing
                        },
                        error -> System.err.println("Error occurred: "+error.getMessage())
                );
        return productDetails;
    }

    // Process an item asynchronously
    @Async
    public void processItemAsync(Item item) {
        try {
            Thread.sleep(5000);  // Simulate processing delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.err.println("Processed item: " + item + " on thread: " + Thread.currentThread().getName());
    }
}
