package com.andreidodu.model;

import com.andreidodu.model.common.ModelCommon;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Arrays;

@Entity
@Table(name = "mj_user_picture")
@EntityListeners(AuditingEntityListener.class)
public class UserPicture extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "picture", nullable = false)
    private byte[] picture;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "UserPicture{" +
                "id=" + id +
                ", picture=" + Arrays.toString(picture) +
                ", user=" + user.getId() +
                '}';
    }
}
