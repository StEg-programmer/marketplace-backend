package org.example.marketplacebackend.domain.categorytree;

import java.util.ArrayList;
import java.util.List;

public class CategoryComposite implements CategoryComponent {

    private final Long id;
    private final String name;
    private final List<CategoryComponent> children = new ArrayList<CategoryComponent>();

    public CategoryComposite(Long id, String name) {
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
    public List<CategoryComponent> getChildren() {
        return children;
    }

    @Override
    public void add(CategoryComponent child){
        children.add(child);
    }

    @Override
    public void remove(CategoryComponent child){
        children.remove(child);
    }
}
