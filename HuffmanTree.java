import java.util.*;

public class HuffmanTree {
    private HuffmanNode root;
    private Map<Character, HuffmanNode> nodeMap;
    private PriorityQueue<HuffmanNode> heap;

    public HuffmanTree() {
        nodeMap = new HashMap<>();
        heap = new PriorityQueue<>((a, b) -> {
            if (a.freq != b.freq) return Integer.compare(a.freq, b.freq);
            return Integer.compare(a.id, b.id);
        });
        root = null;
    }

    public void insert(char symbol) {
        if (nodeMap.containsKey(symbol)) {
            HuffmanNode node = nodeMap.get(symbol);
            increaseFrequency(node);
        } else {
            HuffmanNode leaf = new HuffmanNode(symbol, 1);
            nodeMap.put(symbol, leaf);

            if (root == null) {
                root = leaf;
            } else {
                HuffmanNode internal = new HuffmanNode(null, root.freq + 1);
                internal.left = root;
                internal.right = leaf;
                root.parent = internal;
                leaf.parent = internal;
                root = internal;

                updateFrequencies(leaf);
            }
        }
    }

    private void increaseFrequency(HuffmanNode node) {
        node.freq++;
        updateFrequencies(node);
    }

    private void updateFrequencies(HuffmanNode node) {
        if (node == null) return;

        HuffmanNode swapNode = findNodeToSwap(node);

        if (swapNode != null && swapNode != node && swapNode.parent != node) {
            swapNodes(node, swapNode);
        }

        if (node.parent != null) {
            node.parent.freq = node.parent.left.freq + node.parent.right.freq;
            updateFrequencies(node.parent);
        } else {
            root = node;
        }
    }

    private HuffmanNode findNodeToSwap(HuffmanNode node) {
        return null;
    }

    private void swapNodes(HuffmanNode a, HuffmanNode b) {
        if (a.parent == null || b.parent == null) return;

        HuffmanNode parentA = a.parent;
        HuffmanNode parentB = b.parent;

        if (parentA.left == a) parentA.left = b;
        else parentA.right = b;

        if (parentB.left == b) parentB.left = a;
        else parentB.right = a;

        HuffmanNode tempParent = a.parent;
        a.parent = b.parent;
        b.parent = tempParent;
    }

    public boolean search(char symbol) {
        return nodeMap.containsKey(symbol);
    }

    public int getFrequency(char symbol) {
        HuffmanNode node = nodeMap.get(symbol);
        return node == null ? 0 : node.freq;
    }

    public void delete(char symbol) {
        HuffmanNode node = nodeMap.get(symbol);
        if (node == null) return;

        if (node == root) {
            root = null;
            nodeMap.clear();
            return;
        }

        HuffmanNode parent = node.parent;
        HuffmanNode sibling = (parent.left == node) ? parent.right : parent.left;

        if (parent.parent != null) {
            if (parent.parent.left == parent) {
                parent.parent.left = sibling;
            } else {
                parent.parent.right = sibling;
            }
            sibling.parent = parent.parent;
        } else {
            root = sibling;
            sibling.parent = null;
        }

        nodeMap.remove(symbol);

        updateFrequencies(sibling);
    }

    public void printBFS() {
        if (root == null) {
            System.out.println("Дерево пусто");
            return;
        }

        Queue<HuffmanNode> queue = new LinkedList<>();
        queue.add(root);

        int level = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Уровень " + level + ": ");

            for (int i = 0; i < levelSize; i++) {
                HuffmanNode node = queue.poll();
                if (node.symbol != null) {
                    System.out.print("[" + node.symbol + ":" + node.freq + "] ");
                } else {
                    System.out.print("[внутр:" + node.freq + "] ");
                }

                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            System.out.println();
            level++;
        }
    }

    public void printPreOrder() {
        System.out.print("Pre-order: ");
        preOrderRec(root);
        System.out.println();
    }

    private void preOrderRec(HuffmanNode node) {
        if (node == null) return;
        if (node.symbol != null) {
            System.out.print(node.symbol + "(" + node.freq + ") ");
        } else {
            System.out.print("[" + node.freq + "] ");
        }
        preOrderRec(node.left);
        preOrderRec(node.right);
    }

    public void printInOrder() {
        System.out.print("In-order: ");
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(HuffmanNode node) {
        if (node == null) return;
        inOrderRec(node.left);
        if (node.symbol != null) {
            System.out.print(node.symbol + "(" + node.freq + ") ");
        } else {
            System.out.print("[" + node.freq + "] ");
        }
        inOrderRec(node.right);
    }

    public Map<Character, String> getHuffmanCodes() {
        Map<Character, String> codes = new HashMap<>();
        generateCodes(root, "", codes);
        return codes;
    }

    private void generateCodes(HuffmanNode node, String code, Map<Character, String> codes) {
        if (node == null) return;
        if (node.isLeaf()) {
            codes.put(node.symbol, code);
        } else {
            generateCodes(node.left, code + "0", codes);
            generateCodes(node.right, code + "1", codes);
        }
    }

    public void printWithCodes() {
        Map<Character, String> codes = getHuffmanCodes();
        System.out.println("\nКоды Хаффмана:");
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.println("  '" + entry.getKey() + "' -> " + entry.getValue() + " (частота: " + getFrequency(entry.getKey()) + ")");
        }
    }

    HuffmanNode getRoot() {
        return root;
    }
}
