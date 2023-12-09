package com.msp.membership.dto;

import com.msp.membership.entity.Member;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private int id;
    private String userid;
    private String userpw;
    private String username;
    private String userphone;
    private String useremail;

    public static MemberDTO toMemberDTO(Member member){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setUserid(member.getUserId());
        memberDTO.setUserpw(member.getUserpw());
        memberDTO.setUsername(member.getUsername());
        memberDTO.setUserphone(member.getUserphone());
        memberDTO.setUseremail(member.getUseremail());
        return memberDTO;
    }

    private Set<AuthorityDTO> authorityDTOSet;

    public static MemberDTO from(Member member) {
        if(member == null) return null;

        return MemberDTO.builder()
                .userid(member.getUserId())
                .username(member.getUsername())
                .authorityDTOSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDTO.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
