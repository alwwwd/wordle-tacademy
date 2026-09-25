package academy.fiveletters.data;

import academy.fiveletters.enums.GameStatus;
import java.util.Optional;

public class GuessResult {
    private final String guess;
    private final String result;
    private final GameStatus status;
    private final Optional<String> answer;

    public GuessResult(String guess, String result, GameStatus status, Optional<String> answer) {
        this.guess = guess;
        this.result = result;
        this.status = status;
        this.answer = answer;
    }

    public String getGuess() {
        return guess;
    }

    public String getResult() {
        return result;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Optional<String> getAnswer() {
        return answer;
    }
}
