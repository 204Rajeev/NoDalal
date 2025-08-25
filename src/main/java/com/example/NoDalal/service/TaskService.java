package com.example.NoDalal.service;


import com.example.NoDalal.entity.Task;
import com.example.NoDalal.entity.TaskRejection;
import com.example.NoDalal.entity.TaskValidation;
import com.example.NoDalal.entity.TaskValidationId;
import com.example.NoDalal.repository.TaskRejectionRepository;
import com.example.NoDalal.repository.TaskRepository;
import com.example.NoDalal.repository.TaskValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskValidationRepository taskValidationRepository;
    @Autowired
    private  TaskRejectionRepository taskRejectionRepository;

    // Save a task
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // Get all tasks
    public List<Task> getAllTasks() {

        return taskRepository.findAll();
    }

    // Delete a task by ID
    public Task deleteTask(int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            taskRepository.deleteById(id);
            return task;
        } else {
            throw new RuntimeException("Task not found with ID: " + id);
        }
    }

    // Get all tasks for a specific user
    public List<Task> getTasksByUserId(int userId) {
        return taskRepository.findByTaskCreatedBy(userId);
    }

    // get count of task except for the current user
    public int getTaskCountExceptCurrentUser(int userId){
        return taskRepository.countTasksNotCreatedByUserNotValidatedAndRejected(userId);
    }

    // get task except for the current user
    public List<Task> getTaskExceptCurrentUser(int userId){
        return taskRepository.tasksNotCreatedByUserNotValidatedAndRejected(userId);
    }

    public boolean validateTask(Integer taskId, Integer userId) {
        TaskValidationId id = new TaskValidationId(taskId, userId);
        if (taskValidationRepository.existsById(id)) {
            return false;
        }
        TaskValidation validation = new TaskValidation(id);
        taskValidationRepository.save(validation);
        return true;
    }

    public boolean rejectTask(Integer taskId, Integer userId, String rejectReason, String imagePath) {
        // check if already rejected
        boolean alreadyRejected = taskRejectionRepository.existsByIdTaskIdAndIdRejectedBy(taskId, userId);
        if (alreadyRejected) {
            return false;
        }

        TaskRejection rejection = new TaskRejection(taskId, userId, rejectReason, imagePath);
        taskRejectionRepository.save(rejection);
        return true;
    }

    public int getValidationCount(Integer taskId) {
        return taskValidationRepository.countByTaskId(taskId);
    }

    public int getRejectionCount(Integer taskId) {
        return taskRejectionRepository.countByTaskId(taskId);
    }

    public List<String> getAllImagesForTask(Integer taskId){
        return taskRejectionRepository.getImageUrlByTaskId(taskId);
    }
}
