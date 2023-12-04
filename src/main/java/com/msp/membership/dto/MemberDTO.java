package com.msp.membership.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msp.membership.entity.Member;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @NotNull
    private String userid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String userpw;

    private String username;
    private String userphone;
    private String useremail;

    public Member toEntity() {
        return Member.builder()
                .userid(userid)
                .userpw(userpw)
                .useremail(useremail)
                .username(username)
                .userphone(userphone)
                .activated(true)
                .build();
    }

    private Set<AuthorityDTO> authorityDTOSet;

    public static MemberDTO from(Member member) {
        if(member == null) return null;

        return MemberDTO.builder()
                .userid(member.getUserid())
                .username(member.getUsername())
                .authorityDTOSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDTO.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
