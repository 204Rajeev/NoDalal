package com.example.NoDalal.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Entity
@Table(name = "task_validations")
public class TaskValidation {

    @EmbeddedId
    private TaskValidationId id;

    @CreationTimestamp
    @Column(name = "validated_at", nullable = false, updatable = false)
    private Timestamp validatedAt;

    public TaskValidation() {}

    public TaskValidation(TaskValidationId id) {
        this.id = id;
    }

    public TaskValidationId getId() {
        return id;
    }

    public void setId(TaskValidationId id) {
        this.id = id;
    }

    public Timestamp getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(Timestamp validatedAt) {
        this.validatedAt = validatedAt;
    }
}
