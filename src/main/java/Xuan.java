import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class Xuan {
    public static void main(String[] args) {
        Ui ui = new Ui();

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

                //exiting message
                if (input.equals("bye")) {
                    ui.showBye();
                    break;
                } else if (input.equals("list")) {
                    ui.showTaskList(taskList);
                } else if (input.equals("find") || input.startsWith("find ")) {
                    String dateString = input.substring(4).trim();

                    if (dateString.isEmpty()) {
                        throw new XuanException("Please specify a date in yyyy-MM-dd format.");
                    }

                    LocalDate targetDate;

                    try {
                        targetDate = LocalDate.parse(dateString);
                    } catch (DateTimeParseException e) {
                        throw new XuanException("Please enter the date in yyyy-MM-dd format.");
                    }

                    System.out.println("Xuan: Here are the deadlines on " + dateString + ":");

                    int count = 0;

                    for (Task task : taskList.getTasks()) {
                        if (task instanceof Deadline) {
                            Deadline deadline = (Deadline) task;

                            if (deadline.getBy().equals(targetDate)) {
                                count++;
                                System.out.println("      " + count + ". " + deadline);
                            }
                        }
                    }

                    if (count == 0) {
                        System.out.println("      No deadlines found.");
                    }
                } else if (input.startsWith("mark")) {
                    //check whether a task number is given
                    if (!input.startsWith("mark ") || input.substring(5).trim().isEmpty()) {
                        throw new XuanException("Please specify the task number to mark.");
                    }

                    int taskNumber;
                    try {
                        taskNumber = Integer.parseInt(input.substring(5).trim());
                    } catch (NumberFormatException e) {
                        throw new XuanException("The task number must be a number.");
                    }

                    //check whether the task number exists
                    if (taskNumber < 1 || taskNumber > taskList.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    taskList.get(taskNumber - 1).markAsDone();
                    storage.saveTasks(taskList.getTasks());

                    ui.showMarkedTask(taskList.get(taskNumber - 1));
                } else if (input.startsWith("unmark")) {
                    //check whether a task number is given
                    if (!input.startsWith("unmark ") || input.substring(7).trim().isEmpty()) {
                        throw new XuanException("Please specify the task number to unmark.");
                    }

                    int taskNumber;
                    try {
                        taskNumber = Integer.parseInt(input.substring(7).trim());
                    } catch (NumberFormatException e) {
                        throw new XuanException("The task number must be a number.");
                    }

                    //check whether the task number exists
                    if (taskNumber < 1 || taskNumber > taskList.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    taskList.get(taskNumber - 1).markAsNotDone();
                    storage.saveTasks(taskList.getTasks());

                    ui.showUnmarkedTask(taskList.get(taskNumber - 1));
                } else if (input.startsWith("delete")) {
                    //check whether a task number is given
                    if (!input.startsWith("delete ") || input.substring(7).trim().isEmpty()) {
                        throw new XuanException("Please specify the task number to delete.");
                    }

                    int taskNumber;
                    try {
                        taskNumber = Integer.parseInt(input.substring(7).trim());
                    } catch (NumberFormatException e) {
                        throw new XuanException("The task number must be a number.");
                    }

                    //check whether the task number exists
                    if (taskNumber < 1 || taskNumber > taskList.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    //delete the task
                    Task deletedTask = taskList.delete(taskNumber - 1);
                    storage.saveTasks(taskList.getTasks());

                    ui.showDeletedTask(deletedTask, taskList.size());
                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.substring(4).trim();

                    //throw exception
                    if (description.isEmpty()) {
                        throw new XuanException("The description of a todo cannot be empty.");
                    }

                    //create new "Todo" tasks
                    taskList.add(new Todo(description));
                    ui.showAddedTask(taskList.get(taskList.size() - 1), taskList.size());

                    //Store the tasks data
                    storage.saveTasks(taskList.getTasks());
                } else if (input.equals("deadline") || input.startsWith("deadline ")) {
                    int byIndex = input.indexOf(" /by ");

                    //throw exceptions
                    if (byIndex == -1) {
                        throw new XuanException("Please specify the deadline using /by.");
                    }

                    String description = input.substring(8, byIndex).trim();
                    String byString = input.substring(byIndex + 5).trim();

                    if (description.isEmpty()) {
                        throw new XuanException("The description of a deadline cannot be empty.");
                    }
                    if (byString.isEmpty()) {
                        throw new XuanException("The deadline time cannot be empty.");
                    }

                    LocalDate by;

                    //Change the string format to LocalDate format
                    try {
                        by = LocalDate.parse(byString);
                    } catch (DateTimeParseException e) {
                        throw new XuanException("Please enter the deadline in yyyy-MM-dd format.");
                    }

                    //create new "Deadline" tasks
                    taskList.add(new Deadline(description, by));
                    ui.showAddedTask(taskList.get(taskList.size() - 1), taskList.size());

                    //Store the tasks data
                    storage.saveTasks(taskList.getTasks());
                } else if (input.equals("event") || input.startsWith("event ")) {
                    int fromIndex = input.indexOf(" /from ");
                    int toIndex = input.indexOf(" /to ");

                    //throw exceptions
                    if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
                        throw new XuanException("Please specify the event time using /from and /to in the correct order.");
                    }

                    String description = input.substring(5, fromIndex).trim();
                    String from = input.substring(fromIndex + 7, toIndex).trim();
                    String to = input.substring(toIndex + 5).trim();

                    if (description.isEmpty()) {
                        throw new XuanException("The description of an event cannot be empty.");
                    }

                    if (from.isEmpty()) {
                        throw new XuanException("The start time of an event cannot be empty.");
                    }

                    if (to.isEmpty()) {
                        throw new XuanException("The end time of an event cannot be empty.");
                    }

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
