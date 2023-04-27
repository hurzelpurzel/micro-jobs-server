package com.andreidodu.model;

import com.andreidodu.model.common.ModelCommon;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "mj_payment_type")
@EntityListeners(AuditingEntityListener.class)
public class PaymentType extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "paypal_email", nullable = false, unique = true)
    private String paypalEmail;
    @Column(name = "type", nullable = false)
    private Integer type;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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
