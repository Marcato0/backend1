package com.api.backend1.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithCulturesDto {

    private UUID id;
    private String name;
    private String description;
    private LocalDate registrationDate;
    private boolean status;
    private List<ProductCultureDto> productCultures;

}
