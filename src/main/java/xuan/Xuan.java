package xuan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import xuan.exception.XuanException;
import xuan.parser.Parser;
import xuan.storage.Storage;
import xuan.task.Deadline;
import xuan.task.Event;
import xuan.task.Task;
import xuan.task.TaskList;
import xuan.task.Todo;
import xuan.ui.Ui;

/**
 * Runs the Xuan chatbot application.
 */
public class Xuan {

    private final Ui ui;
    private final Parser parser;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Creates a Xuan chatbot and loads saved tasks from storage.
     */
    public Xuan() {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage("./data/xuan.txt");

        TaskList loadedTaskList;

        try {
            loadedTaskList = new TaskList(storage.loadTasks());
        } catch (FileNotFoundException e) {
            loadedTaskList = new TaskList(new ArrayList<>());
        }

        taskList = loadedTaskList;
    }

    /**
     * Starts the command-line version of the Xuan chatbot.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Xuan xuan = new Xuan();
        xuan.run();
    }

    /**
     * Returns Xuan's greeting message.
     *
     * @return Xuan's greeting message
     */
    public String getGreeting() {
        return ui.getGreetingMessage();
    }

    /**
     * Runs the command-line interaction loop.
     */
    private void run() {
        ui.showBanner();
        ui.showGreeting();

        while (true) {
            String input = ui.readCommand();
            String response = getResponse(input);

            ui.showMessage(response);

            if (input.trim().equals("bye")) {
                break;
            }
        }

        ui.close();
    }

    /**
     * Returns Xuan's response to the given user input.
     *
     * @param input the user input
     * @return Xuan's response
     */
    public String getResponse(String input) {
        try {
            String command = parser.getCommandWord(input);
            if (command.equals("bye")) {
                return ui.getByeMessage();
            } else if (command.equals("list")) {
                return ui.getTaskListMessage(taskList);
            } else if (command.equals("find")) {
                String keyword = parser.getFindKeyword(input);
                ArrayList<Task> matchingTasks = taskList.findTasks(keyword);

                return ui.getMatchingTasksMessage(matchingTasks);
            } else if (command.equals("finddate")) {
                LocalDate targetDate = parser.getFindDate(input);
                ArrayList<Deadline> deadlines =
                        taskList.findDeadlinesOnDate(targetDate);

                return ui.getDeadlinesOnDateMessage(targetDate, deadlines);
            } else if (command.equals("mark")) {
                if (!input.startsWith("mark ")
                        || input.substring(5).trim().isEmpty()) {
                    throw new XuanException(
                            "Please specify the task number to mark.");
                }

                int taskNumber = parser.getTaskNumber(input, 5);

                if (taskNumber < 1 || taskNumber > taskList.size()) {
                    throw new XuanException(
                            "That task number does not exist.");
                }

                Task task = taskList.get(taskNumber - 1);
                task.markAsDone();
                storage.saveTasks(taskList.getTasks());

                return ui.getMarkedTaskMessage(task);
            } else if (command.equals("unmark")) {
                if (!input.startsWith("unmark ")
                        || input.substring(7).trim().isEmpty()) {
                    throw new XuanException(
                            "Please specify the task number to unmark.");
                }

                int taskNumber = parser.getTaskNumber(input, 7);

                if (taskNumber < 1 || taskNumber > taskList.size()) {
                    throw new XuanException(
                            "That task number does not exist.");
                }

                Task task = taskList.get(taskNumber - 1);
                task.markAsNotDone();
                storage.saveTasks(taskList.getTasks());

                return ui.getUnmarkedTaskMessage(task);
            } else if (command.equals("delete")) {
                if (!input.startsWith("delete ")
                        || input.substring(7).trim().isEmpty()) {
                    throw new XuanException(
                            "Please specify the task number to delete.");
                }

                int taskNumber = parser.getTaskNumber(input, 7);

                if (taskNumber < 1 || taskNumber > taskList.size()) {
                    throw new XuanException(
                            "That task number does not exist.");
                }

                Task deletedTask = taskList.delete(taskNumber - 1);
                storage.saveTasks(taskList.getTasks());

                return ui.getDeletedTaskMessage(
                        deletedTask, taskList.size());
            } else if (command.equals("todo")) {
                String description = parser.getDescription(input, 4);

                Task task = new Todo(description);
                taskList.add(task);
                storage.saveTasks(taskList.getTasks());

                return ui.getAddedTaskMessage(task, taskList.size());
            } else if (command.equals("deadline")) {
                String description =
                        parser.getDeadlineDescription(input);
                LocalDate by = parser.getDeadlineDate(input);

                Task task = new Deadline(description, by);
                taskList.add(task);
                storage.saveTasks(taskList.getTasks());

                return ui.getAddedTaskMessage(task, taskList.size());
            } else if (command.equals("event")) {
                String description =
                        parser.getEventDescription(input);
                String from = parser.getEventFrom(input);
                String to = parser.getEventTo(input);

                Task task = new Event(description, from, to);
                taskList.add(task);
                storage.saveTasks(taskList.getTasks());

                return ui.getAddedTaskMessage(task, taskList.size());
            } else {
                throw new XuanException(
                        "Sorry, I don't understand that command.");
            }
        } catch (XuanException e) {
            return ui.getErrorMessage(e.getMessage());
        } catch (IOException e) {
            return ui.getErrorMessage(
                    "Sorry, I couldn't save the tasks.");
        }
    }
}
