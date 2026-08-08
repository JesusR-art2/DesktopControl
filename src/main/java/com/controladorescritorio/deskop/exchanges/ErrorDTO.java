package com.controladorescritorio.deskop.exchanges;

import java.time.Instant;

public record ErrorDTO (
        String code,
        String message,
        Instant timestamp
){}
