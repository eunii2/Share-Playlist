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
    private Long id;
    private String username;
    private String profileImage;

    public UserProfileDTO EntityToDto(Member member) {
        return UserProfileDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .profileImage(member.getProfileImage())
                .build();
    }
}