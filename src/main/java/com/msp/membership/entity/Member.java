package com.msp.membership.entity;

import java.util.List;
import java.util.ArrayList;

import com.msp.friend.entity.FriendList;
import com.msp.friend.entity.FriendRequest;
import com.msp.friend.entity.Preference;
import com.msp.membership.dto.MemberDTO;
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
public class Member { // 일종의 테이블 역할을 함
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
    private List<FriendList> friendlist1 = new ArrayList<>();

    @OneToMany(mappedBy = "id2")
    private List<FriendList> friendlist2 = new ArrayList<>();

    @OneToMany(mappedBy = "requestId")
    private List<FriendRequest> friendRequest1 = new ArrayList<>();

    @OneToMany(mappedBy = "requestedId")
    private List<FriendRequest> friendRequest2 = new ArrayList<>();

    @OneToMany(mappedBy = "userid")
    private List<Preference> preference = new ArrayList<>();

    public static Member toMemberEntity(MemberDTO memberDTO){
        Member member = new Member();
        member.setUserid(memberDTO.getUserid());
        member.setUserpw(memberDTO.getUserpw());
        member.setUsername(memberDTO.getUsername());
        member.setUserphone(memberDTO.getUserphone());
        member.setUseremail(memberDTO.getUseremail());
        return member;
    }
}
