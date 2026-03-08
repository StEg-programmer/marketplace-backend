package org.example.marketplacebackend.domain.integration;

import org.example.marketplacebackend.domain.integration.delivery.DeliveryProviderFactory;
import org.example.marketplacebackend.domain.integration.notification.NotificationSender;
import org.example.marketplacebackend.domain.integration.payment.PaymentProvider;
import org.example.marketplacebackend.domain.integration.payment.PaymentProviderFactory;

public interface IntegrationFactory {
    PaymentProviderFactory paymentFactory();
    DeliveryProviderFactory deliveryFactory();
    NotificationSender notificationSender();

}
