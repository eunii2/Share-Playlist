package com.msp.membership.entity;

import com.msp.playlist.entity.Playlist;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userid", unique = true)
    private  String userid;

    @Column(name = "userpw", nullable = false)
    private  String userpw;

    @Column(name = "username")
    private String username;

    @Column(name = "userphone")
    private String userphone;

    @Column(name = "useremail")
    private String useremail;

    @Column(name = "activated")
    private boolean activated;

    @Column
    private String profileImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Playlist> playlists = new ArrayList<>();



    public String getProfileImage() {
        return profileImage;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
