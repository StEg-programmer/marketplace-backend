package org.example.marketplacebackend.integration.adapter;

import org.example.marketplacebackend.integration.external.MockNotificationClient;

public class NotificationAdapter {
    private final MockNotificationClient _notificationClient;

    public NotificationAdapter(MockNotificationClient notificationClient) {
        this._notificationClient = notificationClient;
    }

    public void send(String to,String subject,String text) {
        _notificationClient.pushMessage(
                "user:" + to,
                subject,
                text
        );
    }
}
