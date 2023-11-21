package com.MusicSharing.member.entity;

import java.util.List;
import java.util.ArrayList;

import com.MusicSharing.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Setter
@Getter
@Table(name = "member_table")   // 테이블이 생성되었을 때의 테이블 이름
public class MemberEntity { // 일종의 테이블 역할을 함
    @Id //primary key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long Id;

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

    @OneToMany(mappedBy = "id1")
    private List<FriendListEntity> friendlist1 = new ArrayList<>();

    @OneToMany(mappedBy = "id2")
    private List<FriendListEntity> friendlist2 = new ArrayList<>();

    @OneToMany(mappedBy = "requestId")
    private List<FriendRequestEntity> friendRequest1 = new ArrayList<>();

    @OneToMany(mappedBy = "requestedId")
    private List<FriendRequestEntity> friendRequest2 = new ArrayList<>();

    @OneToMany(mappedBy = "userid")
    private List<PreferenceEntity> preference = new ArrayList<>();

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
