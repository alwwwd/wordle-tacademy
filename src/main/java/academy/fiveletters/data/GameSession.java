package academy.fiveletters.data;

import academy.fiveletters.enums.GameStatus;

import java.util.ArrayList;
import java.util.List;

import static academy.fiveletters.enums.GameStatus.IN_PROGRESS;

public class GameSession {
    private static final int WORD_LENGTH = 5;

    private final String answer;
    private final int maxAttempts;
    private int attemptsUsed = 0;
    private final List<String> attemptsHistory;
    private GameStatus status;

    public GameSession(String answer, int maxAttempts) {
        if (answer == null || answer.length() != WORD_LENGTH) {
            throw new IllegalArgumentException("Ответ должен состоять из 5 букв");
        }

        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("Количество попыток должно быть больше 0");
        }

        this.answer = answer;
        this.maxAttempts = maxAttempts;
        this.attemptsHistory = new ArrayList<>();
        this.status = IN_PROGRESS;
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

    public void setStatus(GameStatus status) {
        if (this.status == IN_PROGRESS) {
            this.status = status;
        }
    }
}
