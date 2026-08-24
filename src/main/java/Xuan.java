import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class Xuan {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();

        //my banner
        ui.showBanner();

        //greeting message
        ui.showGreeting();

        //Initialize the Storage helper
        Storage storage = new Storage("./data/xuan.txt");

        //initialize the ArrayList
        TaskList taskList;

        //Read the task data in xuan.txt
        try {
            taskList = new TaskList(storage.loadTasks());
        } catch (FileNotFoundException e) {
            taskList = new TaskList(new ArrayList<>());
        }

        while (true) {
            try {
                String input = ui.readCommand();
                String command = parser.getCommandWord(input);

                //exiting message
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
                    //check whether a task number is given
                    if (!input.startsWith("mark ") || input.substring(5).trim().isEmpty()) {
                        throw new XuanException("Please specify the task number to mark.");
                    }

                    int taskNumber = parser.getTaskNumber(input, 5);

                    //check whether the task number exists
                    if (taskNumber < 1 || taskNumber > taskList.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    taskList.get(taskNumber - 1).markAsDone();
                    storage.saveTasks(taskList.getTasks());

                    ui.showMarkedTask(taskList.get(taskNumber - 1));
                } else if (command.equals("unmark")) {
                    //check whether a task number is given
                    if (!input.startsWith("unmark ") || input.substring(7).trim().isEmpty()) {
                        throw new XuanException("Please specify the task number to unmark.");
                    }

                    int taskNumber = parser.getTaskNumber(input, 7);

                    //check whether the task number exists
                    if (taskNumber < 1 || taskNumber > taskList.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    taskList.get(taskNumber - 1).markAsNotDone();
                    storage.saveTasks(taskList.getTasks());

                    ui.showUnmarkedTask(taskList.get(taskNumber - 1));
                } else if (command.equals("delete")) {
                    //check whether a task number is given
                    if (!input.startsWith("delete ") || input.substring(7).trim().isEmpty()) {
                        throw new XuanException("Please specify the task number to delete.");
                    }

                    int taskNumber = parser.getTaskNumber(input, 7);

                    //check whether the task number exists
                    if (taskNumber < 1 || taskNumber > taskList.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    //delete the task
                    Task deletedTask = taskList.delete(taskNumber - 1);
                    storage.saveTasks(taskList.getTasks());

                    ui.showDeletedTask(deletedTask, taskList.size());
                } else if (command.equals("todo")) {
                    //Get the description
                    String description = parser.getDescription(input, 4);

                    //create new "Todo" tasks
                    taskList.add(new Todo(description));
                    ui.showAddedTask(taskList.get(taskList.size() - 1), taskList.size());

                    //Store the tasks data
                    storage.saveTasks(taskList.getTasks());
                } else if (command.equals("deadline")) {
                    //Get the description and by of deadline items
                    String description = parser.getDeadlineDescription(input);
                    LocalDate by = parser.getDeadlineDate(input);

                    //create new "Deadline" tasks
                    taskList.add(new Deadline(description, by));
                    ui.showAddedTask(taskList.get(taskList.size() - 1), taskList.size());

                    //Store the tasks data
                    storage.saveTasks(taskList.getTasks());
                } else if (command.equals("event")) {
                    //Get the description and from and to of event items
                    String description = parser.getEventDescription(input);
                    String from = parser.getEventFrom(input);
                    String to = parser.getEventTo(input);

                    //create new "Event" tasks
                    taskList.add(new Event(description, from, to));
                    ui.showAddedTask(taskList.get(taskList.size() - 1), taskList.size());

                    //Store the tasks data
                    storage.saveTasks(taskList.getTasks());
                } else {
                    throw new XuanException("Sorry, I don't understand that command.");
                }
            } catch (XuanException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                //handle the exception about File I/O
                ui.showError("Sorry, I couldn't save the tasks.");
            }
        }
        ui.close();
    }
}
