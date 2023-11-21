package com.MusicSharing.member.dto;

import com.MusicSharing.member.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter //각각의 필드에 대한 getter을 만들어줌
@Setter
@NoArgsConstructor  // 기본 생성자를 자동으로 만들어줌
@ToString   //DTO 객체가 가지고 있는 값들을 출력할 때 tostring을 사용하는데 자동으로 만들어줌
public class MemberDTO {    //회원 정보의 필요한 내용들을 필드로 정리하고 클래스의 필드를 모두 private로
    private Long Id;
    private String userid;  //name과 DTO의 필드가 동일하면 setter 매서드를 호출하면서 작성한 값을 알아서 담아줌
    private String userpw;
    private String username;
    private String userphone;
    private String useremail;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setUserid(memberEntity.getUserid());
        memberDTO.setUserpw(memberEntity.getUserpw());
        memberDTO.setUsername(memberEntity.getUsername());
        memberDTO.setUserphone(memberEntity.getUserphone());
        memberDTO.setUseremail(memberEntity.getUseremail());
        return memberDTO;
    }
}
