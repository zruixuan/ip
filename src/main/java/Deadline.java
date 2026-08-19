public class Deadline extends Task {
    //add specific variable by
    private String by;

    public Deadline(String description, String by) {
        //initialize the "by" variable
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        //direct use superclass's toString
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
