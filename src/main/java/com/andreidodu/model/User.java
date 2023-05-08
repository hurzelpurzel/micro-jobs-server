package com.andreidodu.model;

import com.andreidodu.model.common.ModelCommon;
import com.andreidodu.model.message.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "mj_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends ModelCommon implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "username", nullable = false, unique = true)
    @Length(min = 3)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @Length(min = 6)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstname", nullable = false)
    @Length(min = 3)
    private String firstname;
    @Column(name = "lastname", nullable = false)
    @Length(min = 3)
    private String lastname;
    @Column(name = "description")
    private String description;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "status", nullable = false)
    private Integer status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private PaymentType paymentType;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserPicture userPicture;

    @OneToMany(mappedBy = "publisher")
    private Set<Job> jobs;

    @OneToMany(mappedBy = "userVoter")
    private Set<Rating> ratingsMade;

    @OneToMany(mappedBy = "userTarget")
    private Set<Rating> ratingsRecevied;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Set<Rating> getRatingsMade() {
        return ratingsMade;
    }

    public void setRatingsMade(Set<Rating> ratingsMade) {
        this.ratingsMade = ratingsMade;
    }

    public Set<Rating> getRatingsRecevied() {
        return ratingsRecevied;
    }

    public void setRatingsRecevied(Set<Rating> ratingsRecevied) {
        this.ratingsRecevied = ratingsRecevied;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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
