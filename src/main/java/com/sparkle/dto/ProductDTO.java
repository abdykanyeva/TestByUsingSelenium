package com.sparkle.dto;

import com.sparkle.enums.ProductUnit;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private  Long id ;
    @NotNull
    @Min(value = 1)
    private int lowLimitAlert;

    @NotBlank
    @Size(max =100, min = 2)
    private String name;

    @NotNull
    private ProductUnit productUnit;

    @NotNull
    private int quantityInStock;

    @NotNull
    private CategoryDTO category;

    private InvoiceProductDTO invoiceProductDTO;
}
