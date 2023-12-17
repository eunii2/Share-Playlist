package com.msp.membership.entity;
import com.msp.playlist.entity.Grade;
import com.msp.playlist.entity.Playlist;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;

    @Column(name = "authority_name", length = 50)
    private String authorityName;

    /*@ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;*/
}
