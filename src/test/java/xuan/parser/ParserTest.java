package xuan.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void getCommandWord_commandWithArgument_returnsCommandWord() {
        Parser parser = new Parser();

        String result = parser.getCommandWord("todo read book");

        assertEquals("todo", result);
    }

    @Test
    public void getCommandWord_commandWithoutArgument_returnsCommandWord() {
        Parser parser = new Parser();

        String result = parser.getCommandWord("list");

        assertEquals("list", result);
    }

    @Test
    public void getCommandWord_inputWithExtraSpaces_returnsCommandWord() {
        Parser parser = new Parser();

        String result = parser.getCommandWord("   todo read book   ");

        assertEquals("todo", result);
    }

    @Test
    public void getCommandWord_emptyInput_returnsEmptyString() {
        Parser parser = new Parser();

        String result = parser.getCommandWord("   ");

        assertEquals("", result);
    }
}
