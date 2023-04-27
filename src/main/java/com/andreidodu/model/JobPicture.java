package com.andreidodu.model;

import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Arrays;

@Entity
@Table(name = "mj_job_picture")
@EntityListeners(AuditingEntityListener.class)
public class JobPicture extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "picture", nullable = false)
    private Byte[] picture;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }


    @Override
    public String toString() {
        return "JobPicture{" +
                "id=" + id +
                ", picture=" + Arrays.toString(picture) +
                ", job=" + job.getId() +
                '}';
    }
}
