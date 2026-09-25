package academy.fiveletters.data;

import academy.fiveletters.enums.GameStatus;

public class GuessResult {
    private final String guess;
    private final String result;
    private final GameStatus status;
    private final String answer;

    public GuessResult(String guess, String result, GameStatus status, String answer) {
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

    public String getAnswer() {
        return answer;
    }
}
