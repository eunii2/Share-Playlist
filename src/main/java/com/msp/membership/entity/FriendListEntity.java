package com.msp.membership.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.msp.membership.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 모든 필드값을 파라미터로 갖는 생성자 생성
@NoArgsConstructor  // 파라미터가 없는 생성자를 생성
@Entity
public class FriendListEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id1")
    private MemberEntity id1;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id2")
    private MemberEntity id2;

}
