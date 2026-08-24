package xuan.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    private LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toString() {
        String formattedDate = by.format(
                DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH));
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }
}
