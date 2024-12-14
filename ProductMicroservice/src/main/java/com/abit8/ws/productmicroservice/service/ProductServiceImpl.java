package com.abit8.ws.productmicroservice.service;

import com.abit.core.ProductCreatedEvent;
import com.abit8.ws.productmicroservice.service.dto.CreateProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {
    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger log = LoggerFactory.getLogger(Logger.class);

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductDto productDto) throws ExecutionException, InterruptedException {
        //TODO save db
        String productId = UUID.randomUUID().toString();
        ProductCreatedEvent createdEvent = new ProductCreatedEvent(productId, productDto.getTitle(),
                productDto.getPrice(), productDto.getQuantity());

        SendResult<String, ProductCreatedEvent> result =
                kafkaTemplate.send("product-created-events-topic", productId, createdEvent).get();

        log.info("Topic : {}", result.getRecordMetadata().topic());
        log.info("Offset : {}", result.getRecordMetadata().offset());
        log.info("Partition : {}", result.getRecordMetadata().partition());

        //асинхронщина
//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate.
//                send("product-created-events-topic", productId, createdEvent);

//        future.whenComplete((result, exception) -> {
//            if (exception != null) {
//                log.error("Error sending event: {}", exception.getMessage());
//            } else {
//                log.info("Successfully sending event: {}", result.getRecordMetadata());
//            }
//        });

        log.info("Return : {}", productId);

        return productId;
    }
}