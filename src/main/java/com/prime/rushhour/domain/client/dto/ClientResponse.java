package com.prime.rushhour.domain.client.dto;

import com.prime.rushhour.domain.account.dto.AccountResponse;

public record ClientResponse(
        String phone,
        String address,
        AccountResponse account
) {
}
