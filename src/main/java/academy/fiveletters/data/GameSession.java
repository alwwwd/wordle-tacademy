package academy.fiveletters.data;

import static academy.fiveletters.enums.GameStatus.IN_PROGRESS;

import academy.fiveletters.enums.GameStatus;
import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private final String answer;
    private final int maxAttempts;
    private int attemptsUsed;
    private final List<String> attemptsHistory;
    private GameStatus status;
    private final List<String> dictionary;

    public GameSession(String answer, int maxAttempts, List<String> dictionary) {
        this.answer = answer;
        this.maxAttempts = maxAttempts;
        this.attemptsUsed = 0;
        this.attemptsHistory = new ArrayList<>();
        this.status = IN_PROGRESS;
        this.dictionary = dictionary;
    }

    public String getAnswer() {
        return answer;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    public List<String> getAttemptsHistory() {
        return List.copyOf(attemptsHistory);
    }

    public GameStatus getStatus() {
        return status;
    }

    public void registerValidGuess(String guess) {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Игра уже завершена");
        }

        attemptsHistory.add(guess);
        attemptsUsed++;

        if (guess.equals(answer)) {
            status = GameStatus.WIN;
        } else if (attemptsUsed >= maxAttempts) {
            status = GameStatus.LOSE;
        }
    }

    public List<String> getDictionary() {
        return dictionary;
    }
}
