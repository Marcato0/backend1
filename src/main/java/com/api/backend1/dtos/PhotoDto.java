package com.api.backend1.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PhotoDto {

    @NotBlank
    @Size(max=80)
    private String name;

    @NotBlank
    private String type;

    @NotBlank
    private byte[] image;


}
