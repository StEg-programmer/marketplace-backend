package org.example.marketplacebackend.service;

import jakarta.transaction.Transactional;
import org.example.marketplacebackend.domain.catalog.Product;
import org.example.marketplacebackend.domain.digital.DigitalKey;
import org.example.marketplacebackend.dto.AddDigitalKeysRequest;
import org.example.marketplacebackend.repository.DigitalKeyRepository;
import org.example.marketplacebackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DigitalKeyService {

    private final DigitalKeyRepository _digitalKeyRepository;
    private final ProductRepository _productRepository;

    public DigitalKeyService(ProductRepository productRepository, DigitalKeyRepository digitalKeyRepository){
        this._productRepository = productRepository;
        this._digitalKeyRepository = digitalKeyRepository;
    }

    @Transactional
    public int addKeys(AddDigitalKeysRequest req){
        Product product = _productRepository.findById(req.productId)
                .orElseThrow(()->new IllegalArgumentException("Product not found" + req.productId));
        if (product.getType() != Product.ProductType.DIGITAL){
            throw new IllegalArgumentException("Product type is not Digital");
        }
        if (!product.getSellerId().equals(req.sellerId)){
            throw new IllegalArgumentException("Seller does not own this product");
        }

        int added = 0;
        for (String key : req.keys){
            _digitalKeyRepository.save(new DigitalKey(req.productId,req.sellerId, key));
            added++;
        }
        return added;
    }

    public long availableKeyCount(Long productId){
        return _digitalKeyRepository.countByProductIdAndStatus(productId,DigitalKey.KeyStatus.AVAILABLE);
    }
}
