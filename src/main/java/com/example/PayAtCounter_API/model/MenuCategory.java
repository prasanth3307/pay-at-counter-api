package com.example.PayAtCounter_API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "menu_categories", schema = "payatcounter_v2", indexes = {
        @Index(name = "store_id", columnList = "store_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "name", columnNames = {"name"})
})
public class MenuCategory {
    @Id
    @Column(name = "category_id", nullable = false, length = 36 , columnDefinition = "char(36)")
    private String categoryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

}