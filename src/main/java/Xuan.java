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
                //get the index to mark
                int taskNumber = Integer.parseInt(input.substring(5));
                tasks[taskNumber - 1].markAsDone();

                //mark as done
                System.out.println("Xuan: Nice! I've marked this task as done:");
                System.out.println("      " + tasks[taskNumber - 1]);
            } else if (input.startsWith("unmark ")) {
                //get the index to mark
                int taskNumber = Integer.parseInt(input.substring(7));
                tasks[taskNumber - 1].markAsNotDone();

                //mark as not done
                System.out.println("Xuan: OK, I've marked this task as not done yet:");
                System.out.println("      " + tasks[taskNumber - 1]);
            } else {
                //store tasks
                tasks[taskCount] = new Task(input);
                taskCount++;

                //repeat users
                System.out.println("Xuan: added: " + input);
            }
        }
        scanner.close();
    }
}
