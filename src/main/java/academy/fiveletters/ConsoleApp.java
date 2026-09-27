package academy.fiveletters;

import academy.fiveletters.data.GameSession;
import academy.fiveletters.data.GuessResult;
import academy.fiveletters.enums.GameStatus;
import academy.fiveletters.repository.Dictionary;
import academy.fiveletters.service.GameService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import org.jspecify.annotations.Nullable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "five-letters", description = "Консольная игра «5 букв»", mixinStandardHelpOptions = true)
public final class ConsoleApp implements Runnable {
    private static final String RESET = "\u001B[0m";

    // Цвета текста
    private static final String YELLOW = "\033[0;33m";
    private static final String GREEN = "\u001B[32m";
    private static final  String RED = "\u001B[31m";
    private static final int MAX_ATTEMPTS = 6;

    @Nullable
    @Option(names = "--seed", description = "Начальное значение генератора случайных чисел")
    private Long seed;

    private final Scanner scanner;
    private final List<String> dictionary;

    public ConsoleApp() {
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        dictionary = Dictionary.WORDS;
    }

    @Override
    public void run() {
        long currentSeed = seed != null ? seed : System.nanoTime();

        while (true) {
            printMenu();

            String choice = scanner.nextLine();

            switch (choice) {
                case "1"-> {
                    currentSeed = playGame(currentSeed);
                    return; }
                case "2" -> {System.out.println("До свидания!");
                    return;}
                default -> {System.out.println("Некорректный выбор. Введите 1 или 2."); }
            }
        }
    }

    private void printMenu() {
        printColor("«5 букв» — Т-Академия.", YELLOW);
        System.out.println("1. Новая игра");
        System.out.println("2. Выход");
        System.out.print("Ваш выбор: ");
    }

    private long playGame(long currentSeed) {
        GameSession session = GameService.startGame(dictionary, MAX_ATTEMPTS, currentSeed);

        System.out.println();
        System.out.println("Новая игра!");

        while (session.getStatus() == GameStatus.IN_PROGRESS) {
            System.out.printf("Попытка %d из %d.%n", session.getAttemptsUsed() + 1, session.getMaxAttempts());

            System.out.print("Введите слово: ");

            String guess = scanner.nextLine();

            try {
                GuessResult result = GameService.applyGuess(session, guess);
                printResult(result, session);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return currentSeed + 1;
    }

    private void printResult(GuessResult result, GameSession session) {
        System.out.println();
        System.out.println(result.getResult() + " " + result.getGuess());

        System.out.println("Осталось попыток: " + (session.getMaxAttempts() - session.getAttemptsUsed()));

        switch (result.getStatus()) {
            case GameStatus.WIN -> { printColor("Победа! Слово угадано за " + session.getAttemptsUsed() + " попытки", GREEN);}
            case GameStatus.LOSE -> {
                printColor("Неудача. Загаданное слово: " + result.getAnswer().get(), RED);
            }
            case GameStatus.IN_PROGRESS -> {}
        }

        System.out.println();
    }

    public static void printColor(String text, String color) {
        System.out.println(color + text + RESET);
    }
}
