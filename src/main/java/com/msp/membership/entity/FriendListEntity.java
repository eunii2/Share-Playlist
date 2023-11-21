package com.MusicSharing.member.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.MusicSharing.member.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
