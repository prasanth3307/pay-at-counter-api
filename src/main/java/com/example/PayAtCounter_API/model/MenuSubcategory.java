package com.example.PayAtCounter_API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "menu_subcategories", schema = "payatcounter_v2", uniqueConstraints = {
        @UniqueConstraint(name = "category_id", columnNames = {"category_id", "name"})
})
public class MenuSubcategory {
    @Id
    @Column(name = "subcategory_id", nullable = false, length = 36)
    private String subcategoryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategory category;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

}