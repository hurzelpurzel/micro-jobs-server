package com.andreidodu.model.message;

import com.andreidodu.model.Job;
import com.andreidodu.model.common.ModelCommon;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "mj_room", uniqueConstraints =
@UniqueConstraint(columnNames = {"title"}))
@EntityListeners(AuditingEntityListener.class)
public class Room extends ModelCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "status", nullable = false)
    private Integer status;
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Message> messages;
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Participant> participants;
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
    @Column(name = "picture_name")
    private String pictureName;

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }


    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", messages=" + messages +
                ", participants=" + participants +
                '}';
    }
}
