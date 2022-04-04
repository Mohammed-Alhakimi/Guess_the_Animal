package animals;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class BinaryTree {
    @Getter
    @Setter
    TreeNode root;


    public void insertAFactAndAnAnimal(TreeNode focusNode, String question, String guess, String animal2,boolean yesOrNo) {
        focusNode.data = question;
        focusNode.type = TreeNode.Type.QUESTION;
        if(yesOrNo){
            focusNode.yes = new TreeNode(animal2, TreeNode.Type.ANSWER);
            focusNode.no = new TreeNode(guess, TreeNode.Type.ANSWER);
        }else {
            focusNode.no = new TreeNode(animal2, TreeNode.Type.ANSWER);
            focusNode.yes = new TreeNode(guess, TreeNode.Type.ANSWER);
        }
    }


    public int nodes() {
        return numberOfNodes(root);
    }

    public int animals() {
        return numberOfAnimals(root);
    }

    public int height() {
        return findHeight(root) - 1;
    }

    public int minimumDepth() {
        return minimumDepth(this.root);
    }

    /**
     *  Function to calculate the height of the tree
     */
    private int findHeight(TreeNode root) {
        if (root == null) {     //Check whether tree is empty
            return 0;
        } else {
            int leftHeight = 0, rightHeight = 0;
            if (root.yes != null)       //Calculate the height of left subtree
                leftHeight = findHeight(root.yes);

            if (root.no != null)        //Calculate the height of right subtree
                rightHeight = findHeight(root.no);

            //Compare height of left subtree and right subtree  
            //and store maximum of two in variable max  
            int max = Math.max(leftHeight, rightHeight);

            return max + 1; //Calculate the total height of tree by adding height of root
        }
    }

    /**
     *  Function to calculate the minimum depth of the tree
     */
    private int minimumDepth(TreeNode root) {
        /*Corner case Should never be hit unless the code is called on root = NULL*/
        if (root == null)
            return 0;

        if (root.yes == null && root.no == null) // Base case : Leaf Node. This accounts for height = 1.
            return 1;

        if (root.yes == null)   // If left subtree is NULL, recur for right subtree
            return minimumDepth(root.no) + 1;

        if (root.no == null)    // If right subtree is NULL, recur for left subtree
            return minimumDepth(root.yes) + 1;

        return Math.min(minimumDepth(root.yes),
                minimumDepth(root.no));
    }

    private int numberOfAnimals(TreeNode node) {
        if (node == null)
            return 0;
        if (node.yes == null && node.no == null)
            return 1;
        else
            return numberOfAnimals(node.yes) + numberOfAnimals(node.no);
    }

    private int numberOfNodes(TreeNode node) {
        if (null == node) return 0;
        return 1 + numberOfNodes(node.yes) + numberOfNodes(node.no);
    }

    public int findDepth(TreeNode root, String data) {

        if (root == null)   // Base case
            return -1;

        int dist = -1;  // Initialize distance as -1

        /*Check if x is current node = data
        Otherwise, check if x is
        present in the left subtree
        Otherwise, check if x is
        present in the right subtree*/
        if ((root.data.equals(data)) ||
                (dist = findDepth(root.yes, data)) >= 0 ||
                            (dist = findDepth(root.no, data)) >= 0)
            return dist + 1;    // Return depth of the node
        return dist;
    }
}
