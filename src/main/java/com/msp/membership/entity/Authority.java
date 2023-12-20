package com.msp.membership.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @Column(name = "authority_name", length = 50)
    private String authorityName;

}
