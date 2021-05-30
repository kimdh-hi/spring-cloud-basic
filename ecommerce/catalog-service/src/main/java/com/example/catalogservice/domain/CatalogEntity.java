package com.example.catalogservice.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "catalog")
public class CatalogEntity {

    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String productId;
    @Column(nullable = false)
    private String product_name;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;
}
