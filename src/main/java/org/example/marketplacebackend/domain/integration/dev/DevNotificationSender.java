package org.example.marketplacebackend.domain.integration.dev;

import org.example.marketplacebackend.domain.integration.notification.NotificationSender;
import org.example.marketplacebackend.integration.adapter.NotificationAdapter;
import org.example.marketplacebackend.integration.external.MockNotificationClient;

public class DevNotificationSender implements NotificationSender {

    private final NotificationAdapter _adapter = new NotificationAdapter(new MockNotificationClient());
    @Override
    public void send(String to, String subject, String text) {
        _adapter.send(to, subject, text);

    }
}
