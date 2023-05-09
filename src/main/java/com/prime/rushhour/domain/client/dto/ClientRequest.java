package com.prime.rushhour.domain.client.dto;

import com.prime.rushhour.domain.account.dto.AccountRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientRequest(
        @NotBlank(message = "Phone is a mandatory field")
        @Pattern(regexp="^\\+?[0-9]+$", message="Phone number must contain numbers only and can optionally start with +")
        String phone,
        @NotBlank(message = "Address is a mandatory field")
        @Size(min = 3, message = "Address must be at least 3 characters long")
        String address,
        @Valid
        AccountRequest accountRequest
) {
}
