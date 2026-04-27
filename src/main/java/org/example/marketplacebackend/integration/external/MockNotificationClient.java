package org.example.marketplacebackend.integration.external;

public class MockNotificationClient {
    public void pushMessage(String recipientCode,String messageTitle,String messageBody){
        System.out.println("[MOCK NOTIFICATION CLIENT] recipient="
                + recipientCode
                + ", title=" + messageTitle
                + ", body=" + messageBody);
    }
}
