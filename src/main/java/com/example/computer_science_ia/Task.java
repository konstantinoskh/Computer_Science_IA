package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)//To serialise non-null values
public class Task {
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "dueDate")
    private LocalDate dueDate;
    @JsonProperty(value = "status")
    private TaskStatus status;
    @JsonProperty(value = "priority")
    private TaskPriority priority;

    public Task(String title, String description, LocalDate dueDate, TaskStatus taskStatus, TaskPriority taskPriority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = taskStatus;
        this.priority = taskPriority;
    }

    public Task(){ // A task can be initialised with no data
        this.title = "";
        this.description = "";
        this.dueDate = LocalDate.now();
        this.status = TaskStatus.NOT_STARTED;
        this.priority = TaskPriority.MEDIUM;
    }

    public enum TaskStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    public enum TaskPriority {
        HIGH,
        MEDIUM,
        LOW
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}