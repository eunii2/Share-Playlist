package com.msp.membership.dto;

import com.msp.membership.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private int id;
    private String userid;
    private String username;
    private String profileImage;

    private boolean subscribeState;
    private int subscribeCount;

    public UserProfileDTO EntityToDto(Member member) {
        return UserProfileDTO.builder()
                .id(member.getId())
                .userid(member.getUserid())
                .username(member.getUsername())
                .profileImage(member.getProfileImage())
                .build();
    }
}