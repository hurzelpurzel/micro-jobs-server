package com.andreidodu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mj_message", uniqueConstraints =
@UniqueConstraint(columnNames = {"user_from_id", "user_to_id", "insert_date"}))
public class Message extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_from_id", nullable = false)
    private User userFrom;

    @ManyToOne
    @JoinColumn(name = "user_to_id", nullable = false)
    private User userTo;

    @Column(name = "message", nullable = false)
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userFrom=" + userFrom.getId() +
                ", userTo=" + userTo.getId() +
                ", message='" + message + '\'' +
                '}';
    }
}
