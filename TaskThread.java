public class TaskThread extends Thread {

    private TaskManager taskManager;
    private Task task;

    public TaskThread(TaskManager taskManager, Task task) {
        this.taskManager = taskManager;
        this.task = task;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName()
                + " is adding task: " + task.getTitle());

        taskManager.addTask(task);

        try {
            // Simulate processing time
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        System.out.println(Thread.currentThread().getName()
                + " finished adding Task ID: "
                + task.getTaskId());
    }
}