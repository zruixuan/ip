import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    //Show my banner
    public void showBanner() {
        String banner = "__  __  _   _    _    _   _\n"
                + "\\ \\/ / | | | |  / \\  | \\ | |\n"
                + " \\  /  | | | | / _ \\ |  \\| |\n"
                + " /  \\  | |_| |/ ___ \\| |\\  |\n"
                + "/_/\\_\\  \\___//_/   \\_\\_| \\_|\n";

        System.out.println(banner);
    }

    public String readCommand() {
        System.out.print("You: ");
        return scanner.nextLine();
    }

    public void showGreeting() {
        System.out.println("Xuan: Hello! I'm Xuan.");
        System.out.println("Xuan: What can I do for you? \n");
    }

    public void showBye() {
        System.out.println("Xuan: Bye. Hope to see you again soon!");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }

    //Show all the tasks in the file
    public void showTaskList(TaskList taskList) {
        System.out.println("Xuan: Here are your tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("      " + (i + 1) + ". " + taskList.get(i));
        }
    }
    //Show the errors in the file
    public void showError(String message) {
        System.out.println("Xuan: " + message);
    }

    public void showAddedTask(Task task, int taskCount) {
        System.out.println("Xuan: Got it. I've added this task:");
        System.out.println("      " + task);
        System.out.println("      Now you have " + taskCount + " tasks in the list.");
    }

    public void showDeletedTask(Task task, int taskCount) {
        System.out.println("Xuan: Noted. I've removed this task:");
        System.out.println("      " + task);
        System.out.println("      Now you have " + taskCount + " tasks in the list.");
    }

    public void showMarkedTask(Task task) {
        System.out.println("Xuan: Nice! I've marked this task as done:");
        System.out.println("      " + task);
    }

    public void showUnmarkedTask(Task task) {
        System.out.println("Xuan: OK, I've marked this task as not done yet:");
        System.out.println("      " + task);
    }

}
