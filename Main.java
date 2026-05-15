public class Main {
    public static void main(String[] args) {
        System.out.println("ДЕМОНСТРАЦИЯ ОПЕРАЦИЙ");
        demonstrateOperations();

        System.out.println("ЗАПУСК ТЕСТОВ ПРОИЗВОДИТЕЛЬНОСТИ");
        try {
            HuffmanTreeBenchmark.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void demonstrateOperations() {
        HuffmanTree tree = new HuffmanTree();

        System.out.println("Вставка символов: a, b, c, a, b, a");
        tree.insert('a');
        tree.insert('b');
        tree.insert('c');
        tree.insert('a');
        tree.insert('b');
        tree.insert('a');

        System.out.println("Обход в ширину (BFS):");
        tree.printBFS();

        System.out.println("Обход в глубину (Pre-order):");
        tree.printPreOrder();

        System.out.println("Коды Хаффмана:");
        tree.printWithCodes();

        System.out.println("Поиск 'a': " + (tree.search('a') ? "найден" : "не найден"));
        System.out.println("Поиск 'd': " + (tree.search('d') ? "найден" : "не найден"));

        System.out.println("Удаление 'c':");
        tree.delete('c');
        tree.printBFS();
    }
}
