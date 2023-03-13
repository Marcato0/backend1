package com.api.backend1.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private UUID id;

    @NotBlank
    @Size(max=80)
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private boolean status;


    private List<ProductCultureDto> productCultures = new ArrayList<>();

    // lista de IDs das fotos do produto
    private List<UUID> productPhotos = new ArrayList<>();


}
