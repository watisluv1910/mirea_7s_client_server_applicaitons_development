package com.wladischlau.app.pract4.dto;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record Sale(@Id Long id,
                   Long carId,
                   Long clientId,
                   BigDecimal salePrice,
                   Timestamp saleDate) {}
