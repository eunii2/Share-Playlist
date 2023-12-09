package com.msp.playlist.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GrantAccessRequestDto {
    private Long memberId;
    private boolean canEdit;

    public Long getMemberId() {
        return memberId;
    }

    public boolean isCanEdit() {
        return canEdit;
    }
}