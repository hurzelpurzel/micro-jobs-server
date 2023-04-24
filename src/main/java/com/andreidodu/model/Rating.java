package com.andreidodu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mj_rating", uniqueConstraints =
@UniqueConstraint(columnNames = {"user_voter_id", "user_target_id", "job_instance_id"}))
public class Rating extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_voter_id", referencedColumnName = "id", nullable = false)
    private User userVoter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_target_id", referencedColumnName = "id", nullable = false)
    private User userTarget;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_instance_id", referencedColumnName = "id", nullable = false)
    private JobInstance jobInstance;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserVoter() {
        return userVoter;
    }

    public void setUserVoter(User userVoter) {
        this.userVoter = userVoter;
    }

    public User getUserTarget() {
        return userTarget;
    }

    public void setUserTarget(User userTarget) {
        this.userTarget = userTarget;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public JobInstance getJobInstance() {
        return jobInstance;
    }

    public void setJobInstance(JobInstance jobInstance) {
        this.jobInstance = jobInstance;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", userVoter=" + userVoter.getId() +
                ", userTarget=" + userTarget.getId() +
                ", rating=" + rating +
                ", jobInstance=" + jobInstance.getId() +
                '}';
    }
}
