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
    @Column(name = "picture_name", nullable = false)
    private String pictureName;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;


    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                ", pictureName=" + pictureName +
                ", user=" + user.getId() +
                '}';
    }
}
