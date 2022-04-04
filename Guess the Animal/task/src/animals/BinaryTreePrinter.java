package animals;

import java.io.PrintStream;

public class BinaryTreePrinter {

    private final BinaryTree binaryTree;

    public BinaryTreePrinter(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }

    public void traversePreOrder(StringBuilder sb, String padding, String pointer, TreeNode node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getData());
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("│  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.getNo() != null) ? "├──" : "└──";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.getYes());
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.getNo());
        }
    }
    public void print(PrintStream os) {
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "", this.binaryTree.root);
        os.print(sb);
    }
}
