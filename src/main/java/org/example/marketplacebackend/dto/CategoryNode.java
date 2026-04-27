package org.example.marketplacebackend.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryNode {
    public Long id;
    public String name;
    public List<CategoryNode> children = new ArrayList<>();

    public CategoryNode(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
