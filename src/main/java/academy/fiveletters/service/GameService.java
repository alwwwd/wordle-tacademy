package academy.fiveletters.service;

import academy.fiveletters.data.GameSession;
import academy.fiveletters.data.GuessResult;
import academy.fiveletters.enums.GameStatus;
import academy.fiveletters.repository.Dictionary;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public final class GameService {
    private static final int WORD_LENGTH = 5;
    private static final int MINIMUM_DICTIONARY_SIZE = 50;
    private static final String CORRECT = "✅";
    private static final String PRESENT = "🟡";
    private static final String ABSENT = "❌";

    private GameService() {}

    public static GameSession startGame(List<String> dictionary, int maxAttempts, long seed) {
        validateDictionary(dictionary);

        Random random = new Random(seed);
        String answer = dictionary.get(random.nextInt(dictionary.size()));

        return new GameSession(answer, maxAttempts);
    }

    public static GuessResult applyGuess(GameSession session, String guess) {
        validateSession(session);

        String normalizedGuess = normalizeGuess(guess);

        validateGuess(normalizedGuess);

        String result = checkGuess(normalizedGuess, session.getAnswer());

        session.registerValidGuess(normalizedGuess);

        String answer = session.getStatus() == GameStatus.LOSE ? session.getAnswer() : null;

        return new GuessResult(normalizedGuess, result, session.getStatus(), answer);
    }

    private static void validateSession(GameSession session) {
        if (session == null) {
            throw new IllegalArgumentException("Игровая сессия не может быть null");
        }
        if (session.getStatus() != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Игра уже завершена");
        }
        if (!Dictionary.WORDS.contains(session.getAnswer())) {
            throw new IllegalArgumentException("Такого слова нет в словаре");
        }
    }

    private static String normalizeGuess(String guess) {
        if (guess == null) {
            throw new IllegalArgumentException("Слово не может быть null");
        }

        return guess.toLowerCase(Locale.ROOT);
    }

    private static void validateGuess(String guess) {
        if (guess.length() != WORD_LENGTH) {
            throw new IllegalArgumentException("Слово должно состоять ровно из 5 букв");
        }

        for (int i = 0; i < guess.length(); i++) {
            if (!Character.isLetter(guess.charAt(i))) {
                throw new IllegalArgumentException("Слово должно содержать только буквы");
            }
        }
    }

    public static String checkGuess(String guess, String answer) {
        String[] result = new String[WORD_LENGTH];
        boolean[] used = new boolean[WORD_LENGTH];

        Arrays.fill(result, ABSENT);

        // Ищем идеальные совпадения
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = CORRECT;
                used[i] = true;
            }
        }

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (result[i].equals(CORRECT)) {
                continue;
            }

            for (int j = 0; j < WORD_LENGTH; j++) {
                if (!used[j] && guess.charAt(i) == answer.charAt(j)) {
                    result[i] = PRESENT;
                    used[j] = true;
                    break;
                }
            }
        }

        return String.join("", result);
    }

    private static void validateDictionary(List<String> dictionary) {
        if (dictionary == null || dictionary.isEmpty()) {
            throw new IllegalArgumentException("Словарь не может быть пустым");
        }
        if (dictionary.size() < MINIMUM_DICTIONARY_SIZE) {
            throw new IllegalArgumentException("Словарь не может состоять меньше чем из 50 слов");
        }
    }
}
