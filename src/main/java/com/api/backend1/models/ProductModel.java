package com.api.backend1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // chave primária será gerado automaticamente pelo sistema
    private UUID id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "registration_date")
    private LocalDate registrationDate;

    @Column(nullable = false)
    private boolean status; // status do produto (ativo/inativo)

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("product")
    private List<ProductCultureModel> productCultures = new ArrayList<>();




}
