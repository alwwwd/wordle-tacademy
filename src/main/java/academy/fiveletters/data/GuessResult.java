package academy.fiveletters.data;

import academy.fiveletters.enums.GameStatus;

public class GuessResult {
    private final String guess;
    private final String result;
    private final GameStatus status;

    public GuessResult(String guess, String result, GameStatus status) {
        this.guess = guess;
        this.result = result;
        this.status = status;
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
}
