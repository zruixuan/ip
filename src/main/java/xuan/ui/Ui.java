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
    private final Scanner scanner;

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
     * Returns the greeting message.
     *
     * @return the greeting message
     */
    public String getGreetingMessage() {
        return "Xuan: Hello! I'm Xuan.\n"
                + "Xuan: What can I do for you?";
    }

    /**
     * Displays the greeting message.
     */
    public void showGreeting() {
        showMessage(
                "Xuan: Hello! I'm Xuan.",
                "Xuan: What can I do for you?"
        );
        System.out.println();
    }

    /**
     * Returns the goodbye message.
     *
     * @return the goodbye message
     */
    public String getByeMessage() {
        return "Xuan: Bye. Hope to see you again soon!";
    }

    /**
     * Displays the goodbye message.
     */
    public void showBye() {
        System.out.println(getByeMessage());
    }

    /**
     * Displays one or more specified messages.
     *
     * @param messages the messages to display
     */
    public void showMessage(String... messages) {
        for (String message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Returns all tasks in the task list as a formatted message.
     *
     * @param taskList the task list to format
     * @return the formatted task list
     */
    public String getTaskListMessage(TaskList taskList) {
        StringBuilder message = new StringBuilder(
                "Xuan: Here are your tasks:");

        for (int i = 0; i < taskList.size(); i++) {
            message.append("\n      ")
                    .append(i + 1)
                    .append(". ")
                    .append(taskList.get(i));
        }

        return message.toString();
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param taskList the task list to display
     */
    public void showTaskList(TaskList taskList) {
        System.out.println(getTaskListMessage(taskList));
    }

    /**
     * Returns a formatted error message.
     *
     * @param message the error message
     * @return the formatted error message
     */
    public String getErrorMessage(String message) {
        return "Xuan: " + message;
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println(getErrorMessage(message));
    }

    /**
     * Returns information about a newly added task.
     *
     * @param task the added task
     * @param taskCount the current number of tasks
     * @return the formatted message about the added task
     */
    public String getAddedTaskMessage(Task task, int taskCount) {
        return "Xuan: Got it. I've added this task:\n"
                + "      " + task + "\n"
                + "      Now you have " + taskCount
                + " tasks in the list.";
    }

    /**
     * Displays information about a newly added task.
     *
     * @param task the added task
     * @param taskCount the current number of tasks
     */
    public void showAddedTask(Task task, int taskCount) {
        System.out.println(getAddedTaskMessage(task, taskCount));
    }

    /**
     * Returns information about a deleted task.
     *
     * @param task the deleted task
     * @param taskCount the current number of tasks
     * @return the formatted message about the deleted task
     */
    public String getDeletedTaskMessage(Task task, int taskCount) {
        return "Xuan: Noted. I've removed this task:\n"
                + "      " + task + "\n"
                + "      Now you have " + taskCount
                + " tasks in the list.";
    }

    /**
     * Displays information about a deleted task.
     *
     * @param task the deleted task
     * @param taskCount the current number of tasks
     */
    public void showDeletedTask(Task task, int taskCount) {
        System.out.println(getDeletedTaskMessage(task, taskCount));
    }

    /**
     * Returns a message for a task that has been marked as done.
     *
     * @param task the marked task
     * @return the formatted message about the marked task
     */
    public String getMarkedTaskMessage(Task task) {
        return "Xuan: Nice! I've marked this task as done:\n"
                + "      " + task;
    }

    /**
     * Displays a task that has been marked as done.
     *
     * @param task the marked task
     */
    public void showMarkedTask(Task task) {
        System.out.println(getMarkedTaskMessage(task));
    }

    /**
     * Returns a message for a task that has been marked as not done.
     *
     * @param task the unmarked task
     * @return the formatted message about the unmarked task
     */
    public String getUnmarkedTaskMessage(Task task) {
        return "Xuan: OK, I've marked this task as not done yet:\n"
                + "      " + task;
    }

    /**
     * Displays a task that has been marked as not done.
     *
     * @param task the unmarked task
     */
    public void showUnmarkedTask(Task task) {
        System.out.println(getUnmarkedTaskMessage(task));
    }

    /**
     * Returns the deadlines that occur on the specified date.
     *
     * @param targetDate the date of the deadlines
     * @param deadlines the deadlines occurring on the specified date
     * @return the formatted deadline list
     */
    public String getDeadlinesOnDateMessage(
            LocalDate targetDate, ArrayList<Deadline> deadlines) {
        StringBuilder message = new StringBuilder(
                "Xuan: Here are the deadlines on "
                        + targetDate + ":");

        if (deadlines.isEmpty()) {
            message.append("\n      No deadlines found.");
        } else {
            for (int i = 0; i < deadlines.size(); i++) {
                message.append("\n      ")
                        .append(i + 1)
                        .append(". ")
                        .append(deadlines.get(i));
            }
        }

        return message.toString();
    }

    /**
     * Shows the deadlines that occur on the specified date.
     *
     * @param targetDate the date of the deadlines to display
     * @param deadlines the list of deadlines occurring on the specified date
     */
    public void showDeadlinesOnDate(
            LocalDate targetDate, ArrayList<Deadline> deadlines) {
        System.out.println(
                getDeadlinesOnDateMessage(targetDate, deadlines));
    }

    /**
     * Returns the tasks that match the given search keyword.
     *
     * @param tasks the matching tasks
     * @return the formatted matching tasks
     */
    public String getMatchingTasksMessage(ArrayList<Task> tasks) {
        StringBuilder message = new StringBuilder(
                "Xuan: Here are the matching tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            message.append("\n      ")
                    .append(i + 1)
                    .append(". ")
                    .append(tasks.get(i));
        }

        return message.toString();
    }

    /**
     * Displays the tasks that match the given search keyword.
     *
     * @param tasks the matching tasks to display
     */
    public void showMatchingTasks(ArrayList<Task> tasks) {
        System.out.println(getMatchingTasksMessage(tasks));
    }
}
