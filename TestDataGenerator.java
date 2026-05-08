import java.io.*;
import java.util.*;

public class TestDataGenerator {
    private static final Random random = new Random(42);
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";

    public static String generateRandomText(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static String generatePattern(String text, int patternLength, boolean shouldExist) {
        if (shouldExist && text.length() >= patternLength) {
            int startPos = random.nextInt(text.length() - patternLength + 1);
            return text.substring(startPos, startPos + patternLength);
        } else {
            StringBuilder sb = new StringBuilder(patternLength);
            for (int i = 0; i < patternLength; i++) {
                sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            return sb.toString();
        }
    }

    public static void saveTestSet(String filename, String text, String pattern, int expectedCount) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(text.length());
            writer.println(text);
            writer.println(pattern.length());
            writer.println(pattern);
            writer.println(expectedCount);
        }
    }

    public static void generateAllTestSets() throws IOException {
        File testDataDir = new File("test_data");
        if (!testDataDir.exists()) {
            testDataDir.mkdir();
        }

        int[] sizes = {100, 200, 500, 1000, 2000, 3000, 5000, 8000, 10000};
        int setId = 0;

        for (int size : sizes) {
            for (int variant = 0; variant < 10 && setId < 100; variant++) {
                String text = generateRandomText(size);
                int[] patternLengths = {3, 5, 10, 20, (int)(size * 0.1)};

                for (int pl : patternLengths) {
                    if (pl > size) continue;
                    if (setId >= 100) break;

                    boolean exists = random.nextDouble() < 0.7;
                    String pattern = generatePattern(text, Math.min(pl, size), exists);
                    int expectedCount = countOccurrences(text, pattern);

                    String filename = String.format("test_data/test_%03d_%d_%d.txt", setId, size, pl);
                    saveTestSet(filename, text, pattern, expectedCount);

                    System.out.printf("Сгенерирован набор %d: размер=%d, паттерн=%d, вхождений=%d%n",
                            setId, size, pattern.length(), expectedCount);

                    setId++;
                    if (setId >= 100) break;
                }
            }
        }
        System.out.println("\nВсего сгенерировано " + setId + " тестовых наборов");
    }

    private static int countOccurrences(String text, String pattern) {
        if (pattern.isEmpty()) return text.length() + 1;
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index++;
        }
        return count;
    }

    public static TestCase loadTestCase(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int textLength = Integer.parseInt(reader.readLine().trim());
            String text = reader.readLine();
            int patternLength = Integer.parseInt(reader.readLine().trim());
            String pattern = reader.readLine();
            int expectedCount = Integer.parseInt(reader.readLine().trim());
            return new TestCase(text, pattern, expectedCount);
        }
    }
}
