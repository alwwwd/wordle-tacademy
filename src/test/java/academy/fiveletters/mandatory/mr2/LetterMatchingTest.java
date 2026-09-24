package academy.fiveletters.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import academy.fiveletters.data.GameSession;
import academy.fiveletters.data.GuessResult;
import academy.fiveletters.enums.GameStatus;
import academy.fiveletters.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: раскраска букв. */
@DisplayName("MR2. Проверка букв")
class LetterMatchingTest {

    @Test
    @DisplayName("Базовый случай: загадано \"озеро\", ввод \"арбуз\" -> ❌🟡❌❌🟡")
    void basicCase() {
        GameSession session = new GameSession("озеро", 6);

        GuessResult result = GameService.applyGuess(session, "арбуз");

        assertEquals("арбуз", result.getGuess());
        assertEquals("❌🟡❌❌🟡", result.getResult());
        assertEquals(GameStatus.IN_PROGRESS, result.getStatus());
        assertEquals(1, session.getAttemptsUsed());
    }

    @Test
    @Disabled("MR2: реализуй тест и удали эту строку")
    @DisplayName("Полное совпадение: загадано \"озеро\", ввод \"озеро\" -> ✅✅✅✅✅")
    void exactMatch() {
        GameSession session = new GameSession("озеро", 6);

        GuessResult result = GameService.applyGuess(session, "озеро");

        assertEquals("озеро", result.getGuess());
        assertEquals("✅✅✅✅✅", result.getResult());
        assertEquals(GameStatus.WIN, result.getStatus());
        assertEquals(1, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Повторяющиеся буквы: загадано \"сорок\", ввод \"оооом\" -> ❌✅❌✅❌")
    void repeatedLettersAreNotDoubleCounted() {
        GameSession session = new GameSession("сорок", 6);

        GuessResult result = GameService.applyGuess(session, "оооом");

        assertEquals("оооом", result.getGuess());
        assertEquals("❌✅❌✅❌", result.getResult());
        assertEquals(GameStatus.IN_PROGRESS, result.getStatus());
        assertEquals(1, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Ни одна буква не подошла: все позиции ❌")
    void noMatchingLetters() {
        GameSession session = new GameSession("сорок", 6);

        GuessResult result = GameService.applyGuess(session, "ттттт");

        assertEquals("ттттт", result.getGuess());
        assertEquals("❌❌❌❌❌", result.getResult());
        assertEquals(GameStatus.IN_PROGRESS, result.getStatus());
        assertEquals(1, session.getAttemptsUsed());
    }
}
