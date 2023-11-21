<<<<<<< HEAD
package com.msp.membership.entity;

import com.msp.membership.dto.MemberDTO;
=======
package com.MusicSharing.member.entity;

import java.util.List;
import java.util.ArrayList;

import com.MusicSharing.member.dto.MemberDTO;
>>>>>>> 6269fa28bc8a7b9bd05f1810657f8226645249e9
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
<<<<<<< HEAD
=======
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
>>>>>>> 6269fa28bc8a7b9bd05f1810657f8226645249e9

@Entity
@Setter
@Getter
@Table(name = "member_table")   // 테이블이 생성되었을 때의 테이블 이름
public class MemberEntity { // 일종의 테이블 역할을 함
    @Id //primary key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
<<<<<<< HEAD
    private Long id;

    @Column(unique = true)
    private  String userId;

    @Column
    private  String userPw;
=======
    private Long Id;

    @Column(unique = true)
    private  String userid;

    @Column
    private  String userpw;
>>>>>>> 6269fa28bc8a7b9bd05f1810657f8226645249e9

    @Column
    private String username;

    @Column
<<<<<<< HEAD
    private String userPhone;

    @Column
    private String userEmail;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUserId(memberDTO.getUserid());
        memberEntity.setUserPw(memberDTO.getUserpw());
        memberEntity.setUsername(memberDTO.getUsername());
        memberEntity.setUserPhone(memberDTO.getUserphone());
        memberEntity.setUserEmail(memberDTO.getUseremail());
=======
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
>>>>>>> 6269fa28bc8a7b9bd05f1810657f8226645249e9
        return memberEntity;
    }
}
