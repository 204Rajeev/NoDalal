package com.example.NoDalal.repository;

import com.example.NoDalal.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByTaskCreatedBy(Integer userId);

    @Query(value = "SELECT COUNT(*) FROM tasks t " +
            "LEFT JOIN task_validations tv " +
            "  ON t.task_id = tv.task_id AND tv.validated_by = :userId " +
            "LEFT JOIN task_rejections tr " +
            "  ON tr.task_id = t.task_id AND tr.rejected_by = :userId " +
            "WHERE t.task_created_by != :userId " +
            "AND tv.task_id IS NULL " +
            "AND tr.task_id IS NULL",
            nativeQuery = true)
    int countTasksNotCreatedByUserNotValidatedAndRejected(@Param("userId") Integer userId);


    // Get tasks not created by user and not validated/ rejected
    // Get tasks not created by user and not validated/rejected by this user
    @Query(value = "SELECT t.* FROM tasks t " +
            "LEFT JOIN task_validations tv " +
            "  ON t.task_id = tv.task_id AND tv.validated_by = :userId " +
            "LEFT JOIN task_rejections tr " +
            "  ON t.task_id = tr.task_id AND tr.rejected_by = :userId " +
            "WHERE t.task_created_by != :userId " +
            "AND tv.task_id IS NULL " +
            "AND tr.task_id IS NULL",
            nativeQuery = true)
    List<Task> tasksNotCreatedByUserNotValidatedAndRejected(@Param("userId") Integer userId);

}
