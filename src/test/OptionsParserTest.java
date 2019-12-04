package test;

import main.*;
import org.junit.Test;

import static main.MoveDirection.*;
import static org.junit.Assert.*;

public class OptionsParserTest {

    @Test
    public void parse() {
        String[] strings;
        MoveDirection[] expected;
        MoveDirection[] parsed;

        strings = new String[]{"r", "f", "backward", "left", "forward"};
        expected = new MoveDirection[]{RIGHT, FORWARD, BACKWARD, LEFT, FORWARD};
        parsed = OptionsParser.parse(strings);
        assertArrayEquals(parsed, expected);


        strings = new String[]{};
        expected = new MoveDirection[]{};
        parsed = OptionsParser.parse(strings);
        assertArrayEquals(parsed, expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse2() {
        String[] strings = new String[]{"ą"};
        OptionsParser.parse(strings);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse3() {
        String[] strings = new String[]{"r", "f", "backward", "x", "left", "ddd", "forward"};
        OptionsParser.parse(strings);
    }

}