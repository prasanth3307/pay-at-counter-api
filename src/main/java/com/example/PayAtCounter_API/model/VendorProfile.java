package com.example.PayAtCounter_API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "vendor_profiles", schema = "payatcounter_v2", uniqueConstraints = {
        @UniqueConstraint(name = "vendor_name", columnNames = {"vendor_name"}),
        @UniqueConstraint(name = "email", columnNames = {"email"}),
        @UniqueConstraint(name = "phone_no", columnNames = {"phone_no"})
})

public class VendorProfile {
    @Id
    @Column(name = "vendor_id", nullable = false, length = 36 , columnDefinition = "char(36)")
    private String vendorId;

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_no", nullable = false, length = 15)
    private String phoneNo;

    @Column(length = 20, nullable = false)
    private String status = "ACTIVE";

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

}