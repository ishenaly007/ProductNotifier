package com.abit8.ws.productmicroservice.service;

import com.abit8.ws.productmicroservice.service.dto.CreateProductDto;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductDto productDto) throws ExecutionException, InterruptedException;
}
