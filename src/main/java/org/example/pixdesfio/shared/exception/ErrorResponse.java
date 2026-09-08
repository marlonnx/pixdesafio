package org.example.pixdesfio.shared.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        int status,
        List<String> errors,
        String message,
        String path,
        LocalDateTime timestamp
) {}