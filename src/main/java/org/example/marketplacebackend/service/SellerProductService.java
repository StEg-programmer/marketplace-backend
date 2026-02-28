package org.example.marketplacebackend.service;


import jakarta.transaction.Transactional;
import org.example.marketplacebackend.domain.catalog.Category;
import org.example.marketplacebackend.domain.catalog.Product;
import org.example.marketplacebackend.domain.catalog.ProductBuilder;
import org.example.marketplacebackend.domain.catalog.ProductImage;
import org.example.marketplacebackend.dto.CreateProductRequest;
import org.example.marketplacebackend.repository.CategoryRepository;
import org.example.marketplacebackend.repository.ProductImageRepository;
import org.example.marketplacebackend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.util.List;

@Service
public class SellerProductService {

    private final CategoryRepository _categoryRepository;
    private final ProductRepository _productRepository;
    private final ProductImageRepository _productImageRepository;
    private final FileStorageService _fileStorageService;

    public SellerProductService(CategoryRepository categoryRepository,ProductRepository productRepository,ProductImageRepository productImageRepository,FileStorageService fileStorageService) {
        this._categoryRepository = categoryRepository;
        this._productRepository = productRepository;
        this._productImageRepository = productImageRepository;
        this._fileStorageService = fileStorageService;
    }

    @Transactional
    public Product createProduct(CreateProductRequest req, List<MultipartFile> images) {
        Category category = _categoryRepository.findById(req.categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + req.categoryId));

        Product.ProductType type = Product.ProductType.valueOf(req.type);

        ProductBuilder builder = ProductBuilder.create(req.sellerId,category,req.title,req.basePrice,type);

        if(req.description != null && !(req.description.isBlank())){
            builder.withDescription(req.description);
        }
        if(req.attributesJson != null && !(req.attributesJson.isBlank())){
            builder.withAttributesJson(req.attributesJson);
        }
        if(req.discountPercent != null){
            builder.withDescounterPercent(req.discountPercent);
        }
        if(Boolean.TRUE.equals(req.active)){builder.asActive();}

        Product product = _productRepository.save(builder.build());

        if (images != null && !images.isEmpty()) {
            int pos =0;
            for (MultipartFile image : images) {
                if (image != null || image.isEmpty()) continue;
                String url = _fileStorageService.saveImage(image);
                _productImageRepository.save(new ProductImage(product,url,pos++));
            }
        }
        return product;
    }
}
