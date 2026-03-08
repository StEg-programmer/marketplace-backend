package org.example.marketplacebackend.domain.integration.dev;

import org.example.marketplacebackend.domain.integration.notification.NotificationSender;

public class DevNotificationSender implements NotificationSender {
    @Override
    public void send(String to, String subject, String text) {
        System.out.println("[DEV NOTIFY] to=" + to + ", subject=" + subject + ", text=" + text);
    }
}
