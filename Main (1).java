import java.util.*;

public class Main {

    static class User {
        int id;
        String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return id + " - " + name;
        }
    }

    static class Task {
        int id;
        String title;
        String description;
        String assignedUser;
        String priority;
        String dueDate;
        boolean completed;

        Task(int id, String title, String description, String assignedUser,
             String priority, String dueDate) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.assignedUser = assignedUser;
            this.priority = priority;
            this.dueDate = dueDate;
            this.completed = false;
        }

        public String toString() {
            return "\nTask ID: " + id +
                    "\nTitle: " + title +
                    "\nDescription: " + description +
                    "\nAssigned User: " + assignedUser +
                    "\nPriority: " + priority +
                    "\nDue Date: " + dueDate +
                    "\nStatus: " + (completed ? "Completed" : "Pending") +
                    "\n-----------------------------------";
        }
    }

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Task> tasks = new ArrayList<>();
    static HashMap<Integer, User> users = new HashMap<>();
    static int nextTaskId = 1;
    static int nextUserId = 1;

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n=== Collaborative To-Do List ===");
            System.out.println("1. Add User");
            System.out.println("2. View Users");
            System.out.println("3. Add Task");
            System.out.println("4. View Tasks");
            System.out.println("5. Update Task");
            System.out.println("6. Delete Task");
            System.out.println("7. Mark Completed");
            System.out.println("8. Search Task");
            System.out.println("9. Exit");
            System.out.print("Choice: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (ch) {
                case 1 -> addUser();
                case 2 -> viewUsers();
                case 3 -> addTask();
                case 4 -> viewTasks();
                case 5 -> updateTask();
                case 6 -> deleteTask();
                case 7 -> markCompleted();
                case 8 -> searchTask();
                case 9 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addUser() {
        System.out.print("User Name: ");
        String name = sc.nextLine();
        users.put(nextUserId, new User(nextUserId, name));
        nextUserId++;
        System.out.println("User added.");
    }

    static void viewUsers() {
        if (users.isEmpty()) {
            System.out.println("No users.");
            return;
        }
        users.values().forEach(System.out::println);
    }

    static void addTask() {
        if (users.isEmpty()) {
            System.out.println("Add a user first.");
            return;
        }
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();

        viewUsers();
        System.out.print("Assign User ID: ");
        int uid = Integer.parseInt(sc.nextLine());

        if (!users.containsKey(uid)) {
            System.out.println("Invalid user.");
            return;
        }

        System.out.print("Priority: ");
        String pri = sc.nextLine();

        System.out.print("Due Date: ");
        String due = sc.nextLine();

        tasks.add(new Task(nextTaskId++, title, desc, users.get(uid).name, pri, due));
        System.out.println("Task added.");
    }

    static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    static void updateTask() {
        System.out.print("Task ID: ");
        int id = Integer.parseInt(sc.nextLine());

        for (Task t : tasks) {
            if (t.id == id) {
                System.out.print("New Title: ");
                t.title = sc.nextLine();
                System.out.print("New Description: ");
                t.description = sc.nextLine();
                viewUsers();
                System.out.print("Assign User ID: ");
                int uid = Integer.parseInt(sc.nextLine());
                if (users.containsKey(uid)) t.assignedUser = users.get(uid).name;
                System.out.print("Priority: ");
                t.priority = sc.nextLine();
                System.out.print("Due Date: ");
                t.dueDate = sc.nextLine();
                System.out.println("Updated.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    static void deleteTask() {
        System.out.print("Task ID: ");
        int id = Integer.parseInt(sc.nextLine());
        tasks.removeIf(t -> t.id == id);
        System.out.println("Delete operation completed.");
    }

    static void markCompleted() {
        System.out.print("Task ID: ");
        int id = Integer.parseInt(sc.nextLine());
        for (Task t : tasks) {
            if (t.id == id) {
                t.completed = true;
                System.out.println("Marked completed.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    static void searchTask() {
        System.out.print("Keyword: ");
        String key = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Task t : tasks) {
            if (t.title.toLowerCase().contains(key) ||
                t.description.toLowerCase().contains(key)) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) System.out.println("No matching tasks.");
    }
}
