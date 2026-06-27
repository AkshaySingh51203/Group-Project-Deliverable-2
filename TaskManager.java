import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<User> users = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

    public synchronized void addUser(User user) {
        users.add(user);
    }

    public synchronized void addTask(Task task) {
        tasks.add(task);
    }

    public synchronized void deleteTask(int taskId) {
        tasks.removeIf(t -> t.getTaskId() == taskId);
    }

    public synchronized void markTaskCompleted(int taskId) {
        for (Task t : tasks) {
            if (t.getTaskId() == taskId) {
                t.markCompleted();
                return;
            }
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public User getUserById(int id) {
        for (User u : users) {
            if (u.getUserId() == id) return u;
        }
        return null;
    }

    public void viewUsers() {
        for (User u : users) {
            System.out.println(u);
        }
    }

    public void viewAllTasks() {
        for (Task t : tasks) {
            System.out.println(t);
        }
    }

    // 🔥 THIS IS THE MISSING METHOD
    public void viewTasksByUser(int userId) {
        boolean found = false;

        for (Task t : tasks) {
            if (t.getAssignedUser().getUserId() == userId) {
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks found for this user.");
        }
    }
}