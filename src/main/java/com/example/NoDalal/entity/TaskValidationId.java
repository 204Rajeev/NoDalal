package com.example.NoDalal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TaskValidationId implements Serializable {

    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "validated_by")
    private Integer validatedBy;

    public TaskValidationId() {}

    public TaskValidationId(Integer taskId, Integer validatedBy) {
        this.taskId = taskId;
        this.validatedBy = validatedBy;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(Integer validatedBy) {
        this.validatedBy = validatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskValidationId)) return false;
        TaskValidationId that = (TaskValidationId) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(validatedBy, that.validatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, validatedBy);
    }
}
