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
    @Column(name = "authority_name", length = 50)
    private String authorityName;

}
