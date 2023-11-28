package com.msp.friend.dto;


import com.msp.membership.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendListDTO {
    private Long id;
    private MemberDTO id1;
    private MemberDTO id2;
}