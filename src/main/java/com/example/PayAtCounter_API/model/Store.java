package com.example.PayAtCounter_API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Entity
@Table(name = "stores", schema = "payatcounter_v2", indexes = {
        @Index(name = "vendor_id", columnList = "vendor_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "store_address", columnNames = {"store_address"}),
        @UniqueConstraint(name = "store_phone", columnNames = {"store_phone"})
})
public class Store {
    @Id
    @Column(name = "store_id", nullable = false, length = 36 , columnDefinition = "char(36)")
    private String storeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "vendor_id", nullable = false)
    @JsonIgnore
    private VendorProfile vendor;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_address", nullable = false)
    private String storeAddress;

    @Column(name = "store_phone", nullable = false, length = 20)
    private String storePhone;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @JsonProperty("vendor_id")
    public String getVendorId() {
        return vendor != null ? vendor.getVendorId() : null;
    }

}