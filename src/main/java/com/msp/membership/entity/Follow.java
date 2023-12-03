package com.msp.membership.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
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
    @Column(name = "id")
    private int id;

    private int toUserId;

    @JoinColumn(name = "fromUserId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member fromUser;

    public Follow(int toUserId, Member fromUser) {
        this.toUserId = toUserId;
        this.fromUser = fromUser;
    }

}
