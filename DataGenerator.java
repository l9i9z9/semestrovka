import java.io.*;
import java.util.*;

public class DataGenerator {
    public static void generateRandomData(String filename, int size) throws IOException {
        Random rand = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < size; i++) {
                char c = (char) ('a' + rand.nextInt(26));
                writer.write(c);
                if (i < size - 1) writer.write('\n');
            }
        }
    }

    public static void generateSortedData(String filename, int size) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < size; i++) {
                char c;
                if (i < size / 2) {
                    c = 'a';
                } else if (i < size * 3 / 4) {
                    c = 'b';
                } else if (i < size * 7 / 8) {
                    c = 'c';
                } else {
                    c = (char) ('d' + (i % 22));
                }
                writer.write(c);
                if (i < size - 1) writer.write('\n');
            }
        }
    }

    public static void generateAllTestData() throws IOException {
        int[] sizes = {100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000};

        new File("testdata").mkdir();

        for (int size : sizes) {
            for (int variant = 1; variant <= 10; variant++) {
                String filename = String.format("testdata/random_%d_%d.txt", size, variant);
                generateRandomData(filename, size);
            }

            for (int variant = 1; variant <= 5; variant++) {
                String filename = String.format("testdata/sorted_%d_%d.txt", size, variant);
                generateSortedData(filename, size);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        generateAllTestData();
    }
}
