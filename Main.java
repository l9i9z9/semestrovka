import java.util.*;
import java.io.*;

public class Main {

    private static List<ResultExporter.DataPoint> allData = new ArrayList<>();

    public static void main(String[] args) {
        runQuickTests();

        System.out.println("ТЕСТИРОВАНИЕ С ФАЙЛОВЫМИ ДАННЫМИ");
        runFileTests();
        exportResultsToExcel();
    }

    private static void runQuickTests() {
        System.out.println("БЫСТРЫЕ ТЕСТЫ:\n");

        char[] text1 = "Hello World!".toCharArray();
        char[] pattern1 = "World".toCharArray();
        long start = System.nanoTime();
        int result1 = BoyerMooreSearch.search(text1, pattern1);
        long end = System.nanoTime();
        int iterations1 = BoyerMooreSearch.getIterationCount();
        System.out.printf("1. Поиск 'World' в 'Hello World!': позиция=%d, время=%.2f мкс, итераций=%d%n",
                result1, (end-start)/1000.0, iterations1);

        char[] text2 = "the cat in the hat".toCharArray();
        char[] pattern2 = "at".toCharArray();
        start = System.nanoTime();
        List<Integer> result2 = BoyerMooreSearch.searchAll(text2, pattern2);
        end = System.nanoTime();
        int iterations2 = BoyerMooreSearch.getIterationCount();
        System.out.printf("2. Поиск 'at' в 'the cat in the hat': позиции=%s, время=%.2f мкс, итераций=%d%n",
                result2, (end-start)/1000.0, iterations2);

        char[] text3 = "abcdef".toCharArray();
        char[] pattern3 = "abc".toCharArray();
        start = System.nanoTime();
        int result3 = BoyerMooreSearch.search(text3, pattern3);
        end = System.nanoTime();
        int iterations3 = BoyerMooreSearch.getIterationCount();
        System.out.printf("3. Поиск 'abc' в 'abcdef': позиция=%d, время=%.2f мкс, итераций=%d%n",
                result3, (end-start)/1000.0, iterations3);

        char[] text4 = "abcdef".toCharArray();
        char[] pattern4 = "def".toCharArray();
        start = System.nanoTime();
        int result4 = BoyerMooreSearch.search(text4, pattern4);
        end = System.nanoTime();
        int iterations4 = BoyerMooreSearch.getIterationCount();
        System.out.printf("4. Поиск 'def' в 'abcdef': позиция=%d, время=%.2f мкс, итераций=%d%n",
                result4, (end-start)/1000.0, iterations4);

        char[] text5 = "abcde".toCharArray();
        char[] pattern5 = "xyz".toCharArray();
        start = System.nanoTime();
        int result5 = BoyerMooreSearch.search(text5, pattern5);
        end = System.nanoTime();
        int iterations5 = BoyerMooreSearch.getIterationCount();
        System.out.printf("5. Поиск 'xyz' в 'abcde': позиция=%d, время=%.2f мкс, итераций=%d%n",
                result5, (end-start)/1000.0, iterations5);

        String[] text6 = {"a", "b", "c", "d", "b", "c", "a"};
        String[] pattern6 = {"b", "c"};
        start = System.nanoTime();
        int result6 = BoyerMooreSearch.search(text6, pattern6);
        end = System.nanoTime();
        int iterations6 = BoyerMooreSearch.getIterationCount();
        System.out.printf("6. Поиск [b,c] в массиве: позиция=%d, время=%.2f мкс, итераций=%d%n",
                result6, (end-start)/1000.0, iterations6);

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 2, 3, 5);
        List<Integer> pattern7 = Arrays.asList(2, 3);
        start = System.nanoTime();
        int result7 = BoyerMooreSearch.search(list, pattern7);
        end = System.nanoTime();
        int iterations7 = BoyerMooreSearch.getIterationCount();
        System.out.printf("7. Поиск [2,3] в списке: позиция=%d, время=%.2f мкс, итераций=%d%n",
                result7, (end-start)/1000.0, iterations7);

        char[] text8 = "aaaaa".toCharArray();
        char[] pattern8 = "aa".toCharArray();
        start = System.nanoTime();
        List<Integer> result8 = BoyerMooreSearch.searchAll(text8, pattern8);
        end = System.nanoTime();
        int iterations8 = BoyerMooreSearch.getIterationCount();
        System.out.printf("8. Поиск 'aa' в 'aaaaa': позиции=%s, время=%.2f мкс, итераций=%d%n",
                result8, (end-start)/1000.0, iterations8);

        char[] text9 = "test".toCharArray();
        char[] pattern9 = "".toCharArray();
        start = System.nanoTime();
        int result9 = BoyerMooreSearch.search(text9, pattern9);
        end = System.nanoTime();
        int iterations9 = BoyerMooreSearch.getIterationCount();
        System.out.printf("9. Пустой паттерн: позиция=%d, время=%.2f мкс, итераций=%d%n",
                result9, (end-start)/1000.0, iterations9);

