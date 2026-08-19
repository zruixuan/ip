public class Event extends Task {
    //add 2 specific variables from, to
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        //initialize the "from" & "to" variable
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from + " to: " + to + ")";
    }
}