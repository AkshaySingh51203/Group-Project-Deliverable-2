import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        int choice;

        do {

            System.out.println("\n==============================");
            System.out.println(" Collaborative To-Do List ");
            System.out.println("==============================");
            System.out.println("1. Add User");
            System.out.println("2. View Users");
            System.out.println("3. Add Task");
            System.out.println("4. View All Tasks");
            System.out.println("5. View Tasks by User");
            System.out.println("6. Mark Task Completed");
            System.out.println("7. Delete Task");
            System.out.println("8. Save Data");
            System.out.println("9. Load Data");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Username: ");
                    String username = sc.nextLine();

                    taskManager.addUser(new User(userId, username));
                    break;

                case 2:

                    taskManager.viewUsers();
                    break;

                case 3:

                    if (taskManager.getUsers().isEmpty()) {
                        System.out.println("Please add a user first.");
                        break;
                    }

                    System.out.print("Enter Task ID: ");
                    int taskId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Task Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Category: ");
                    String category = sc.nextLine();

                    System.out.print("Assign to User ID: ");
                    int assignedUserId = sc.nextInt();

                    User assignedUser = taskManager.getUserById(assignedUserId);

                    if (assignedUser == null) {
                        System.out.println("User not found.");
                        break;
                    }

                    Task task = new Task(taskId, title, category, assignedUser);

                    TaskThread thread = new TaskThread(taskManager, task);
                    thread.start();

                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;

                case 4:

                    taskManager.viewAllTasks();
                    break;

                case 5:

                    System.out.print("Enter User ID: ");
                    int id = sc.nextInt();

                    taskManager.viewTasksByUser(id);
                    break;

                case 6:

                    System.out.print("Enter Task ID: ");
                    int completedId = sc.nextInt();

                    taskManager.markTaskCompleted(completedId);
                    break;

                case 7:

                    System.out.print("Enter Task ID: ");
                    int deleteId = sc.nextInt();

                    taskManager.deleteTask(deleteId);
                    break;

                case 8:

                    DataStorage.saveUsers(taskManager.getUsers(), "users.dat");
                    DataStorage.saveTasks(taskManager.getTasks(), "tasks.dat");
                    break;

                case 9:

                    var loadedUsers = DataStorage.loadUsers("users.dat");
                    var loadedTasks = DataStorage.loadTasks("tasks.dat");

                    if (loadedUsers != null) {
                        taskManager.getUsers().clear();
                        taskManager.getUsers().addAll(loadedUsers);
                    }

                    if (loadedTasks != null) {
                        taskManager.getTasks().clear();
                        taskManager.getTasks().addAll(loadedTasks);
                    }

                    System.out.println("Data loaded successfully.");
                    break;

                case 0:

                    System.out.println("Thank you for using the application.");
                    break;

                default:

                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        sc.close();
    }
}