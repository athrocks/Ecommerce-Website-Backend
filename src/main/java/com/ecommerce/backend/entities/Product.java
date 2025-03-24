package com.ecommerce.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String brand;

    @Column
    private Float rating;

    @Column(nullable = false)
    private boolean isNewArrival;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private java.util.Date createdAt;
    private LocalDateTime createdAt;

    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private java.util.Date updatedAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariant> productVariants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryType_id", nullable = false)
    @JsonIgnore
    private CategoryType categoryType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Resources> resources;

    @PrePersist
    protected void onCreate() {
//        createdAt = new java.util.Date();
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
//        updatedAt = new java.util.Date();
        updatedAt = LocalDateTime.now();
    }
}
