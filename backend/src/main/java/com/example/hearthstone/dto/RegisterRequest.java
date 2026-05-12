package com.example.hearthstone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 6, max = 64) String password,
        @NotBlank String nickname
) {
}
