package com.api.backend1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk")
    @JsonIgnore
    private ProductModel product;

    @Column(nullable = false, length = 80)
    private String name;
    @Column(nullable = false)
    private String type;

    @Column(name = "image", nullable = false)
    @Lob // Permite armazenar dados binários grandes (mais de 255 bytes)
    public byte[] image() {
        return this.image;
    }

    /**
     * @JsonIgnore: indica que a propriedade product não será serializada para JSON.
     */
    @JsonIgnore
    private byte[] image; // Armazena a imagem em bytes

}
