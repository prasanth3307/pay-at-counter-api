package com.example.PayAtCounter_API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "inventory", schema = "payatcounter_v2", indexes = {
        @Index(name = "menu_item_id", columnList = "menu_item_id")
})
public class Inventory {
    @Id
    @Column(name = "inventory_id", nullable = false, length = 36 , columnDefinition = "char(36)")
    private String inventoryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @ColumnDefault("0")
    @Column(name = "current_stock")
    private Integer currentStock;

    @ColumnDefault("0")
    @Column(name = "restock_threshold")
    private Integer restockThreshold;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "last_updated")
    private Instant lastUpdated;

}