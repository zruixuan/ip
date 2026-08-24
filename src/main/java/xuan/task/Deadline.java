package xuan.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    //add specific variable by
    private LocalDate by;

    public Deadline(String description, LocalDate by) {
        //initialize the "by" variable
        super(description);
        this.by = by;
    }

    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toString() {
        //direct use superclass's toString
        //Change the stored LocalDate format in to output format
        String formattedDate = by.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH));
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }
}
