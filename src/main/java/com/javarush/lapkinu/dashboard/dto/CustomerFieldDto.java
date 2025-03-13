package com.javarush.lapkinu.dashboard.dto;

import java.util.UUID;

public record CustomerFieldDto(
        UUID id,
        String name
) {
}