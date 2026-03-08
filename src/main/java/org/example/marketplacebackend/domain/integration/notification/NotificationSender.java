package org.example.marketplacebackend.domain.integration.notification;

public interface NotificationSender {

    void send(String to, String subject, String text);

}