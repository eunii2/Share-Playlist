package com.msp.membership.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="follow_uk",
                        columnNames = {"toUserId", "fromUserId"}
                )
        }
)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int toUserId;

    @JoinColumn(name = "fromUserId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member fromUser;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
