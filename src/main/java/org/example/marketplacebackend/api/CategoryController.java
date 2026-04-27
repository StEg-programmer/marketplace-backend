package org.example.marketplacebackend.api;

import org.example.marketplacebackend.dto.CategoryNode;
import org.example.marketplacebackend.service.category.CategoryTreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryTreeService categoryTreeService;

    public CategoryController(CategoryTreeService categoryTreeService) {
        this.categoryTreeService = categoryTreeService;
    }

    @GetMapping("/api/categories/tree")
    public List<CategoryNode> getTree() {
        return categoryTreeService.getCategoryTree();
    }
}
