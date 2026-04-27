package org.example.marketplacebackend.service.category;

import org.example.marketplacebackend.domain.catalog.Category;
import org.example.marketplacebackend.domain.categorytree.CategoryComponent;
import org.example.marketplacebackend.domain.categorytree.CategoryComposite;
import org.example.marketplacebackend.domain.categorytree.CategoryLeaf;
import org.example.marketplacebackend.dto.CategoryNode;
import org.example.marketplacebackend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryTreeService {

    private final CategoryRepository _categoryRepository;

    public CategoryTreeService(CategoryRepository _categoryRepository) {
        this._categoryRepository = _categoryRepository;
    }

    public List<CategoryNode> getCategoryTree() {
        List<Category> categories = _categoryRepository.findAll();

        Map<Long,List<Category>> childrenByParentId = categories.stream()
                .filter(c -> c.getParentId() != null)
                .collect(Collectors.groupingBy(Category::getParentId));

        List<CategoryComponent> roots = categories.stream()
                .filter(c -> c.getParentId() == null)
                .map(c -> buildComponent(c,childrenByParentId))
                .toList();

        return roots.stream()
                .map(this::toDto)
                .toList();
    }

    private CategoryComponent buildComponent(Category category, Map<Long,List<Category>> childrenByParentId) {
        List<Category> children = childrenByParentId.getOrDefault(category.getId(),List.of());

        if (children.isEmpty()) {
            return new CategoryLeaf(category.getId(), category.getName());
        }

        CategoryComposite composite = new CategoryComposite(category.getId(), category.getName());

        for (Category child : children) {
            composite.add(buildComponent(child, childrenByParentId));
        }

        return composite;

    }

    private CategoryNode toDto(CategoryComponent component) {
        CategoryNode node = new CategoryNode(component.getId(), component.getName());

        for (CategoryComponent child : component.getChildren()) {
            node.children.add(toDto(child));
        }

        return node;
    }
}
