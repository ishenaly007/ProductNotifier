package com.abit8.ws.productmicroservice.service;

import com.abit8.ws.productmicroservice.service.dto.ProductCreateDto;

import java.util.concurrent.ExecutionException;

public interface ProductsService {
    String createProduct(ProductCreateDto productCreateDto) throws ExecutionException, InterruptedException;
}
