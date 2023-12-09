package com.msp.membership.entity;

import com.msp.playlist.entity.PlaylistMember;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", unique = true)
    private  String userId;

    @Column(name = "user_pw", nullable = false)
    private  String userpw;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_phone")
    private String userphone;

    @Column(name = "user_email")
    private String useremail;

    @Column(name = "activated")
    private boolean activated;

    @Column
    private String profileImage;

    @OneToMany(mappedBy = "member")
    private Set<PlaylistMember> playlists;

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    // 밑 부분 수정
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}