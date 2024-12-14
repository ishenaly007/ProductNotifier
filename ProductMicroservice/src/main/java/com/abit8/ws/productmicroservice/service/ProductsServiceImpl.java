package com.abit8.ws.productmicroservice.service;

import com.abit.core.ProductCreatedEvent;
import com.abit8.ws.productmicroservice.service.dto.ProductCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductsServiceImpl implements ProductsService {
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ProductsServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(ProductCreateDto productCreateDto) throws ExecutionException, InterruptedException {
        //TODO create db
        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
                productId,
                productCreateDto.getTitle(),
                productCreateDto.getPrice(),
                productCreateDto.getQuantity()
        );

        var future = kafkaTemplate
                .send("product-created-events-topic", productId, productCreatedEvent).get();
//        future.whenComplete((result, exception) -> {
//            if (exception != null) {
//                log.error("Failed to sent message {}", exception.getMessage());
//            } else {
//                log.info("Successfully sent message {}", result.getRecordMetadata());
//            }
//        });
        log.info("Result metadata {}", future.getRecordMetadata().timestamp());

        log.info("Successfully sent message {}", productId);
        return productId;
    }
}