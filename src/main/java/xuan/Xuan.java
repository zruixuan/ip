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

    /**
     * Starts the Xuan chatbot and handles user commands.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();

        ui.showBanner();

        ui.showGreeting();

        Storage storage = new Storage("./data/xuan.txt");

        TaskList taskList;

        try {
            taskList = new TaskList(storage.loadTasks());
        } catch (FileNotFoundException e) {
            taskList = new TaskList(new ArrayList<>());
        }

        while (true) {
            try {
                String input = ui.readCommand();
                String command = parser.getCommandWord(input);

                if (command.equals("bye")) {
                    ui.showBye();
                    break;
                } else if (command.equals("list")) {
                    ui.showTaskList(taskList);
                } else if (command.equals("find")) {
                    LocalDate targetDate = parser.getFindDate(input);

                    ArrayList<Deadline> deadlines = taskList.findDeadlinesOnDate(targetDate);

                    ui.showDeadlinesOnDate(targetDate, deadlines);
                } else if (command.equals("mark")) {
                    if (!input.startsWith("mark ") || input.substring(5).trim().isEmpty()) {
                        throw new XuanException("Please specify the task number to mark.");
                    }

                    int taskNumber = parser.getTaskNumber(input, 5);

                    if (taskNumber < 1 || taskNumber > taskList.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    taskList.get(taskNumber - 1).markAsDone();
                    storage.saveTasks(taskList.getTasks());

                    ui.showMarkedTask(taskList.get(taskNumber - 1));
                } else if (command.equals("unmark")) {
                    if (!input.startsWith("unmark ") || input.substring(7).trim().isEmpty()) {
                        throw new XuanException("Please specify the task number to unmark.");
                    }

                    int taskNumber = parser.getTaskNumber(input, 7);

                    if (taskNumber < 1 || taskNumber > taskList.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    taskList.get(taskNumber - 1).markAsNotDone();
                    storage.saveTasks(taskList.getTasks());

                    ui.showUnmarkedTask(taskList.get(taskNumber - 1));
                } else if (command.equals("delete")) {
                    if (!input.startsWith("delete ") || input.substring(7).trim().isEmpty()) {
                        throw new XuanException("Please specify the task number to delete.");
                    }

                    int taskNumber = parser.getTaskNumber(input, 7);

                    if (taskNumber < 1 || taskNumber > taskList.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    Task deletedTask = taskList.delete(taskNumber - 1);
                    storage.saveTasks(taskList.getTasks());

                    ui.showDeletedTask(deletedTask, taskList.size());
                } else if (command.equals("todo")) {
                    String description = parser.getDescription(input, 4);

                    taskList.add(new Todo(description));
                    ui.showAddedTask(taskList.get(taskList.size() - 1), taskList.size());

                    storage.saveTasks(taskList.getTasks());
                } else if (command.equals("deadline")) {
                    String description = parser.getDeadlineDescription(input);
                    LocalDate by = parser.getDeadlineDate(input);

                    taskList.add(new Deadline(description, by));
                    ui.showAddedTask(taskList.get(taskList.size() - 1), taskList.size());

                    storage.saveTasks(taskList.getTasks());
                } else if (command.equals("event")) {
                    String description = parser.getEventDescription(input);
                    String from = parser.getEventFrom(input);
                    String to = parser.getEventTo(input);

                    taskList.add(new Event(description, from, to));
                    ui.showAddedTask(taskList.get(taskList.size() - 1), taskList.size());

                    storage.saveTasks(taskList.getTasks());
                } else {
                    throw new XuanException("Sorry, I don't understand that command.");
                }
            } catch (XuanException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Sorry, I couldn't save the tasks.");
            }
        }
        ui.close();
    }
}
