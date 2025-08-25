package com.example.NoDalal.repository;

import com.example.NoDalal.entity.Task;
import com.example.NoDalal.entity.TaskValidation;
import com.example.NoDalal.entity.TaskValidationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskValidationRepository extends JpaRepository<TaskValidation, TaskValidationId> {


    @Query(value = "SELECT COUNT(*) FROM task_validations tv WHERE tv.task_id = :taskId",
            nativeQuery = true)
    int countByTaskId(@Param("taskId") Integer taskId);

}
