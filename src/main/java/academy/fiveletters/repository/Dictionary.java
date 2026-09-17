package academy.fiveletters.repository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Dictionary {
    private static final int WORD_LENGTH = 5;

    private Dictionary() {}

    public static final List<String> WORDS = loadWords();

    private static List<String> loadWords() {

        InputStream input = Dictionary.class.getResourceAsStream("/dictionary.txt");

        if (input == null) {
            throw new IllegalStateException("Файл dictionary.txt не найден");
        }

        Scanner scanner = new Scanner(input, StandardCharsets.UTF_8);

        List<String> words = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty() && !line.startsWith("#") && line.length() == WORD_LENGTH) {
                words.add(line);
            }
        }

        scanner.close();

        return List.copyOf(words);
    }
}
