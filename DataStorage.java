import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class DataStorage {

    // Save users to file
    public static void saveUsers(List<User> users, String fileName) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            out.writeObject(users);
            System.out.println("Users saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Load users from file
    @SuppressWarnings("unchecked")
    public static List<User> loadUsers(String fileName) {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            return (List<User>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing user data found.");
            return null;
        }
    }

    // Save tasks to file
    public static void saveTasks(List<Task> tasks, String fileName) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            out.writeObject(tasks);
            System.out.println("Tasks saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Load tasks from file
    @SuppressWarnings("unchecked")
    public static List<Task> loadTasks(String fileName) {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            return (List<Task>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing task data found.");
            return null;
        }
    }
}