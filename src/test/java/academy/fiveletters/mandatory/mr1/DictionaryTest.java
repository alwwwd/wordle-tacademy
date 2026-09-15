package academy.fiveletters.mandatory.mr1;

import academy.fiveletters.repository.Dictionary;
import academy.fiveletters.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Обязательные тесты: словарь. */
@DisplayName("MR1. Словарь")
class DictionaryTest {
    List<String> words = Dictionary.WORDS;

    @Test
    @DisplayName("Словарь содержит не меньше 50 слов")
    void dictionaryContainsAtLeastFiftyWords() {
        boolean containsFiftyWords = words.size() >= 50;

        assertTrue(containsFiftyWords);
    }

    @Test
    @DisplayName("Все слова словаря состоят ровно из 5 букв")
    void allWordsAreExactlyFiveLettersLong() {
        boolean allFiveLetters = words.stream().allMatch(word -> word.length() == 5);

        assertTrue(allFiveLetters);
    }

    @Test
    @DisplayName("Пустой словарь приводит к ошибке, а не к запуску игры без слова")
    void emptyDictionaryIsRejected() {
        List<String> dictionary = List.of();
        Executable action = () -> GameService.startGame(dictionary, 5, 42L);

        assertThrows(IllegalArgumentException.class, action);
    }
}
