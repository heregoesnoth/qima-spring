package com.qima.dto;

public record CategoryResponse(
        Long id,
        String name,
        String parent
) {}
