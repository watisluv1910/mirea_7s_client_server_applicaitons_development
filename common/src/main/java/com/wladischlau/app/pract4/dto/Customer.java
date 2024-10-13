package com.wladischlau.app.pract4.dto;

import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Customer(@Id Long id, String name, String phone) {
}
