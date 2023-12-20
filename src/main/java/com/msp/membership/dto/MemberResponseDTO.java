package com.msp.membership.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
    public Long id;
    public String userid;
}
