package academy.fiveletters.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import academy.fiveletters.data.GameSession;
import academy.fiveletters.data.GuessResult;
import academy.fiveletters.repository.Dictionary;
import academy.fiveletters.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Обязательные тесты: валидация ввода. */
@DisplayName("MR2. Валидация ввода")
class GuessValidationTest {

    @ParameterizedTest
    @ValueSource(strings = {"дом", "домики", ""})
    @DisplayName("Слово не из 5 букв отклоняется: \"{0}\"")
    void wordOfWrongLengthIsRejected(String guess) {
        GameSession session = new GameSession("сорок", 6, Dictionary.WORDS);

        assertThrows(IllegalArgumentException.class, () -> GameService.applyGuess(session, guess));

        assertEquals(0, session.getAttemptsUsed());
    }

    @ParameterizedTest
    @ValueSource(strings = {"дом12", "дом!!", "до ма"})
    @DisplayName("Ввод с не-буквами отклоняется: \"{0}\"")
    void nonLetterInputIsRejected(String guess) {
        GameSession session = new GameSession("сорок", 6, Dictionary.WORDS);

        assertThrows(IllegalArgumentException.class, () -> GameService.applyGuess(session, guess));

        assertEquals(0, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Слово, которого нет в словаре, отклоняется")
    void wordOutsideDictionaryIsRejected() {
        GameSession session = new GameSession("ыыыыы", 6, Dictionary.WORDS);

        assertThrows(IllegalArgumentException.class, () -> GameService.applyGuess(session, "ыыыыы"));

        assertEquals(0, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Некорректный ввод не тратит попытку")
    void invalidInputDoesNotConsumeAttempt() {
        GameSession session = new GameSession("сорок", 6, Dictionary.WORDS);

        assertThrows(IllegalArgumentException.class, () -> GameService.applyGuess(session, "сор"));

        assertEquals(0, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Ввод не зависит от регистра: \"ОЗЕРО\" и \"озеро\" обрабатываются одинаково")
    void inputIsCaseInsensitive() {

        GameSession session = new GameSession("сорок", 6, Dictionary.WORDS);
        GuessResult result = GameService.applyGuess(session, "ОЗЕРО");

        assertEquals("озеро", result.getGuess());
        assertEquals("озеро", session.getAttemptsHistory().get(0));
        assertEquals(1, session.getAttemptsUsed());
    }
}
