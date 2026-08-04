package com.avikaar.taskmanager.service;

import com.avikaar.taskmanager.dto.DashboardStats;
import com.avikaar.taskmanager.dto.TaskRequest;
import com.avikaar.taskmanager.dto.TaskResponse;
import com.avikaar.taskmanager.entity.Task;
import com.avikaar.taskmanager.entity.TaskStatus;
import com.avikaar.taskmanager.entity.User;
import com.avikaar.taskmanager.exception.ResourceNotFoundException;
import com.avikaar.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskResponse> getAllTasks(User user) {
        return taskRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    public TaskResponse getTaskById(Long taskId, User user) {
        Task task = findTaskForUser(taskId, user);
        return TaskResponse.fromEntity(task);
    }

    @Transactional
    public TaskResponse createTask(TaskRequest request, User user) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING)
                .dueDate(request.getDueDate())
                .user(user)
                .build();

        Task saved = taskRepository.save(task);
        return TaskResponse.fromEntity(saved);
    }

    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest request, User user) {
        Task task = findTaskForUser(taskId, user);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        task.setDueDate(request.getDueDate());

        Task updated = taskRepository.save(task);
        return TaskResponse.fromEntity(updated);
    }

    @Transactional
    public TaskResponse updateTaskStatus(Long taskId, TaskStatus status, User user) {
        Task task = findTaskForUser(taskId, user);
        task.setStatus(status);
        Task updated = taskRepository.save(task);
        return TaskResponse.fromEntity(updated);
    }

    @Transactional
    public void deleteTask(Long taskId, User user) {
        Task task = findTaskForUser(taskId, user);
        taskRepository.delete(task);
    }

    public DashboardStats getDashboardStats(User user) {
        long total = taskRepository.countByUserId(user.getId());
        long pending = taskRepository.countByUserIdAndStatus(user.getId(), TaskStatus.PENDING);
        long inProgress = taskRepository.countByUserIdAndStatus(user.getId(), TaskStatus.IN_PROGRESS);
        long completed = taskRepository.countByUserIdAndStatus(user.getId(), TaskStatus.COMPLETED);

        double completionRate = total > 0 ? (completed * 100.0) / total : 0.0;

        return DashboardStats.builder()
                .totalTasks(total)
                .pendingTasks(pending)
                .inProgressTasks(inProgress)
                .completedTasks(completed)
                .completionRate(Math.round(completionRate * 10.0) / 10.0)
                .build();
    }

    private Task findTaskForUser(Long taskId, User user) {
        return taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
    }
}
