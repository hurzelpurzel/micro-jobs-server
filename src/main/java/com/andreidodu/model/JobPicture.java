package com.andreidodu.model;

import com.andreidodu.model.common.ModelCommon;
import jakarta.persistence.*;
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

    @Column(name = "picture_name", nullable = false)
    private String pictureName;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "JobPicture{" +
                "id=" + id +
                ", picture=" + pictureName +
                ", job=" + job.getId() +
                '}';
    }
}
