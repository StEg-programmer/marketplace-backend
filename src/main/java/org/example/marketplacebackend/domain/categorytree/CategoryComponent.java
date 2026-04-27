package org.example.marketplacebackend.domain.categorytree;

import java.util.List;

public interface CategoryComponent {
    Long getId();
    String getName();

    List<CategoryComponent> getChildren();

    default void add(CategoryComponent child){
        throw new UnsupportedOperationException("Leaf cannot add child");

    }

    default void remove(CategoryComponent child){
        throw new UnsupportedOperationException("Leaf cannot remove child");
    }
}
