package academy.fiveletters.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import academy.fiveletters.data.GameSession;
import academy.fiveletters.data.GuessResult;
import academy.fiveletters.enums.GameStatus;
import academy.fiveletters.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: завершение партии. */
@DisplayName("MR2. Победа и поражение")
class GameOutcomeTest {

    @Test
    @DisplayName("Угаданное слово переводит сессию в статус WIN")
    void correctGuessWinsTheGame() {
        GameSession session = new GameSession("сорок", 3);

        GuessResult result = GameService.applyGuess(session, "сорок");

        assertEquals(GameStatus.WIN, result.getStatus());
        assertEquals(GameStatus.WIN, session.getStatus());
        assertEquals(1, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("После 6 неудачных попыток сессия переходит в статус LOSE")
    void sixFailedAttemptsLoseTheGame() {
        GameSession session = new GameSession("сорок", 1);
        GuessResult result = GameService.applyGuess(session, "ааааа");

        assertEquals(GameStatus.LOSE, result.getStatus());
        assertEquals(GameStatus.LOSE, session.getStatus());
        assertEquals("сорок", session.getAnswer());
        assertEquals(1, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("При поражении показывается загаданное слово")
    void answerIsRevealedOnLoss() {
        GameSession session = new GameSession("сорок", 6);
        for (int i = 0; i < 5; i++) {
            GameService.applyGuess(session, "тбанк");
        }
        GuessResult result = GameService.applyGuess(session, "тбанк");
        assertEquals(GameStatus.LOSE, result.getStatus());
        assertEquals(GameStatus.LOSE, session.getStatus());
        assertEquals(6, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Завершённая партия больше не принимает попытки")
    void finishedGameRejectsFurtherGuesses() {
        GameSession session = new GameSession("сорок", 6);
        GameService.applyGuess(session, "сорок");

        assertThrows(IllegalStateException.class, () -> GameService.applyGuess(session, "арбуз"));
        assertEquals(1, session.getAttemptsUsed());
    }
}
