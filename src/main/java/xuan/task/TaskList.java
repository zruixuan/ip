package xuan.task;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds all deadlines that occur on the specified date.
     *
     * @param targetDate the date to search for deadlines
     * @return the list of deadlines that occur on the specified date
     */
    public ArrayList<Deadline> findDeadlinesOnDate(LocalDate targetDate) {
        ArrayList<Deadline> matchingDeadlines = new ArrayList<>();
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;

                if (deadline.getBy().equals(targetDate)) {
                    matchingDeadlines.add(deadline);
                }
            }
        }
        return matchingDeadlines;
    }
}
