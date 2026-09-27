package academy.fiveletters.mandatory.mr3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.fiveletters.ConsoleApp;
import academy.fiveletters.repository.Dictionary;
import academy.fiveletters.service.GameService;
import academy.fiveletters.support.CliRunner;
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

        assertTrue(result.contains("До свидания!"), "После партий меню должно вернуться и принять выход");
        assertTrue(gamesPlayed >= 2, "Должно быть сыграно 2 партии, а сыграно: " + gamesPlayed);
    }

    @Test
    @DisplayName("Детерминированный режим даёт предсказуемый вывод для автопроверки")
    void deterministicModeProducesPredictableOutput() {
        long seed = 42L;
        String answer = GameService.startGame(Dictionary.WORDS, 6, seed).getAnswer();
        String replay = "арбуз,озеро," + answer;

        CliRunner.Result first = CliRunner.run("--seed", Long.toString(seed), "--replay", replay);
        CliRunner.Result second = CliRunner.run("--seed", Long.toString(seed), "--replay", replay);

        assertEquals(0, first.exitCode(), () -> "Программа упала вместо честного завершения:%n%s".formatted(first));
        assertEquals(
                first.stdout(), second.stdout(), "Одинаковый seed и список попыток обязаны давать одинаковый вывод");
        assertTrue(first.stdout().contains("Победа"), "Последней попыткой должно быть угадано загаданное слово");
    }

    private static ByteArrayInputStream input(String value) {
        return new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
    }
}
