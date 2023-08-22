package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)//To serialise non-null values
public class TaskList {

    private final ArrayList<Task> TASKS;

    public TaskList() {
        TASKS = new ArrayList<>();
    }

    public boolean taskExists(Task task){
        for (Task existingTask : TASKS) {
            if (existingTask.getTitle().equals(task.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public Task getTaskByTitle(String title) {
        for (Task task : TASKS) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        return null;
    }

    @JsonIgnore
    public boolean isEmpty(){
        return TASKS.isEmpty();
    }

    public void addTask(Task task) {
        TASKS.add(task);
    }

    public List<Task> getTasks() {
        return TASKS;
    }

    public void sortTasksBasedOnPriority(){
        ArrayList<Task> highPriority = new ArrayList<>();
        ArrayList<Task> mediumPriority = new ArrayList<>();
        ArrayList<Task> lowPriority = new ArrayList<>();


        for (Task task: TASKS){
            if (task.getPriority() == Task.TaskPriority.HIGH){
                highPriority.add(task);
            }else if (task.getPriority() == Task.TaskPriority.MEDIUM){
                mediumPriority.add(task);
            }else {
                lowPriority.add(task);
            }
        }

        TASKS.clear();
        TASKS.addAll(highPriority);
        TASKS.addAll(mediumPriority);
        TASKS.addAll(lowPriority);
    }

    public void sortTasksBasedOnDueDate(){
        quickSort(TASKS, 0, TASKS.size() - 1);
    }

    public static void quickSort(List<Task> tasks, int low, int high) {
        if (low < high) {
            int pi = partition(tasks, low, high);

            quickSort(tasks, low, pi - 1);
            quickSort(tasks, pi + 1, high);
        }
    }

    public static int partition(List<Task> tasks, int low, int high) {
        LocalDate pivot = tasks.get(high).getDueDate();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (tasks.get(j).getDueDate().isBefore(pivot)) {
                i++;
                swap(tasks, i, j);
            }
        }
        swap(tasks, i + 1, high);
        return i + 1;
    }

    public static void swap(List<Task> tasks, int i, int j) {
        Task temp = tasks.get(i);
        tasks.set(i, tasks.get(j));
        tasks.set(j, temp);
    }

    public void deleteNoteByTitle(String title) {
        TASKS.removeIf(task -> task.getTitle().equals(title));
    }
}
