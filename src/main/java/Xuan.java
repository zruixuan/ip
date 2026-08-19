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
        String[] tasks = new String[100];
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
            } else {
                //store tasks
                tasks[taskCount] = input;
                taskCount++;

                //repeat users
                System.out.println("Xuan: added: " + input);
            }
        }
        scanner.close();
    }
}
