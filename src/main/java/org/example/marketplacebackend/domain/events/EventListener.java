package org.example.marketplacebackend.domain.events;

public interface EventListener  <E extends Event>{
    void onEvent(E event);

}
