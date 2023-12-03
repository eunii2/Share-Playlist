package com.msp.playlist.dto;

public class GrantAccessRequestDto {
    private Long memberId;
    private boolean canEdit;

    public Long getMemberId(Long MemberId) {
        return MemberId;
    }

    public boolean isCanEdit() { //TODO~뭔가 꼬름함
        //TODO~수정해야할거임
        return false;
    }
}