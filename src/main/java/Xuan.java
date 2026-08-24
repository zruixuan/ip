import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Xuan {
    /** Save the data to xuan.txt in order to reload data
      * Each time use saveTasks function overwrite the former content
      */
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        File directory = new File("./data");
        directory.mkdirs();

        FileWriter writer = new FileWriter("./data/xuan.txt");

        for (Task task : tasks) {
            if (task instanceof Todo) {
                writer.write("T | " + (task.isDone() ? "1" : "0")
                        + " | " + task.getDescription() + "\n");

            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;

                writer.write("D | " + (task.isDone() ? "1" : "0")
                        + " | " + task.getDescription()
                        + " | " + deadline.getBy() + "\n");

            } else if (task instanceof Event) {
                Event event = (Event) task;

                writer.write("E | " + (task.isDone() ? "1" : "0")
                        + " | " + task.getDescription()
                        + " | " + event.getFrom()
                        + " | " + event.getTo() + "\n");
            }
        }

        writer.close();
    }

    public static void main(String[] args) {
        //my banner
        String banner = "__  __  _   _    _    _   _\n"
                + "\\ \\/ / | | | |  / \\  | \\ | |\n"
                + " \\  /  | | | | / _ \\ |  \\| |\n"
                + " /  \\  | |_| |/ ___ \\| |\\  |\n"
                + "/_/\\_\\  \\___//_/   \\_\\_| \\_|\n";

        System.out.println(banner);

        //initialize the ArrayList
        ArrayList<Task> tasks = new ArrayList<>();

        //greeting message
        System.out.println("Xuan: Hello! I'm Xuan.");
        System.out.println("Xuan: What can I do for you? \n");

        //get the content users type in
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("You: ");
                String input = scanner.nextLine();

                //exiting message
                if (input.equals("bye")) {
                    System.out.println("Xuan: Bye. Hope to see you again soon!");
                    break;
                } else if (input.equals("list")) {
                    System.out.println("Xuan: Here are your tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("      " + (i + 1) + ". " + tasks.get(i));
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
                    if (taskNumber < 1 || taskNumber > tasks.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    tasks.get(taskNumber - 1).markAsDone();
                    Xuan.saveTasks(tasks);

                    System.out.println("Xuan: Nice! I've marked this task as done:");
                    System.out.println("      " + tasks.get(taskNumber - 1));
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
                    if (taskNumber < 1 || taskNumber > tasks.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    tasks.get(taskNumber - 1).markAsNotDone();
                    Xuan.saveTasks(tasks);

                    System.out.println("Xuan: OK, I've marked this task as not done yet:");
                    System.out.println("      " + tasks.get(taskNumber - 1));
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
                    if (taskNumber < 1 || taskNumber > tasks.size()) {
                        throw new XuanException("That task number does not exist.");
                    }

                    //delete the task
                    Task deletedTask = tasks.remove(taskNumber - 1);
                    Xuan.saveTasks(tasks);

                    System.out.println("Xuan: Noted. I've removed this task:");
                    System.out.println("      " + deletedTask);
                    System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.substring(4).trim();

                    //throw exception
                    if (description.isEmpty()) {
                        throw new XuanException("The description of a todo cannot be empty.");
                    }

                    //create new "Todo" tasks
                    tasks.add(new Todo(description));
                    System.out.println("Xuan: Got it. I've added this task:");
                    System.out.println("      " + tasks.get(tasks.size() - 1));

                    //return the number of tasks
                    Xuan.saveTasks(tasks);
                    System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
                } else if (input.equals("deadline") || input.startsWith("deadline ")) {
                    int byIndex = input.indexOf(" /by ");

                    //throw exceptions
                    if (byIndex == -1) {
                        throw new XuanException("Please specify the deadline using /by.");
                    }

                    String description = input.substring(8, byIndex).trim();
                    String by = input.substring(byIndex + 5).trim();

                    if (description.isEmpty()) {
                        throw new XuanException("The description of a deadline cannot be empty.");
                    }
                    if (by.isEmpty()) {
                        throw new XuanException("The deadline time cannot be empty.");
                    }

                    //create new "Deadline" tasks
                    tasks.add(new Deadline(description, by));
                    System.out.println("Xuan: Got it. I've added this task:");
                    System.out.println("      " + tasks.get(tasks.size() - 1));

                    //return the number of tasks
                    Xuan.saveTasks(tasks);
                    System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
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
                    tasks.add(new Event(description, from, to));
                    System.out.println("Xuan: Got it. I've added this task:");
                    System.out.println("      " + tasks.get(tasks.size() - 1));

                    //return the number of tasks
                    Xuan.saveTasks(tasks);
                    System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    throw new XuanException("Sorry, I don't understand that command.");
                }
            } catch (XuanException e) {
                System.out.println("Xuan: " + e.getMessage());
            } catch (IOException e) {
                //handle the exception about File I/O
                System.out.println("Xuan: Sorry, I couldn't save the tasks.");
            }
        }
        scanner.close();
    }
}
