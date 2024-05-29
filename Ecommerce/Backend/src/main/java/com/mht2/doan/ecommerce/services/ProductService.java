package com.mht2.doan.ecommerce.services;

import com.mht2.doan.ecommerce.dtos.ProductDto;
import com.mht2.doan.ecommerce.models.Product;
import com.mht2.doan.ecommerce.repositories.ProductRepository;
import com.mht2.doan.ecommerce.utils.ImageUpload;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    private final ImageUpload imageUpload;

    public List<Product> findALl() {
        return productRepository.findAll();
    }

    public List<ProductDto> products() {
        return transferData(productRepository.findAll());
    }

    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        Product product = new Product();
        try {
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                imageUpload.uploadFile(imageProduct);
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCostPrice(productDto.getCostPrice());
            product.setCategory(productDto.getCategory());
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).get();
    }

    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getReferenceById(productDto.getId());
            if (imageProduct.getBytes().length > 0) {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setImage(productUpdate.getImage());
                } else {
                    imageUpload.uploadFile(imageProduct);
                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setId(productUpdate.getId());
            productUpdate.setName(productDto.getName());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setCostPrice(productDto.getCostPrice());
            productUpdate.setSalePrice(productDto.getSalePrice());
            productUpdate.setCurrentQuantity(productDto.getCurrentQuantity());
            return productRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    public ProductDto getById(Integer id) {
        ProductDto productDto = new ProductDto();
        Product product = this.findById(id);
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        return productDto;
    }
    
    private List<ProductDto> transferData(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setDescription(product.getDescription());
            productDto.setImage(product.getImage());
            productDto.setCategory(product.getCategory());
            productDtos.add(productDto);
        }
        return productDtos;
    }
}
