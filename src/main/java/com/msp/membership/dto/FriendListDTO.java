package com.msp.membership.dto;

import com.msp.membership.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FriendListDTO {
    private String profileImage;
    private String userid;

    public FriendListDTO(Member member) {
        this.profileImage = member.getProfileImage();
        this.userid = member.getUserid();
    }
}
