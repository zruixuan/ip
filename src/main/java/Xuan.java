import java.util.Scanner;

public class Xuan {
    public static void main(String[] args) {
        //my banner
        String banner = "__  __  _   _    _    _   _\n"
                + "\\ \\/ / | | | |  / \\  | \\ | |\n"
                + " \\  /  | | | | / _ \\ |  \\| |\n"
                + " /  \\  | |_| |/ ___ \\| |\\  |\n"
                + "/_/\\_\\  \\___//_/   \\_\\_| \\_|\n";

        System.out.println(banner);

        //initialize the fixed size array
        Task[] tasks = new Task[100];
        int taskCount = 0;

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
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("      " + (i + 1) + ". " + tasks[i]);
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
                    if (taskNumber < 1 || taskNumber > taskCount) {
                        throw new XuanException("That task number does not exist.");
                    }

                    tasks[taskNumber - 1].markAsDone();

                    System.out.println("Xuan: Nice! I've marked this task as done:");
                    System.out.println("      " + tasks[taskNumber - 1]);
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
                    if (taskNumber < 1 || taskNumber > taskCount) {
                        throw new XuanException("That task number does not exist.");
                    }

                    tasks[taskNumber - 1].markAsNotDone();

                    System.out.println("Xuan: OK, I've marked this task as not done yet:");
                    System.out.println("      " + tasks[taskNumber - 1]);
                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.substring(4).trim();

                    //throw exception
                    if (description.isEmpty()) {
                        throw new XuanException("The description of a todo cannot be empty.");
                    }

                    //create new "Todo" tasks
                    tasks[taskCount] = new Todo(description);
                    System.out.println("Xuan: Got it. I've added this task:");
                    System.out.println("      " + tasks[taskCount]);
                    taskCount++;

                    //return the number of tasks
                    System.out.println("      Now you have " + taskCount + " tasks in the list.");
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
                    tasks[taskCount] = new Deadline(description, by);
                    System.out.println("Xuan: Got it. I've added this task:");
                    System.out.println("      " + tasks[taskCount]);
                    taskCount++;

                    //return the number of tasks
                    System.out.println("      Now you have " + taskCount + " tasks in the list.");
                } else if (input.equals("event") || input.startsWith("event ")) {
                    int fromIndex = input.indexOf(" /from ");
                    int toIndex = input.indexOf(" /to ");

                    //throw exceptions
                    if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
                        throw new XuanException(
                                "Please specify the event time using /from and /to in the correct order."
                        );
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
                    tasks[taskCount] = new Event(description, from, to);
                    System.out.println("Xuan: Got it. I've added this task:");
                    System.out.println("      " + tasks[taskCount]);
                    taskCount++;

                    //return the number of tasks
                    System.out.println("      Now you have " + taskCount + " tasks in the list.");
                } else {
                    throw new XuanException("Sorry, I don't understand that command.");
                }
            } catch (XuanException e) {
                System.out.println("Xuan: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