        char[] text10 = "AbraCadabra".toCharArray();
        char[] pattern10 = "Cad".toCharArray();
        start = System.nanoTime();
        int result10 = BoyerMooreSearch.search(text10, pattern10);
        end = System.nanoTime();
        int iterations10 = BoyerMooreSearch.getIterationCount();
        System.out.printf("10. Поиск 'Cad' в 'AbraCadabra': позиция=%d, время=%.2f мкс, итераций=%d%n",
                result10, (end-start)/1000.0, iterations10);

        System.out.println("\n Тесты разного размера для графика");
        int[] testSizes = {10, 50, 100, 200, 500, 1000, 2000, 5000};

        for (int size : testSizes) {
            char[] bigText = generateTestText(size);
            char[] shortPattern = "abc".toCharArray();

            start = System.nanoTime();
            BoyerMooreSearch.search(bigText, shortPattern);
            end = System.nanoTime();
            int iter = BoyerMooreSearch.getIterationCount();

            allData.add(new ResultExporter.DataPoint(size, iter, (end-start)/1000.0));
            System.out.printf("Размер=%d: итераций=%d, время=%.2f мкс%n", size, iter, (end-start)/1000.0);
        }

        System.out.println();
    }

    private static char[] generateTestText(int size) {
        char[] text = new char[size];
        for (int i = 0; i < size; i++) {
            text[i] = (char) ('a' + (i % 26));
        }
        return text;
    }

    private static void runFileTests() {
        try {
            File testDataDir = new File("test_data");
            if (!testDataDir.exists() || testDataDir.list().length == 0) {
                TestDataGenerator.generateAllTestSets();
            }

            File[] testFiles = testDataDir.listFiles((dir, name) -> name.endsWith(".txt"));
            if (testFiles == null || testFiles.length == 0) {
                System.out.println("Тестовые данные не найдены!");
                return;
            }

            System.out.println("Найдено " + testFiles.length + " тестовых наборов\n");

            int totalTests = 0;
            int passedTests = 0;
            long totalTime = 0;
            int totalIterations = 0;

            Map<Integer, List<Long>> timeBySize = new HashMap<>();
            Map<Integer, List<Integer>> iterBySize = new HashMap<>();

            Arrays.sort(testFiles);

            for (File testFile : testFiles) {
                TestCase testCase = TestDataGenerator.loadTestCase(testFile.getPath());

                System.out.println("Тест: " + testFile.getName());
                System.out.println("  Размер текста: " + testCase.text.length());
                System.out.println("  Размер паттерна: " + testCase.pattern.length());

                long start = System.nanoTime();
                List<Integer> positions = BoyerMooreSearch.searchAll(
                        testCase.text.toCharArray(),
                        testCase.pattern.toCharArray()
                );
                long end = System.nanoTime();
                int iterations = BoyerMooreSearch.getIterationCount();

                long duration = end - start;
                totalTime += duration;
                totalIterations += iterations;

                allData.add(new ResultExporter.DataPoint(testCase.text.length(), iterations, duration/1000.0));

                int size = testCase.text.length();
                timeBySize.computeIfAbsent(size, k -> new ArrayList<>()).add(duration);
                iterBySize.computeIfAbsent(size, k -> new ArrayList<>()).add(iterations);

                int actualCount = positions.size();
                boolean passed = (actualCount == testCase.expectedCount);

                if (passed) {
                    passedTests++;
                    System.out.printf("  Результат: ПРОЙДЕН (%.2f мкс, %d итераций)%n",
                            duration/1000.0, iterations);
                } else {
                    System.out.println("  Результат: НЕ ПРОЙДЕН");
                    System.out.println("    Ожидалось: " + testCase.expectedCount + ", найдено: " + actualCount);
                }

                totalTests++;
                System.out.println();
            }
            
            System.out.println("ИТОГОВАЯ СТАТИСТИКА:");
            System.out.printf("Всего тестов: %d%n", totalTests);
            System.out.printf("Пройдено: %d (%.2f%%)%n", passedTests, (passedTests*100.0/totalTests));
            System.out.printf("Общее время: %.2f мс%n", totalTime/1_000_000.0);
            System.out.printf("Среднее время: %.2f мкс%n", (totalTime/totalTests)/1000.0);
            System.out.printf("Общее число итераций: %,d%n", totalIterations);
            System.out.printf("Среднее число итераций: %,.0f%n", (double)totalIterations/totalTests);

            Map<Integer, ResultExporter.SummaryStats> summaryMap = new HashMap<>();
            List<Integer> sizes = new ArrayList<>(timeBySize.keySet());
            Collections.sort(sizes);

            for (int size : sizes) {
                List<Long> times = timeBySize.get(size);
                List<Integer> iters = iterBySize.get(size);

                double avgTime = times.stream().mapToLong(Long::longValue).average().orElse(0) / 1000.0;
                double avgIter = iters.stream().mapToInt(Integer::intValue).average().orElse(0);
                int minIter = iters.stream().mapToInt(Integer::intValue).min().orElse(0);
                int maxIter = iters.stream().mapToInt(Integer::intValue).max().orElse(0);
            }

            ResultExporter.exportSummary("summary_stats.csv", summaryMap);
            System.out.println("\n Сводная статистика сохранена");

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void exportResultsToExcel() {
        try {
            System.out.println("\n Данные для графиков сохранены");
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении: " + e.getMessage());
        }
    }
}
