package com.andreidodu.model;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "mj_user_picture")
public class UserPicture extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "picture", nullable = false)
    private Byte[] picture;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
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
