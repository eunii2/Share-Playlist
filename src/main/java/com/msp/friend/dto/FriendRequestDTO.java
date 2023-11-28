package com.msp.friend.dto;

import com.msp.friend.entity.FriendRequest;
import com.msp.membership.dto.MemberDTO;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequestDTO {
    private Long id;
    private MemberDTO requestId;
    private MemberDTO requestedId;

    public static FriendRequestDTO of(FriendRequest friendRequest) {
        return FriendRequestDTO.builder()
                .id(friendRequest.getId())
                .requestId(MemberDTO.toMemberDTO(friendRequest.getRequestId()))
                .requestedId(MemberDTO.toMemberDTO(friendRequest.getRequestedId()))
                .build();
    }
}
