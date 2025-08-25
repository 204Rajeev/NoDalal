package com.example.NoDalal.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    @Column(name = "task_created_by_name", nullable = false)
    private String taskCreatedByName;

    private Timestamp taskDoneOn;

    @Column(name = "task_created_by", nullable = false)
    private Integer taskCreatedBy;

    @Column(name = "task_done_for", nullable = false)
    private String taskDoneFor; // "Morning", "Night", or "Morning,Night"

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    public void setTaskCreatedByName(String taskCreatedByName) {
        this.taskCreatedByName = taskCreatedByName;
    }


    public String getTaskCreatedByName() {
        return taskCreatedByName;
    }


    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskCreatedBy() {
        return taskCreatedBy;
    }

    public void setTaskCreatedBy(Integer taskCreatedBy) {
        this.taskCreatedBy = taskCreatedBy;
    }

    public Timestamp getTaskDoneOn() {
        return taskDoneOn;
    }

    public void setTaskDoneOn(Timestamp taskDoneOn) {
        this.taskDoneOn = taskDoneOn;
    }

    public String getTaskDoneFor() {
        return taskDoneFor;
    }

    public void setTaskDoneFor(String taskDoneFor) {
        this.taskDoneFor = taskDoneFor;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "{" +
                "taskId=" + taskId +
                ", taskCreatedBy=" + taskCreatedBy +
                ", taskCreatedByName='" + taskCreatedByName + '\'' +
                ", taskDoneOn=" + taskDoneOn +
                ", taskDoneFor='" + taskDoneFor + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
