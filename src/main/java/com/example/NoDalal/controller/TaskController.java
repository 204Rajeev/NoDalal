package com.example.NoDalal.controller;

import com.example.NoDalal.entity.Task;
import com.example.NoDalal.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Save a new task
    @PostMapping("save")
    public ResponseEntity<?> saveTask(@RequestBody Task task, HttpServletRequest request) {

        Integer userId = (Integer) request.getAttribute("userId");

        if (task.getTaskCreatedByName() == null || task.getTaskCreatedByName().isBlank()) {
            return ResponseEntity.badRequest().body("taskCreatedByName is required");
        }
        task.setTaskCreatedBy(userId);
        Task savedTask = taskService.saveTask(task);

        return ResponseEntity.ok(savedTask);
    }


    // Delete task by ID
    @DeleteMapping("delete/{id}")
    public Task deleteTask(@PathVariable int id) {
        return taskService.deleteTask(id);
    }

    // Get all tasks for a specific user
    @GetMapping("forUser")
    public List<Task> getTasksForAuthenticatedUser(HttpServletRequest request) {
        // Get userId from AuthFilter
        Integer userId = (Integer) request.getAttribute("userId");
        return taskService.getTasksByUserId(userId);
    }

    // get count of task except for the current user
    @GetMapping("exceptUserCount")
    public int getTaskCountExceptCurrentUser(HttpServletRequest request) {
        // Get the authenticated user's ID from the request
        Integer userId = (Integer) request.getAttribute("userId");
        return taskService.getTaskCountExceptCurrentUser(userId);
    }

    // get count of task except for the current user
    @GetMapping("exceptUser")
    public List<Task> getTaskExceptCurrentUser(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        return taskService.getTaskExceptCurrentUser(userId);
    }

    @PostMapping("validate")
    public ResponseEntity<String> validateTask(HttpServletRequest request, @RequestBody Integer taskId){
        Integer userId = (Integer) request.getAttribute("userId");

        // Call service to validate task
        boolean success = taskService.validateTask(taskId, userId);

        if (success) {
            return ResponseEntity.ok("Task validated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Task already validated by this user.");
        }
    }

    @PostMapping("reject")
    public ResponseEntity<String> rejectTask(
            HttpServletRequest request,
            @RequestParam("taskId") Integer taskId,
            @RequestParam("rejectReason") String rejectReason,
            @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");

            // Define base path
            String basePath = "C:\\Users\\gsatu\\Documents\\appProjects\\no_dalal\\images\\"
                    + userId + "\\" + taskId;

            // Create directories if not exist
            File dir = new File(basePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Save file with unique name
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File destination = new File(dir, fileName);
            imageFile.transferTo(destination);

            // Store relative/absolute path in DB
            String imagePath = destination.getAbsolutePath();

            boolean success = taskService.rejectTask(taskId, userId, rejectReason, imagePath);

            if (success) {
                return ResponseEntity.ok("Task rejected successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Task already rejected by this user.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error rejecting task: " + e.getMessage());
        }
    }


    @GetMapping("countValidated")
    public ResponseEntity<?> getCountOfValidatedTask(HttpServletRequest request,
                                                     @RequestParam("taskId") Integer taskId) {
        int count = taskService.getValidationCount(taskId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("countRejected")
    public ResponseEntity<?> getCountOfRejectedTask(HttpServletRequest request,
                                                    @RequestParam("taskId") Integer taskId) {
        int count = taskService.getRejectionCount(taskId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("getRejectImages")
    public ResponseEntity<?> getRejectTaskImages(
            HttpServletRequest request,
            @RequestParam("taskId") Integer taskId
    ) {
        try {
            // Fetch all file paths for the task from service (full Windows paths)
            List<String> imageFiles = taskService.getAllImagesForTask(taskId);

            if (imageFiles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No images found for this task");
            }

            // Convert full paths to relative URLs for /images/** handler
            String basePath = "C:\\Users\\gsatu\\Documents\\appProjects\\no_dalal\\images\\";
            List<String> imageUrls = imageFiles.stream()
                    .map(fullPath -> {
                        // Remove base path and replace backslashes with forward slashes
                        String relativePath = fullPath.replace(basePath, "").replace("\\", "/");
                        return "images/" + relativePath;
                    })
                    .toList();

            return ResponseEntity.ok(imageUrls);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving task images: " + e.getMessage());
        }
    }





}
