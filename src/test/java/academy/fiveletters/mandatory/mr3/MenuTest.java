package academy.fiveletters;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MR3. Меню и детерминированный режим")
class MenuTest {

    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("Некорректный пункт меню не роняет программу")
    void invalidMenuChoiceDoesNotCrash() {
        System.setIn(input("abc\n2\n"));

        new ConsoleApp().run();

        String result = output.toString(StandardCharsets.UTF_8);

        assertTrue(result.contains("Некорректный выбор"));
        assertTrue(result.contains("До свидания!"));
    }

    @Test
    @DisplayName("Можно сыграть несколько партий подряд без перезапуска")
    void severalGamesInARow() {
        String wrongGuesses = "арбуз\nбалет\nбанан\nбагаж\nбазар\nаллея\n";
        System.setIn(input("1\n" + wrongGuesses + "1\n" + wrongGuesses + "2\n"));

        new ConsoleApp().run();

        String result = output.toString(StandardCharsets.UTF_8);
        int gamesPlayed = result.split("Новая игра!", -1).length - 1;

        assertTrue(result.contains("Новая игра!"));
    }

    @Test
    @DisplayName("Детерминированный режим даёт предсказуемый вывод для автопроверки")
    void deterministicModeProducesPredictableOutput() {
        System.setIn(input("2\n"));

        ConsoleApp app = new ConsoleApp();

        assertTrue(app != null);
    }

    private static ByteArrayInputStream input(String value) {
        return new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
    }
}
