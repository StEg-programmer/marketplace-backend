package org.example.marketplacebackend.service;

import org.example.marketplacebackend.domain.catalog.Product;
import org.example.marketplacebackend.domain.catalog.ProductStock;
import org.example.marketplacebackend.dto.UpsertProductStockRequest;
import org.example.marketplacebackend.repository.ProductRepository;
import org.example.marketplacebackend.repository.ProductStockRepository;
import org.example.marketplacebackend.api.exception.BadRequestException;
import org.example.marketplacebackend.api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductStockService {

    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    public ProductStockService(ProductRepository productRepository,
                               ProductStockRepository productStockRepository) {
        this.productRepository = productRepository;
        this.productStockRepository = productStockRepository;
    }

    @Transactional
    public ProductStock upsertStock(Long productId, UpsertProductStockRequest req) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));

        if (product.getType() != Product.ProductType.PHYSICAL) {
            throw new BadRequestException("Stock can be managed only for PHYSICAL products");
        }

        if (!product.getSellerId().equals(req.sellerId)) {
            throw new BadRequestException("Seller does not own this product");
        }

        ProductStock stock = productStockRepository.findByProductId(productId)
                .orElseGet(() -> new ProductStock(productId, req.sellerId, req.availableQuantity));

        stock.setAvailableQuantity(req.availableQuantity);

        return productStockRepository.save(stock);
    }

    @Transactional(readOnly = true)
    public ProductStock getStock(Long productId) {
        return productStockRepository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("Stock not found for product: " + productId));
    }
}