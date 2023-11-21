package com.MusicSharing.member.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequestDTO {
    private Long Id;
    private MemberDTO requestId;
    private MemberDTO requestedId;
}
