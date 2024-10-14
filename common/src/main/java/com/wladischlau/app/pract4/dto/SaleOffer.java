package com.wladischlau.app.pract4.dto;

import java.math.BigDecimal;

public record SaleOffer(Long carId, Long customerId, BigDecimal offeredPrice) {
}
