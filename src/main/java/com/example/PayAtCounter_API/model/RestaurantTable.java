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
@Table(name = "restaurant_tables", schema = "payatcounter_v2", uniqueConstraints = {
        @UniqueConstraint(name = "store_id", columnNames = {"store_id", "table_number"})
})
public class RestaurantTable {
    @Id
    @Column(name = "table_id", nullable = false, length = 36 , columnDefinition = "char(36)")
    private String tableId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;

    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity;

    @Column(length = 20, nullable = false)
    private String status = "AVAILABLE";

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}