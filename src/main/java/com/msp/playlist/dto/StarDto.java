package com.msp.playlist.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record StarDto(
        @NotNull
        @Size(min = 0, max = 50)
        String comment,

        @NotNull
        @Min(value = 0)
        @Max(value = 5)
        Integer grade
) {
}
