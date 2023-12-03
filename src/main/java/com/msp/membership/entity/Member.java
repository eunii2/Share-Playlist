package com.msp.membership.entity;

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
    @Column(name = "id")
    private int id;

    @Column(name = "user_id", unique = true)
    private  String userid;

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

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
