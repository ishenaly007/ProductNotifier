package com.abit8.ws.productmicroservice.http.controller.rest;

import com.abit8.ws.productmicroservice.service.ProductsService;
import com.abit8.ws.productmicroservice.service.dto.ProductCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private ProductsService productsService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductCreateDto productCreateDto) {
        String productId = null;
        try {
            productId = productsService.createProduct(productCreateDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(new Date(), e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
