import java.util.*;

public class BoyerMooreSearch {

    private static int iterationCount = 0;

    private static void resetIterationCount() {
        iterationCount = 0;
    }

    public static int getIterationCount() {
        return iterationCount;
    }

    public static int search(char[] text, char[] pattern) {
        resetIterationCount();

        if (pattern.length == 0) return 0;
        if (text.length < pattern.length) return -1;

        int n = text.length;
        int m = pattern.length;

        int[] lastOccurrence = new int[256];
        Arrays.fill(lastOccurrence, -1);

        for (int i = 0; i < m; i++) {
            lastOccurrence[pattern[i]] = i;
        }

        int shift = 0;
        while (shift <= n - m) {
            int j = m - 1;

            while (j >= 0 && pattern[j] == text[shift + j]) {
                iterationCount++;
                j--;
            }

            if (j >= 0) {
                iterationCount++;
            }

            if (j < 0) {
                return shift;
            } else {
                int badCharIndex = lastOccurrence[text[shift + j]];
                int badCharShift;

                if (badCharIndex == -1) {
                    badCharShift = j + 1;
                } else {
                    badCharShift = j - badCharIndex;
                    if (badCharShift <= 0) {
                        badCharShift = 1;
                    }
                }

                shift += badCharShift;
            }
        }
        return -1;
    }

    public static List<Integer> searchAll(char[] text, char[] pattern) {
        resetIterationCount();
        List<Integer> result = new ArrayList<>();

        if (pattern.length == 0 || text.length < pattern.length) return result;

        int n = text.length;
        int m = pattern.length;

        int[] lastOccurrence = new int[256];
        Arrays.fill(lastOccurrence, -1);

        for (int i = 0; i < m; i++) {
            lastOccurrence[pattern[i]] = i;
        }

        int shift = 0;
        while (shift <= n - m) {
            int j = m - 1;

            while (j >= 0 && pattern[j] == text[shift + j]) {
                iterationCount++;
                j--;
            }

            if (j >= 0) {
                iterationCount++;
            }

            if (j < 0) {
                result.add(shift);
                shift++;
            } else {
                int badCharIndex = lastOccurrence[text[shift + j]];
                int badCharShift;

                if (badCharIndex == -1) {
                    badCharShift = j + 1;
                } else {
                    badCharShift = j - badCharIndex;
                    if (badCharShift <= 0) {
                        badCharShift = 1;
                    }
                }

                shift += badCharShift;
            }
        }
        return result;
    }

    public static <T> int search(T[] text, T[] pattern) {
        resetIterationCount();

        if (pattern.length == 0) return 0;
        if (text.length < pattern.length) return -1;

        int n = text.length;
        int m = pattern.length;

        Map<T, Integer> lastOccurrence = new HashMap<>();
        for (int i = 0; i < m; i++) {
            lastOccurrence.put(pattern[i], i);
        }

        int shift = 0;
        while (shift <= n - m) {
            int j = m - 1;

            while (j >= 0 && Objects.equals(pattern[j], text[shift + j])) {
                iterationCount++;
                j--;
            }

            if (j >= 0) {
                iterationCount++;
            }

            if (j < 0) {
                return shift;
            } else {
                Integer badCharIndex = lastOccurrence.get(text[shift + j]);
                int badCharShift;

                if (badCharIndex == null) {
                    badCharShift = j + 1;
                } else {
                    badCharShift = j - badCharIndex;
                    if (badCharShift <= 0) {
                        badCharShift = 1;
                    }
                }

                shift += badCharShift;
            }
        }
        return -1;
    }

    public static <T> int search(List<T> list, List<T> pattern) {
        resetIterationCount();

        if (pattern.isEmpty()) return 0;
        if (list.size() < pattern.size()) return -1;

        int n = list.size();
        int m = pattern.size();

        Map<T, Integer> lastOccurrence = new HashMap<>();
        for (int i = 0; i < m; i++) {
            lastOccurrence.put(pattern.get(i), i);
        }

        int shift = 0;
        while (shift <= n - m) {
            int j = m - 1;

            while (j >= 0 && Objects.equals(pattern.get(j), list.get(shift + j))) {
                iterationCount++;
                j--;
            }

            if (j >= 0) {
                iterationCount++;
            }

            if (j < 0) {
                return shift;
            } else {
                Integer badCharIndex = lastOccurrence.get(list.get(shift + j));
                int badCharShift;

                if (badCharIndex == null) {
                    badCharShift = j + 1;
                } else {
                    badCharShift = j - badCharIndex;
                    if (badCharShift <= 0) {
                        badCharShift = 1;
                    }
                }

                shift += badCharShift;
            }
        }
        return -1;
    }

    public static <T> List<Integer> searchAll(List<T> list, List<T> pattern) {
        resetIterationCount();
        List<Integer> result = new ArrayList<>();

        if (pattern.isEmpty() || list.size() < pattern.size()) return result;

        int n = list.size();
        int m = pattern.size();

        Map<T, Integer> lastOccurrence = new HashMap<>();
        for (int i = 0; i < m; i++) {
            lastOccurrence.put(pattern.get(i), i);
        }

        int shift = 0;
        while (shift <= n - m) {
            int j = m - 1;

            while (j >= 0 && Objects.equals(pattern.get(j), list.get(shift + j))) {
                iterationCount++;
                j--;
            }

            if (j >= 0) {
                iterationCount++;
            }

            if (j < 0) {
                result.add(shift);
                shift++;
            } else {
                Integer badCharIndex = lastOccurrence.get(list.get(shift + j));
                int badCharShift;

                if (badCharIndex == null) {
                    badCharShift = j + 1;
                } else {
                    badCharShift = j - badCharIndex;
                    if (badCharShift <= 0) {
                        badCharShift = 1;
                    }
                }

                shift += badCharShift;
            }
        }
        return result;
    }
}
