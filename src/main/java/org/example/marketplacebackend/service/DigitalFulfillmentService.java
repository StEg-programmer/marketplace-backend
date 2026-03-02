package org.example.marketplacebackend.service;

import org.example.marketplacebackend.domain.digital.DigitalKey;
import org.example.marketplacebackend.domain.order.OrderItem;
import org.example.marketplacebackend.repository.DigitalKeyRepository;
import org.example.marketplacebackend.api.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DigitalFulfillmentService {

    private final DigitalKeyRepository digitalKeyRepository;

    public DigitalFulfillmentService(DigitalKeyRepository digitalKeyRepository) {
        this.digitalKeyRepository = digitalKeyRepository;
    }

    @Transactional
    public List<DigitalKey> issueKeysForItem(OrderItem item) {
        int need = item.getQuantity();

        List<DigitalKey> keys = digitalKeyRepository.lockNextAvailable(item.getProductId(), need);
        if (keys.size() < need) {
            throw new BadRequestException("Not enough digital keys in stock for productId=" + item.getProductId());
        }

        for (DigitalKey k : keys) {
            k.markSold(item.getId());
        }

        return digitalKeyRepository.saveAll(keys);
    }
}