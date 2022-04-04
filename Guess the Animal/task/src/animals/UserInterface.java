package animals;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.*;

import static animals.GrammarUtils.*;

public class UserInterface {

    private final static Scanner scanner = new Scanner(System.in);
    private final String typeMapping;
    private List<String> animals;
    private Deque<String> animalFacts;
    BinaryTree tree = new BinaryTree();

    public UserInterface(String typeMapping) {
        this.typeMapping = typeMapping;
    }

    private void loadTree(String typeMapping) {
        String fileName = "animals." + typeMapping;
        ObjectMapper mapper = new MapperFactory().createObjectMapper(typeMapping);
        try {
            this.tree.root = mapper.readValue(new File(fileName), TreeNode.class);
        } catch (IOException e) {
            //In case the file was not found that means that the tree is not saved yet so the tree will be initialized
            initializeTree();
        }
    }

    private void initializeTree() {
        prt("I want to learn about animals.\n" +
                "Which animal do you like most?");
        String fav = processArticle(scanner.nextLine());
        newLine();
        this.tree.setRoot(new TreeNode(fav, TreeNode.Type.ANSWER));
    }


    public void guessingGame() {
        loadTree(typeMapping);
        showMenu();
    }

    private void showMenu() {

        boolean exit = false;
        while (!exit) {
            prtMenuOptions();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    playTheGame();
                    break;
                case "2":
                    printListOfAnimals();
                    break;
                case "3":
                    search();
                    break;
                case "4":
                    printStats();
                    break;
                case "5":
                printTree();
                    break;
                case "0":
                    exit = true;
                    prt(ReplyGen.generateByeAnswer());
                    break;
                default:
                    break;
            }
        }
    }

    private void printTree() {
        new BinaryTreePrinter(tree).print(System.out);
    }

    private void printStats() {
        String root = changeQuestionToFact(tree.root.getData(), true);
        int nodesCount = tree.nodes();
        int animalsCount = tree.animals();
        int statementsCount = nodesCount - animalsCount;
        int treeHeight = tree.height();
        int minDepth = tree.minimumDepth();
        double avGDepthAnimals = getAvgDepth();
        prt("The Knowledge Tree stats\n");

        prt("- root node                    " + root);
        prt("- total number of nodes        " + nodesCount);
        prt("- total number of animals      " + animalsCount);
        prt("- total number of statements   " + statementsCount);
        prt("- height of the tree           " + treeHeight);
        prt("- minimum animal's depth       " + minDepth);
        prt("- average animal's depth       " + avGDepthAnimals);
    }

    private double getAvgDepth() {
        animals = new ArrayList<>();
        inOrderTraverse(tree.root);
        AtomicInteger sumOfDepths = new AtomicInteger(0);
        animals.forEach(animal->{
            sumOfDepths.getAndAdd(tree.findDepth(tree.root,animal));
        });
        return (double)sumOfDepths.get() / tree.animals();
    }


    private void search() {
        searchForAnAnimal();
    }

    private void searchForAnAnimal() {
        prt("Your choice:\n" +
                "3");
        prt("Enter the animal:");
        String animalToSearch = scanner.nextLine().toLowerCase(Locale.ROOT);
        TreeNode animal = search(animalToSearch, tree.root);
        try {
            animalFacts = new ArrayDeque<>();
            animalFacts.addFirst(animal.getData());
            addAncestorsToFactsDeque(tree.root, animal.data);
            Deque<String> animalFactsToPrint = new ArrayDeque<>();
            while (animalFacts.size() > 1) {
                String child = animalFacts.pop();
                TreeNode parent = search(animalFacts.peek(), tree.root);
                if (parent.yes.getData().equals(child)) {
                    animalFactsToPrint.addFirst(changeQuestionToFact(parent.getData(), true));
                } else if (parent.no.getData().equals(child)) {
                    animalFactsToPrint.addFirst(changeQuestionToFact(parent.getData(), false));
                }

            }
            prt("Facts about the " + animalToSearch);
            animalFactsToPrint.forEach(System.out::println);
        } catch (NullPointerException e) {
            prt("No facts about the " + animalToSearch + ".");
        }

    }

    // Iterative Function to print all ancestors of a given key
    private void addAncestorsToFactsDeque(TreeNode root, String key) {
        if (root == null)
            return;

        // Create a stack to hold ancestors
        Stack<TreeNode> st = new Stack<>();

        // Traverse the complete tree in postorder way till we find the key
        while (true) {

            // Traverse the left side. While traversing, push the nodes into
            // the stack so that their right subtrees can be traversed later
            while (root != null && root.data != key) {
                st.push(root);   // push current node
                root = root.yes;   // move to next node
            }

            // If the node whose ancestors are to be printed is found,
            // then break the while loop.
            if (root != null && root.data.equals(key))
                break;

            // Check if right sub-tree exists for the node at top
            // If not then pop that node because we don't need this
            // node any more.
            if (st.peek().no == null) {
                root = st.peek();
                st.pop();

                // If the popped node is right child of top, then remove the top
                // as well. Left child of the top must have processed before.
                while (st.empty() == false && st.peek().no == root) {
                    root = st.peek();
                    st.pop();
                }
            }

            // if stack is not empty then simply set the root as right child
            // of top and start traversing right sub-tree.
            root = st.empty() ? null : st.peek().no;
        }

        // If stack is not empty, print contents of stack
        // Here assumption is that the key is there in tree
        while (!st.empty()) {
            animalFacts.addLast(st.peek().data);
            st.pop();
        }
    }

    private TreeNode search(String name, TreeNode node) {
        if (node != null) {
            if (node.getData().equals(name)) {
                return node;
            } else {
                TreeNode foundNode = search(name, node.yes);
                if (foundNode == null) {
                    foundNode = search(name, node.no);
                }
                return foundNode;
            }
        } else {
            return null;
        }
    }

    private void printListOfAnimals() {
        prt("Your choice:\n" +
                "2");
        animals = new ArrayList<>();
        inOrderTraverse(tree.root);
        if (animals.isEmpty()) {
            prt("You don't have any animals yet!");
        } else {
            prt("Here are the animals I know:");
            Collections.sort(animals);
            animals.forEach(s -> prt("- " + stripArticles(s)));
        }
        newLine();
    }

    /**
     * @param focusNode starts from the root of the tree
     *                  This is s helper method for printing the list of animals
     */
    private void inOrderTraverse(TreeNode focusNode) {
        if (focusNode != null) {
            if (!focusNode.isQuestion()) {
                animals.add(focusNode.data);
            }
            inOrderTraverse(focusNode.yes);
            inOrderTraverse(focusNode.no);
        }
    }

    private void playTheGame() {
        startGame();
        TreeNode focusNode = traverse(tree.root);
        String guess = focusNode.getData();
        prt("Is it " + processArticle(focusNode.getData()) + "?");
        String reply = stripReplies(scanner.nextLine());
        while (true) {
            Matcher positiveMatcher = YES_REPLY.matcher(reply);
            Matcher negativeMatcher = NO_REPLY.matcher(reply);
            if (negativeMatcher.matches()) {
                prt("I give up. What animal do you have in mind?");
                String animal2 = processArticle(scanner.nextLine());
                while (true) {
                    String statement = specifyStatement(guess, animal2);
                    Matcher sMatcher = STATEMENT.matcher(statement);
                    if (sMatcher.matches()) {
                        prt("Is the statement correct for " + animal2 + "?");
                        String replyYesOrNo = stripReplies(scanner.nextLine());
                        while (true) {
                            Matcher yesMatcher = YES_REPLY.matcher(replyYesOrNo);
                            Matcher noMatcher = NO_REPLY.matcher(replyYesOrNo);
                            if (yesMatcher.matches() || noMatcher.matches()) {
                                guess = stripArticles(guess);
                                animal2 = stripArticles(animal2);
                                String verb = sMatcher.group(1);
                                String extraInfo = sMatcher.group(2);
                                String question = prtStatusAndGenerateQuestion(guess, animal2, verb, extraInfo, noMatcher);
                                if (yesMatcher.matches()) {
                                    tree.insertAFactAndAnAnimal(focusNode, question, guess, animal2,true);
                                } else if (noMatcher.matches()) {
                                    tree.insertAFactAndAnAnimal(focusNode, question, guess, animal2,false);
                                }
                                break;
                            }
                            prt(ReplyGen.genInvalidAns());
                            replyYesOrNo = stripReplies(scanner.nextLine());
                        }
                        break;
                    }
                    printErrorStatement();
                }
                break;
            } else if (positiveMatcher.matches()) {
                prt("I have guessed the animal");
                break;
            }
            prt(ReplyGen.genInvalidAns());
            reply = stripReplies(scanner.nextLine());
        }
        playAgain();
    }

    private void mapTree(TreeNode root, String typeMapping) {
        String fileName = "animals." + typeMapping;
        ObjectMapper mapper = new MapperFactory().createObjectMapper(typeMapping);
        try {
            mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(fileName), root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TreeNode traverse(TreeNode root) {
        TreeNode focusNode = root;
        while (focusNode.getType() != TreeNode.Type.ANSWER) {
            prt(focusNode.getData());
            String reply = stripReplies(scanner.nextLine());
            Matcher positiveMatcher = YES_REPLY.matcher(reply);
            Matcher negativeMatcher = NO_REPLY.matcher(reply);
            if (positiveMatcher.matches()) {
                focusNode = focusNode.yes;
            } else if (negativeMatcher.matches()) {
                focusNode = focusNode.no;
            }
        }
        return focusNode;
    }

    private void playAgain() {
        prt("Nice! I've learned so much about animals!\n");
        prt("Would you like to play again?");
        String reply = stripReplies(scanner.nextLine());
        Matcher yes = YES_REPLY.matcher(reply);
        Matcher no = NO_REPLY.matcher(reply);
        if (yes.matches()) {
            mapTree(this.tree.getRoot(), this.typeMapping);
            playTheGame();
        } else if (no.matches()) {
            mapTree(this.tree.getRoot(), this.typeMapping);
        }
    }

}