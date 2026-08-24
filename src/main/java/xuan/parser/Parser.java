package xuan.parser;

import xuan.exception.XuanException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user commands and extracts the information needed to execute them.
 */
public class Parser {
    /**
     * Gets the command word from the user's input.
     *
     * @param input the full command entered by the user
     * @return the first word of the input, which represents the command type
     */
    public String getCommandWord(String input) {
        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            return "";
        }

        int spaceIndex = trimmedInput.indexOf(" ");

        if (spaceIndex == -1) {
            return trimmedInput;
        }

        return trimmedInput.substring(0, spaceIndex);
    }

    /**
     * Gets the task number from the user's input.
     *
     * @param input the full command entered by the user
     * @param commandLength the length of the command word
     * @return the task number specified by the user
     * @throws XuanException if the task number is missing or is not a number
     */
    public int getTaskNumber(String input, int commandLength) throws XuanException {
        String numberString = input.substring(commandLength).trim();

        if (numberString.isEmpty()) {
            throw new XuanException("Please specify the task number.");
        }

        try {
            return Integer.parseInt(numberString);
        } catch (NumberFormatException e) {
            throw new XuanException("The task number must be a number.");
        }
    }


    //TODO
    /**
     * Gets the task description from the user's input.
     *
     * @param input the full command entered by the user
     * @param commandLength the length of the command word
     * @return the task description
     * @throws XuanException if the description is empty
     */
    public String getDescription(String input, int commandLength) throws XuanException {
        String description = input.substring(commandLength).trim();

        if (description.isEmpty()) {
            throw new XuanException("The task description cannot be empty.");
        }

        return description;
    }


    //DEADLINE
    /**
     * Gets the description of a deadline from the user's input.
     *
     * @param input the full deadline command entered by the user
     * @return the deadline description
     * @throws XuanException if /by is missing or the description is empty
     */
    public String getDeadlineDescription(String input) throws XuanException {
        int byIndex = input.indexOf(" /by ");

        if (byIndex == -1) {
            throw new XuanException("Please specify the deadline using /by.");
        }

        String description = input.substring(8, byIndex).trim();

        if (description.isEmpty()) {
            throw new XuanException("The description of a deadline cannot be empty.");
        }

        return description;
    }

    /**
     * Gets the deadline date from the user's input.
     *
     * @param input the full deadline command entered by the user
     * @return the deadline date
     * @throws XuanException if /by is missing, the date is empty,
     *         or the date is not in yyyy-MM-dd format
     */
    public LocalDate getDeadlineDate(String input) throws XuanException {
        int byIndex = input.indexOf(" /by ");

        if (byIndex == -1) {
            throw new XuanException("Please specify the deadline using /by.");
        }

        String byString = input.substring(byIndex + 5).trim();

        if (byString.isEmpty()) {
            throw new XuanException("The deadline time cannot be empty.");
        }

        try {
            return LocalDate.parse(byString);
        } catch (DateTimeParseException e) {
            throw new XuanException(
                    "Please enter the deadline in yyyy-MM-dd format.");
        }
    }

    //EVENT
    /**
     * Gets the description of an event from the user's input.
     *
     * @param input the full event command entered by the user
     * @return the event description
     * @throws XuanException if /from or /to is missing, or the description is empty
     */
    public String getEventDescription(String input) throws XuanException {
        int fromIndex = input.indexOf(" /from ");
        int toIndex = input.indexOf(" /to ");

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            throw new XuanException(
                    "Please specify the event time using /from and /to in the correct order.");
        }

        String description = input.substring(5, fromIndex).trim();

        if (description.isEmpty()) {
            throw new XuanException("The description of an event cannot be empty.");
        }

        return description;
    }

    /**
     * Gets the start time of an event from the user's input.
     *
     * @param input the full event command entered by the user
     * @return the start time of the event
     * @throws XuanException if /from or /to is missing, or the start time is empty
     */
    public String getEventFrom(String input) throws XuanException {
        int fromIndex = input.indexOf(" /from ");
        int toIndex = input.indexOf(" /to ");

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            throw new XuanException(
                    "Please specify the event time using /from and /to in the correct order.");
        }

        String from = input.substring(fromIndex + 7, toIndex).trim();

        if (from.isEmpty()) {
            throw new XuanException("The start time of an event cannot be empty.");
        }

        return from;
    }

    /**
     * Gets the end time of an event from the user's input.
     *
     * @param input the full event command entered by the user
     * @return the end time of the event
     * @throws XuanException if /to is missing or the end time is empty
     */
    public String getEventTo(String input) throws XuanException {
        int toIndex = input.indexOf(" /to ");

        if (toIndex == -1) {
            throw new XuanException(
                    "Please specify the event time using /from and /to in the correct order.");
        }

        String to = input.substring(toIndex + 5).trim();

        if (to.isEmpty()) {
            throw new XuanException("The end time of an event cannot be empty.");
        }

        return to;
    }

    //FIND
    /**
     * Gets the date from a find command.
     *
     * @param input the full find command entered by the user
     * @return the date specified by the user
     * @throws XuanException if the date is missing or has an invalid format
     */
    public LocalDate getFindDate(String input) throws XuanException {
        String dateString = input.substring(4).trim();

        if (dateString.isEmpty()) {
            throw new XuanException("Please specify a date in yyyy-MM-dd format.");
        }

        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new XuanException("Please enter the date in yyyy-MM-dd format.");
        }
    }
}