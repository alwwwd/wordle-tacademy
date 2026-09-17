package academy.fiveletters.service;

import academy.fiveletters.data.GameSession;
import java.util.List;
import java.util.Random;

public final class GameService {
    private GameService() {}

    public static GameSession startGame(List<String> dictionary, int maxAttempts, long seed) {
        if (dictionary == null || dictionary.isEmpty()) {
            throw new IllegalArgumentException("Словарь не может быть пустым");
        }

        Random random = new Random(seed);
        String answer = dictionary.get(random.nextInt(dictionary.size()));

        return new GameSession(answer, maxAttempts);
    }
}
