package org.example.marketplacebackend.domain.categorytree;

import java.util.List;

public class CategoryLeaf implements CategoryComponent {

    private final Long id;
    private final String name;

    public CategoryLeaf(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<CategoryComponent> getChildren(){
        return List.of();
    }
}
