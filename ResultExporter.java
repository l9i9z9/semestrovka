import java.io.*;
import java.util.*;

public class ResultExporter {

    public static class DataPoint {
        public final int size;
        public final int iterations;
        public final double timeMicro;

        public DataPoint(int size, int iterations, double timeMicro) {
            this.size = size;
            this.iterations = iterations;
            this.timeMicro = timeMicro;
        }
    }

    public static void exportToCSV(String filename, List<DataPoint> data) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Размер текста,Количество итераций,Время мкс");
            for (DataPoint point : data) {
                writer.printf("%d,%d,%.3f%n", point.size, point.iterations, point.timeMicro);
            }
        }
    }

    public static void exportSummary(String filename, Map<Integer, SummaryStats> stats) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Размер,Среднее время мкс,Среднее итераций,Мин итераций,Макс итераций,Кол-во тестов");
            List<Integer> sizes = new ArrayList<>(stats.keySet());
            Collections.sort(sizes);
            for (int size : sizes) {
                SummaryStats s = stats.get(size);
                writer.printf("%d,%.3f,%.2f,%d,%d,%d%n",
                        size, s.avgTimeMicro, s.avgIterations,
                        s.minIterations, s.maxIterations, s.testCount);
            }
        }
    }

    public static class SummaryStats {
        public double avgTimeMicro;
        public double avgIterations;
        public int minIterations;
        public int maxIterations;
        public int testCount;

        public SummaryStats(double avgTimeMicro, double avgIterations,
                            int minIterations, int maxIterations, int testCount) {
            this.avgTimeMicro = avgTimeMicro;
            this.avgIterations = avgIterations;
            this.minIterations = minIterations;
            this.maxIterations = maxIterations;
            this.testCount = testCount;
        }
    }
}
