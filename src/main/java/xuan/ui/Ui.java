package xuan.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import xuan.task.Deadline;
import xuan.task.Task;
import xuan.task.TaskList;

/**
 * Handles interaction with the user, including reading commands
 * and displaying messages.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Creates a Ui object for reading user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the application banner.
     */
    public void showBanner() {
        String banner = "__  __  _   _    _    _   _\n"
                + "\\ \\/ / | | | |  / \\  | \\ | |\n"
                + " \\  /  | | | | / _ \\ |  \\| |\n"
                + " /  \\  | |_| |/ ___ \\| |\\  |\n"
                + "/_/\\_\\  \\___//_/   \\_\\_| \\_|\n";

        System.out.println(banner);
    }

    /**
     * Reads and returns a command entered by the user.
     *
     * @return the user's command
     */
    public String readCommand() {
        System.out.print("You: ");
        return scanner.nextLine();
    }

    /**
     * Displays the greeting message.
     */
    public void showGreeting() {
        System.out.println("Xuan: Hello! I'm Xuan.");
        System.out.println("Xuan: What can I do for you? \n");
    }

    /**
     * Displays the goodbye message.
     */
    public void showBye() {
        System.out.println("Xuan: Bye. Hope to see you again soon!");
    }

    /**
     * Displays the specified message.
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param taskList the task list to display
     */
    public void showTaskList(TaskList taskList) {
        System.out.println("Xuan: Here are your tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("      " + (i + 1) + ". " + taskList.get(i));
        }
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println("Xuan: " + message);
    }

    /**
     * Displays information about a newly added task.
     *
     * @param task the added task
     * @param taskCount the current number of tasks
     */
    public void showAddedTask(Task task, int taskCount) {
        System.out.println("Xuan: Got it. I've added this task:");
        System.out.println("      " + task);
        System.out.println("      Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays information about a deleted task.
     *
     * @param task the deleted task
     * @param taskCount the current number of tasks
     */
    public void showDeletedTask(Task task, int taskCount) {
        System.out.println("Xuan: Noted. I've removed this task:");
        System.out.println("      " + task);
        System.out.println("      Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays a task that has been marked as done.
     *
     * @param task the marked task
     */
    public void showMarkedTask(Task task) {
        System.out.println("Xuan: Nice! I've marked this task as done:");
        System.out.println("      " + task);
    }

    /**
     * Displays a task that has been marked as not done.
     *
     * @param task the unmarked task
     */
    public void showUnmarkedTask(Task task) {
        System.out.println("Xuan: OK, I've marked this task as not done yet:");
        System.out.println("      " + task);
    }

    /**
     * Shows the deadlines that occur on the specified date.
     *
     * @param targetDate the date of the deadlines to display
     * @param deadlines the list of deadlines occurring on the specified date
     */
    public void showDeadlinesOnDate(LocalDate targetDate, ArrayList<Deadline> deadlines) {
        System.out.println("Xuan: Here are the deadlines on " + targetDate + ":");

        if (deadlines.isEmpty()) {
            System.out.println("      No deadlines found.");
        } else {
            for (int i = 0; i < deadlines.size(); i++) {
                System.out.println("      " + (i + 1) + ". " + deadlines.get(i));
            }
        }
    }

    /**
     * Displays the tasks that match the given search keyword.
     *
     * @param tasks the matching tasks to display
     */
    public void showMatchingTasks(ArrayList<Task> tasks) {
        System.out.println("Xuan: Here are the matching tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("      " + (i + 1) + ". " + tasks.get(i));
        }
    }
}
