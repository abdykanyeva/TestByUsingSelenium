package com.sparkle.dto;

import com.sparkle.enums.ClientVendorType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVendorDTO {

    private Long id;

//    @NotBlank(message = "company name is a required field")
//    @Size(min=2,max=50, message = "company name is a required field between 2 and 50 characters long")
    private String clientVendorName;

    @NotBlank(message = "please enter a valid phone number")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",message = "please enter valid phone number")
    private String phone;

@NotBlank(message = "please enter a valid website")
@Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*")
    private String website;

@NotNull(message = "please select type")
    private ClientVendorType clientVendorType;

@Valid //checks all addressDto makes sure it passes
    private AddressDTO address;

@Valid
    private CompanyDTO company;
}
