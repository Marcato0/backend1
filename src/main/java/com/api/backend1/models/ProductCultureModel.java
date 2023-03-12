package com.api.backend1.models;

import com.api.backend1.dtos.ProductCultureDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "product_culture")
public class ProductCultureModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_fk")
    @JsonIgnoreProperties("productCultures")
    private ProductModel product;

    @ManyToOne
    @JoinColumn(name = "culture_fk")
    private CultureModel culture;

    @Column(nullable = false)
    private String area_size;




}
