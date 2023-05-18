import java.util.Scanner;

class Node {
    int key, height;
    Node left, right;

    Node(int d) {
        key = d;
        height = 1;
    }
}

class AVLTree {
    Node root;

    // A utility function to get the height of the tree
    int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    // A utility function to get maximum of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    int getBalance(Node N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }

    Node insert(Node node, int key) {
        /* 1. Perform the normal BST insertion */
        if (node == null)
            return (new Node(key));

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Duplicate keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                height(node.right));

        /*
         * 3. Get the balance factor of this ancestor
         * node to check whether this node became
         * unbalanced
         */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    // prints an output
    // 1. Preorder
    void printPreorder(Node node) {
        if (node == null)
            return;

        System.out.print(node.key + " ");
        printPreorder(node.left);
        printPreorder(node.right);
    }

    void printPreorder() {
        printPreorder(root);
    }

    // 2. Inorder
    void printInorder(Node node) {
        if (node == null)
            return;

        printInorder(node.left);
        System.out.print(node.key + " ");
        printInorder(node.right);
    }

    void printInorder() {
        printInorder(root);
    }

    // 3. Postorder
    void printPostorder(Node node) {
        if (node == null)
            return;

        printPostorder(node.left);
        printPostorder(node.right);
        System.out.print(node.key + " ");
    }

    void printPostorder() {
        printPostorder(root);
    }

    // 4. Level-order
    void printLevelOrder() {
        int h = height(root);
        int i;
        for (i = 1; i <= h; i++) {
            printCurrentLevel(root, i);
        }
    }

    int heightOrder(Node root) {
        if (root == null) {
            return 0;
        } else {
            int lheightOrder = heightOrder(root.left);
            int rheightOrder = heightOrder(root.right);

            if (lheightOrder > rheightOrder) {
                return (lheightOrder + 1);
            } else {
                return (rheightOrder + 1);
            }
        }
    }

    void printCurrentLevel(Node root, int level) {
        if (root == null) {
            return;
        }
        if (level == 1) {
            System.out.print(root.key + " ");
        } else if (level > 1) {
            printCurrentLevel(root.left, level - 1);
            printCurrentLevel(root.right, level - 1);
        }
    }
}

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int check = 0, check_opt = 0, check_case1 = 0;

        AVLTree avlt = new AVLTree();

        while (check == 0) {
            System.out.printf(
                    "[1] insert integer x\n[2] print preorder\n[3] print inorder\n[4] print postorder\n[5] print levels\n[6] exit\n");
            check_opt = 0;
            while (check_opt == 0) {
                System.out.print("> ");
                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        check_opt = 1;
                        int i = 0;
                        System.out.println("insert an integer");
                        check_case1 = 0;
                        while (check_case1 == 0) {
                            System.out.print("> ");
                            String s2 = sc.nextLine();
                            try {
                                i = Integer.parseInt(s2);
                                check_case1 = 1;
                            } catch (Exception e) {
                                System.out.println("invalid input");
                                check_case1 = 0;
                            }
                        }
                        avlt.root = avlt.insert(avlt.root, i);
                        break;
                    case "2":
                        check_opt = 1;
                        System.out.println("preorder of tree:");
                        System.out.print("> ");
                        avlt.printPreorder();
                        System.out.printf("\n\n");
                        break;
                    case "3":
                        check_opt = 1;
                        System.out.println("inorder of tree:");
                        System.out.print("> ");
                        avlt.printInorder();
                        System.out.printf("\n\n");
                        break;
                    case "4":
                        check_opt = 1;
                        System.out.println("postorder of tree:");
                        System.out.print("> ");
                        avlt.printPostorder();
                        System.out.printf("\n\n");
                        break;
                    case "5":
                        check_opt = 1;
                        System.out.println("level-order of tree:");
                        System.out.print("> ");
                        avlt.printLevelOrder();
                        System.out.printf("\n\n");
                        break;
                    case "6":
                        check_opt = 1;
                        check = 1;
                        break;
                    default:
                        System.out.println("invalid input");
                        check_opt = 0;
                }
            }
        }

        sc.close();
    }
}

