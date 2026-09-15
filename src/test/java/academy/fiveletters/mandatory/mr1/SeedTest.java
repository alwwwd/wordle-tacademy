package academy.fiveletters.mandatory.mr1;

import academy.fiveletters.data.GameSession;
import academy.fiveletters.repository.Dictionary;
import academy.fiveletters.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Обязательные тесты: одинаковый seed обязан давать одинаковое загаданное слово. */
@DisplayName("MR1. Воспроизводимость по seed")
class SeedTest {

    @Test
    @DisplayName("Одинаковый seed даёт одинаковое загаданное слово")
    void sameSeedProducesSameAnswer() {
        long seed = 42L;
        GameSession game1 = GameService.startGame(Dictionary.WORDS, 6, seed);
        GameSession game2 = GameService.startGame(Dictionary.WORDS, 7, seed);
        boolean answerIsEqual = game1.getAnswer().equals(game2.getAnswer());

        assertTrue(answerIsEqual);
    }

    @Test
    @DisplayName("Разные seed'ы дают разные слова хотя бы иногда")
    void differentSeedsProduceDifferentAnswers() {
        long seed1 = 42L;
        long seed2 = 43L;
        GameSession game1 = GameService.startGame(Dictionary.WORDS, 6, seed1);
        GameSession game2 = GameService.startGame(Dictionary.WORDS, 6, seed2);
        boolean answerIsNotEqual = !game1.getAnswer().equals(game2.getAnswer());

        assertTrue(answerIsNotEqual);
    }
}
