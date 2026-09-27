package academy.fiveletters.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import academy.fiveletters.data.GameSession;
import academy.fiveletters.data.GuessResult;
import academy.fiveletters.enums.GameStatus;
import academy.fiveletters.repository.Dictionary;
import academy.fiveletters.service.GameService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: раскраска букв. */
@DisplayName("MR2. Проверка букв")
class LetterMatchingTest {

    @Test
    @DisplayName("Базовый случай: загадано \"озеро\", ввод \"арбуз\" -> ❌🟡❌❌🟡")
    void basicCase() {
        GameSession session = new GameSession("озеро", 6, Dictionary.WORDS);

        GuessResult result = GameService.applyGuess(session, "арбуз");

        assertEquals("арбуз", result.getGuess());
        assertEquals("❌🟡❌❌🟡", result.getResult());
        assertEquals(GameStatus.IN_PROGRESS, result.getStatus());
        assertEquals(1, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Полное совпадение: загадано \"озеро\", ввод \"озеро\" -> ✅✅✅✅✅")
    void exactMatch() {
        GameSession session = new GameSession("озеро", 6, Dictionary.WORDS);

        GuessResult result = GameService.applyGuess(session, "озеро");

        assertEquals("озеро", result.getGuess());
        assertEquals("✅✅✅✅✅", result.getResult());
        assertEquals(GameStatus.WIN, result.getStatus());
        assertEquals(1, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Повторяющиеся буквы: загадано \"сорок\", ввод \"оооом\" -> ❌✅❌✅❌")
    void repeatedLettersAreNotDoubleCounted() {
        List<String> testDictionary = List.of("сорок", "оооом");
        GameSession session = new GameSession("сорок", 6, testDictionary);

        GuessResult result = GameService.applyGuess(session, "оооом");

        assertEquals("оооом", result.getGuess());
        assertEquals("❌✅❌✅❌", result.getResult());
        assertEquals(GameStatus.IN_PROGRESS, result.getStatus());
        assertEquals(1, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Ни одна буква не подошла: все позиции ❌")
    void noMatchingLetters() {
        GameSession session = new GameSession("сорок", 6, Dictionary.WORDS);

        GuessResult result = GameService.applyGuess(session, "билет");

        assertEquals("билет", result.getGuess());
        assertEquals("❌❌❌❌❌", result.getResult());
        assertEquals(GameStatus.IN_PROGRESS, result.getStatus());
        assertEquals(1, session.getAttemptsUsed());
    }
}
