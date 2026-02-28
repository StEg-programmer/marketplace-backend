package org.example.marketplacebackend.domain.catalog;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(nullable = false,length = 120)
    private String name;

    protected Category() {}

    public Category(String name,Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public Long getId() {return id;}
    public  Category getParent() {return parent;}
    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
    public void setParent(Category parent) {this.parent = parent;}

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Category that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
