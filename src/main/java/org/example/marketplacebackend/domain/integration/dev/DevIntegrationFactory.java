package org.example.marketplacebackend.domain.integration.dev;

import org.example.marketplacebackend.domain.integration.IntegrationFactory;
import org.example.marketplacebackend.domain.integration.delivery.DeliveryProviderFactory;
import org.example.marketplacebackend.domain.integration.dev.devdelivery.DevDeliveryProviderFactory;
import org.example.marketplacebackend.domain.integration.dev.devpayment.DevPaymentProviderFactory;
import org.example.marketplacebackend.domain.integration.notification.NotificationSender;
import org.example.marketplacebackend.domain.integration.payment.PaymentProviderFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevIntegrationFactory implements IntegrationFactory {
    private final PaymentProviderFactory paymentFactory = new DevPaymentProviderFactory();
    private final DeliveryProviderFactory deliveryFactory = new DevDeliveryProviderFactory();
    private final NotificationSender notificationSender = new DevNotificationSender();

    @Override
    public PaymentProviderFactory paymentFactory() {
        return paymentFactory;
    }

    @Override
    public DeliveryProviderFactory deliveryFactory() {
        return deliveryFactory;
    }

    @Override
    public NotificationSender notificationSender() {
        return notificationSender;
    }
}
