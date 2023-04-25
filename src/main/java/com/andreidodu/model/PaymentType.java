package com.andreidodu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mj_payment_type")
public class PaymentType extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "paypal_email", nullable = false, unique = true)
    private String paypalEmail;
    @Column(name = "type", nullable = false)
    private Integer type;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PaymentType{" +
                "id=" + id +
                ", paypalEmail='" + paypalEmail + '\'' +
                ", user=" + user.getId() +
                '}';
    }
}
