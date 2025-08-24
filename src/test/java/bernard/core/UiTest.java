package bernard.core;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UiTest {
    @Test
    void getUserInput_returnsExpectedInput() {
        String simulatedInput = "Hello World\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        String input = ui.getUserInput();

        assertEquals("Hello World", input);
    }

    @Test
    void outputLine_printsExpectedOutput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Ui ui = new Ui();
        ui.outputLine("Hello World");

        assertEquals("Hello World" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void title_printsLogo() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Ui ui = new Ui();
        ui.title();

        // check for part of the logo
        String output = outContent.toString();
        assert(output.contains("____"));
        assert(output.contains("| __ )  ___"));
    }

    @Test
    void greet_printsGreeting() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Ui ui = new Ui();
        ui.greet();

        String output = outContent.toString();
        assert(output.contains("Hello! I'm Bernard"));
        assert(output.contains("How can I help you today?"));
    }

    @Test
    void bye_printsBye() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Ui ui = new Ui();
        ui.bye();

        String output = outContent.toString();
        assert(output.contains("Goodbye! See you again!"));
    }
}
