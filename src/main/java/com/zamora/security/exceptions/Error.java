package com.zamora.security.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Error {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate tiempo;
    private String mensaje;
    private boolean detalles;

    public Error(LocalDate now, String message, String description) {
    }
}
