package com.example.NoDalal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_rejections")
public class TaskRejection {

    @EmbeddedId
    private TaskRejectionId id;

    @Column(name = "rejection_reason", length = 200)
    private String rejectionReason;

    @Column(name = "image_url", length = 500, nullable = false)
    private String imageUrl;

    @Column(name = "rejected_at", insertable = false, updatable = false)
    private LocalDateTime rejectedAt;

    public TaskRejection() {}

    public TaskRejection(Integer taskId, Integer userId, String rejectReason, String imagePath) {
        this.id = new TaskRejectionId(taskId, userId);
        this.rejectionReason = rejectReason;
        this.imageUrl = imagePath;
        // rejectedAt will be auto-set by DB
    }

    public TaskRejectionId getId() {
        return id;
    }

    public void setId(TaskRejectionId id) {
        this.id = id;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }
}
