import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HuffmanTreeBenchmark {
    private static char[] readData(String filename) throws IOException {
        List<Character> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    list.add(line.charAt(0));
                }
            }
        }
        char[] result = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private static long measureInsert(HuffmanTree tree, char[] data) {
        long start = System.nanoTime();
        for (char c : data) {
            tree.insert(c);
        }
        long end = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMicros(end - start);
    }

    private static long measureSearch(HuffmanTree tree, char[] data) {
        long start = System.nanoTime();
        for (char c : data) {
            tree.search(c);
        }
        long end = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMicros(end - start);
    }

    private static long measureDelete(HuffmanTree tree, char[] data) {
        Set<Character> unique = new HashSet<>();
        for (char c : data) {
            unique.add(c);
        }

        long start = System.nanoTime();
        for (char c : unique) {
            tree.delete(c);
        }
        long end = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMicros(end - start);
    }

    private static long measureBFS(HuffmanTree tree) {
        long start = System.nanoTime();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {}
        }));
        tree.printBFS();
        System.setOut(originalOut);
        long end = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMicros(end - start);
    }

    private static long measureDFS(HuffmanTree tree) {
        long start = System.nanoTime();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {}
        }));
        tree.printPreOrder();
        System.setOut(originalOut);
        long end = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMicros(end - start);
    }

    public static void main(String[] args) throws IOException {
        int[] sizes = {100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000};

        Map<String, Map<Integer, List<Long>>> resultsRandom = new HashMap<>();
        Map<String, Map<Integer, List<Long>>> resultsSorted = new HashMap<>();

        String[] operations = {"insert", "search", "delete", "bfs", "dfs"};
        for (String op : operations) {
            resultsRandom.put(op, new HashMap<>());
            resultsSorted.put(op, new HashMap<>());
            for (int size : sizes) {
                resultsRandom.get(op).put(size, new ArrayList<>());
                resultsSorted.get(op).put(size, new ArrayList<>());
            }
        }

        System.out.println("Замер производительности дерева Хаффмана");

        System.out.println("1. Тестирование на случайных данных:");
        for (int size : sizes) {
            System.out.print("  Размер " + size + ": ");
            for (int variant = 1; variant <= 10; variant++) {
                String filename = String.format("testdata/random_%d_%d.txt", size, variant);
                char[] data = readData(filename);

                HuffmanTree tree = new HuffmanTree();
                long insertTime = measureInsert(tree, data);
                resultsRandom.get("insert").get(size).add(insertTime);

                long searchTime = measureSearch(tree, data);
                resultsRandom.get("search").get(size).add(searchTime);

                long bfsTime = measureBFS(tree);
                resultsRandom.get("bfs").get(size).add(bfsTime);

                long dfsTime = measureDFS(tree);
                resultsRandom.get("dfs").get(size).add(dfsTime);

                HuffmanTree tree2 = new HuffmanTree();
                for (char c : data) tree2.insert(c);
                long deleteTime = measureDelete(tree2, data);
                resultsRandom.get("delete").get(size).add(deleteTime);

                if (variant % 2 == 0) System.out.print(".");
            }
            System.out.println(" готово (" + resultsRandom.get("insert").get(size).size() + " замеров)");
        }

        System.out.println("2. Тестирование на отсортированных данных:");
        for (int size : sizes) {
            System.out.print("  Размер " + size + ": ");
            for (int variant = 1; variant <= 5; variant++) {
                String filename = String.format("testdata/sorted_%d_%d.txt", size, variant);
                char[] data = readData(filename);

                HuffmanTree tree = new HuffmanTree();
                long insertTime = measureInsert(tree, data);
                resultsSorted.get("insert").get(size).add(insertTime);

                long searchTime = measureSearch(tree, data);
                resultsSorted.get("search").get(size).add(searchTime);

                long bfsTime = measureBFS(tree);
                resultsSorted.get("bfs").get(size).add(bfsTime);

                long dfsTime = measureDFS(tree);
                resultsSorted.get("dfs").get(size).add(dfsTime);

                HuffmanTree tree2 = new HuffmanTree();
                for (char c : data) tree2.insert(c);
                long deleteTime = measureDelete(tree2, data);
                resultsSorted.get("delete").get(size).add(deleteTime);

                if (variant % 1 == 0) System.out.print(".");
            }
            System.out.println(" готово");
        }

        exportResultsToCSV(resultsRandom, "results_random.csv", sizes, operations);
        exportResultsToCSV(resultsSorted, "results_sorted.csv", sizes, operations);

        printAverageResults(resultsRandom, "СЛУЧАЙНЫЕ ДАННЫЕ", sizes, operations);
        printAverageResults(resultsSorted, "ОТСОРТИРОВАННЫЕ ДАННЫЕ", sizes, operations);
    }

    private static void exportResultsToCSV(Map<String, Map<Integer, List<Long>>> results,
                                           String filename, int[] sizes, String[] operations) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print("size,operation");
            for (int i = 1; i <= 10; i++) {
                writer.print(",run_" + i);
            }
            writer.println(",average");

            for (int size : sizes) {
                for (String op : operations) {
                    List<Long> times = results.get(op).get(size);
                    if (times != null && !times.isEmpty()) {
                        writer.print(size + "," + op);
                        long sum = 0;
                        for (int i = 0; i < times.size(); i++) {
                            writer.print("," + times.get(i));
                            sum += times.get(i);
                        }
                        double avg = (double) sum / times.size();
                        writer.printf(",%.2f\n", avg);
                    }
                }
            }
        }
    }

    private static void printAverageResults(Map<String, Map<Integer, List<Long>>> results,
                                            String title, int[] sizes, String[] operations) {
        System.out.println("СРЕДНИЕ ЗНАЧЕНИЯ (" + title + ")");
        System.out.println("Размер\tInsert(мкс)\tSearch(мкс)\tDelete(мкс)\tBFS(мкс)\tDFS(мкс)");

        for (int size : sizes) {
            System.out.print(size);
            for (String op : operations) {
                List<Long> times = results.get(op).get(size);
                if (times != null && !times.isEmpty()) {
                    double avg = times.stream().mapToLong(Long::longValue).average().orElse(0);
                    System.out.printf("\t%.2f", avg);
                } else {
                    System.out.print("\tN/A");
                }
            }
            System.out.println();
        }
    }
}
