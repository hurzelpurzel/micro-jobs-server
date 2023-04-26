package com.andreidodu.model;

import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Entity
@Table(name = "mj_user")
@EntityListeners(AuditingEntityListener.class)
public class User extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private PaymentType paymentType;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserPicture userPicture;

    @OneToMany(mappedBy = "publisher")
    private Set<Job> jobs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public UserPicture getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(UserPicture userPicture) {
        this.userPicture = userPicture;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", paymentType=" + paymentType.getId() +
                ", userPicture=" + userPicture.getId() +
                '}';
    }
}
