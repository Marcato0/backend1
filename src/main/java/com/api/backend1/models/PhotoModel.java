package com.api.backend1.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "photo")
public class PhotoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 80)
    private String name;
    @Column(nullable = false)
    private String type;

    @Column(name = "image", nullable = false)
    @Lob // Permite armazenar dados binários grandes (mais de 255 bytes)
    public byte[] image() {
        return this.image;
    }

    private byte[] image; // Armazena a imagem em bytes

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_fk")
//    private ProductModel product;



}
