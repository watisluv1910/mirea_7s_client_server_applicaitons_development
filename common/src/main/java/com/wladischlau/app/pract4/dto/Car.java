package com.wladischlau.app.pract4.dto;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record Car(@Id Long id,
                  String brand,
                  String model,
                  BigDecimal price) {}
