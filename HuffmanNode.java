class HuffmanNode {
    Character symbol;
    int freq;
    HuffmanNode left, right;
    HuffmanNode parent;
    int id;

    HuffmanNode(Character symbol, int freq) {
        this.symbol = symbol;
        this.freq = freq;
        this.left = this.right = this.parent = null;
        this.id = System.identityHashCode(this);
    }

    boolean isLeaf() {
        return left == null && right == null;
    }
}
