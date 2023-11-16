package com.msp.membership.entity;

import com.msp.membership.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_table")   // 테이블이 생성되었을 때의 테이블 이름
public class MemberEntity { // 일종의 테이블 역할을 함
    @Id //primary key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(unique = true)
    private  String userid;

    @Column
    private  String userpw;

    @Column
    private String username;

    @Column
    private String userphone;

    @Column
    private String useremail;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUserid(memberDTO.getUserid());
        memberEntity.setUserpw(memberDTO.getUserpw());
        memberEntity.setUsername(memberDTO.getUsername());
        memberEntity.setUserphone(memberDTO.getUserphone());
        memberEntity.setUseremail(memberDTO.getUseremail());
        return memberEntity;
    }
}
