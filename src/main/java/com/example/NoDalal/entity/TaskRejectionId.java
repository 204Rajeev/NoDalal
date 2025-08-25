package com.example.NoDalal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TaskRejectionId implements Serializable {

    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "rejected_by")
    private Integer rejectedBy;

    public TaskRejectionId() {}

    public TaskRejectionId(Integer taskId, Integer rejectedBy) {
        this.taskId = taskId;
        this.rejectedBy = rejectedBy;
    }

    // Getters & Setters
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(Integer rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    // equals and hashCode (important for composite key)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskRejectionId)) return false;
        TaskRejectionId that = (TaskRejectionId) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(rejectedBy, that.rejectedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, rejectedBy);
    }
}