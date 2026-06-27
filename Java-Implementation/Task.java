

import java.io.Serializable;

public class Task implements Serializable {

    private int taskId;
    private String title;
    private String category;
    private User assignedUser;
    private boolean completed;

    public Task(int taskId, String title, String category, User assignedUser) {
        this.taskId = taskId;
        this.title = title;
        this.category = category;
        this.assignedUser = assignedUser;
        this.completed = false;
    }

    // Getters
    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public boolean isCompleted() {
        return completed;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public void markCompleted() {
        completed = true;
    }

    public void markPending() {
        completed = false;
    }

    @Override
    public String toString() {

        String status = completed ? "Completed" : "Pending";

        return "----------------------------------\n"
                + "Task ID      : " + taskId + "\n"
                + "Title        : " + title + "\n"
                + "Category     : " + category + "\n"
                + "Assigned To  : " + assignedUser.getUsername() + "\n"
                + "Status       : " + status + "\n"
                + "----------------------------------";
    }
}
