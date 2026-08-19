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
            } else if (input.startsWith("mark ")) {
                //get the task number to mark
                int taskNumber = Integer.parseInt(input.substring(5));
                tasks[taskNumber - 1].markAsDone();

                //mark as done
                System.out.println("Xuan: Nice! I've marked this task as done:");
                System.out.println("      " + tasks[taskNumber - 1]);
            } else if (input.startsWith("unmark ")) {
                //get the task number to mark
                int taskNumber = Integer.parseInt(input.substring(7));
                tasks[taskNumber - 1].markAsNotDone();

                //mark as not done
                System.out.println("Xuan: OK, I've marked this task as not done yet:");
                System.out.println("      " + tasks[taskNumber - 1]);
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);

                //create new "Todo" tasks
                tasks[taskCount] = new Todo(description);
                System.out.println("Xuan: Got it. I've added this task:");
                System.out.println("      " + tasks[taskCount]);
                taskCount++;

                //return the number of tasks
                System.out.println("      Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("deadline ")) {
                int byIndex = input.indexOf(" /by ");

                String description = input.substring(9, byIndex);
                String by = input.substring(byIndex + 5);

                //create new "Deadline" tasks
                tasks[taskCount] = new Deadline(description, by);
                System.out.println("Xuan: Got it. I've added this task:");
                System.out.println("      " + tasks[taskCount]);
                taskCount++;

                //return the number of tasks
                System.out.println("      Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("event ")) {
                int fromIndex = input.indexOf(" /from ");
                int toIndex = input.indexOf(" /to ");

                String description = input.substring(6, fromIndex);
                String from = input.substring(fromIndex + 7, toIndex);
                String to = input.substring(toIndex + 5);

                //create new "Event" tasks
                tasks[taskCount] = new Event(description, from, to);
                System.out.println("Xuan: Got it. I've added this task:");
                System.out.println("      " + tasks[taskCount]);
                taskCount++;

                //return the number of tasks
                System.out.println("      Now you have " + taskCount + " tasks in the list.");
            }
        }
        scanner.close();
    }
}
