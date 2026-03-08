package org.example.marketplacebackend.domain.integration.dev.devdelivery;

import org.example.marketplacebackend.domain.integration.delivery.DeliveryProvider;
import org.example.marketplacebackend.domain.integration.delivery.DeliveryProviderFactory;
import org.example.marketplacebackend.domain.integration.delivery.DeliveryType;

public class DevDeliveryProviderFactory implements DeliveryProviderFactory {
    @Override
    public DeliveryProvider create(DeliveryType type){
        return switch (type){
            case COURIER -> new CourirerDeliveryProvider();
            case PICKUP -> new PickupDeliveryProvider();
            case POST -> new PostDeliveryProvider();
            case NONE -> new PickupDeliveryProvider();
        };
    }
}
