package org.example.marketplacebackend.domain.integration.dev.devdelivery;


import org.example.marketplacebackend.domain.integration.delivery.DeliveryProvider;
import org.example.marketplacebackend.domain.integration.delivery.DeliveryResult;
import org.example.marketplacebackend.domain.integration.delivery.DeliveryType;

public class PickupDeliveryProvider implements DeliveryProvider {

    @Override
    public DeliveryResult arrange(Long orderId, DeliveryType type) {
        return new DeliveryResult(true, "PICKUP_DEV", "Pickup simulated");
    }

}