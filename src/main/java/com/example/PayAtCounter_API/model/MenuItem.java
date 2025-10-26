package com.example.PayAtCounter_API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "menu_items", schema = "payatcounter_v2", indexes = {
        @Index(name = "subcategory_id", columnList = "subcategory_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "item_name", columnNames = {"item_name"})
})
public class MenuItem {
    @Id
    @Column(name = "menu_item_id", nullable = false, length = 36)
    private String menuItemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "subcategory_id", nullable = false)
    private MenuSubcategory subcategory;

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @Lob
    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "item_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal itemPrice;

    @Column(name = "preparation_time", length = 50)
    private String preparationTime;

    @ColumnDefault("'MILD'")
    @Lob
    @Column(name = "spicy_level")
    private String spicyLevel;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

}