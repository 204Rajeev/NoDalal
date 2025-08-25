package com.example.NoDalal.repository;

import com.example.NoDalal.entity.TaskRejection;
import com.example.NoDalal.entity.TaskRejectionId;
import com.example.NoDalal.entity.TaskValidation;
import com.example.NoDalal.entity.TaskValidationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRejectionRepository extends JpaRepository<TaskRejection, TaskRejectionId> {

    boolean existsByIdTaskIdAndIdRejectedBy(Integer taskId, Integer rejectedBy);
    @Query(value = "SELECT COUNT(*) FROM task_rejections tr WHERE tr.task_id = :taskId",
            nativeQuery = true)
    int countByTaskId(@Param("taskId") Integer taskId);

    @Query(value = "SELECT tr.image_url FROM task_rejections tr WHERE tr.task_id = :taskId",
            nativeQuery = true)
    List<String> getImageUrlByTaskId(@Param("taskId") Integer taskId);


}

