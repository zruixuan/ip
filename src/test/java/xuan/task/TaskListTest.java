package xuan.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void findDeadlinesOnDate_matchingDeadline_returnsDeadline() {
        ArrayList<Task> tasks = new ArrayList<>();
        Deadline deadline = new Deadline(
                "submit assignment",
                LocalDate.parse("2026-08-30")
        );
        tasks.add(deadline);

        TaskList taskList = new TaskList(tasks);

        ArrayList<Deadline> result =
                taskList.findDeadlinesOnDate(LocalDate.parse("2026-08-30"));

        assertEquals(1, result.size());
        assertEquals(deadline, result.get(0));
    }

    @Test
    public void findDeadlinesOnDate_noMatchingDeadline_returnsEmptyList() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Deadline(
                "submit assignment",
                LocalDate.parse("2026-08-30")
        ));

        TaskList taskList = new TaskList(tasks);

        ArrayList<Deadline> result =
                taskList.findDeadlinesOnDate(LocalDate.parse("2026-09-01"));

        assertTrue(result.isEmpty());
    }

    @Test
    public void findDeadlinesOnDate_multipleTasks_returnsOnlyMatchingDeadlines() {
        ArrayList<Task> tasks = new ArrayList<>();

        Deadline firstDeadline = new Deadline(
                "submit assignment",
                LocalDate.parse("2026-08-30")
        );

        Deadline secondDeadline = new Deadline(
                "return book",
                LocalDate.parse("2026-08-30")
        );

        Deadline otherDeadline = new Deadline(
                "project meeting",
                LocalDate.parse("2026-09-01")
        );

        tasks.add(firstDeadline);
        tasks.add(new Todo("read book"));
        tasks.add(secondDeadline);
        tasks.add(otherDeadline);
        tasks.add(new Event("meeting", "2pm", "4pm"));

        TaskList taskList = new TaskList(tasks);

        ArrayList<Deadline> result =
                taskList.findDeadlinesOnDate(LocalDate.parse("2026-08-30"));

        assertEquals(2, result.size());
        assertEquals(firstDeadline, result.get(0));
        assertEquals(secondDeadline, result.get(1));
    }
}
