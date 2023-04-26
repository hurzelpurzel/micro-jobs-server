package com.andreidodu.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Entity
@Table(name = "mj_job")
@EntityListeners(AuditingEntityListener.class)
public class Job extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false, unique = true)
    private String description;

    @Column(name = "type", nullable = false)
    private Integer type;

    @ManyToOne
    @JoinColumn(name = "user_publisher_id", nullable = false)
    private User publisher;

    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "job")
    private Set<JobInstance> jobInstanceSet;

    @OneToMany(mappedBy = "job")
    private Set<JobPicture> jobPictureSet;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Set<JobInstance> getJobInstanceSet() {
        return jobInstanceSet;
    }

    public void setJobInstanceSet(Set<JobInstance> jobInstanceSet) {
        this.jobInstanceSet = jobInstanceSet;
    }

    public Set<JobPicture> getJobPictureSet() {
        return jobPictureSet;
    }

    public void setJobPictureSet(Set<JobPicture> jobPictureSet) {
        this.jobPictureSet = jobPictureSet;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", jobInstanceSet=" + jobInstanceSet.size() +
                ", jobPictureSet=" + jobPictureSet.size() +
                '}';
    }
}
