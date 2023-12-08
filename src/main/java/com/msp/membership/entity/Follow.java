package com.msp.membership.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="fromUserId")
    private Member fromUser;

    @ManyToOne
    @JoinColumn(name="toUserId")
    private Member toUser;

    @CreationTimestamp
    @JoinColumn(name="createDate")
    private Timestamp follow_date;

    public void setFollowing(Member fromUser) {
        this.fromUser = fromUser;
    }

    public void setFollower(Member toUser) {
        this.toUser = toUser;
    }

    public void setFollow_date(Timestamp follow_date) {
        this.follow_date = follow_date;
    }
}
