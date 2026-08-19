public class Todo extends Task {
    public Todo(String description) {
        //no other information to store
        super(description);
    }

    @Override
    public String toString() {
        //direct use superclass's toString
        return "[T]" + super.toString();
    }
}