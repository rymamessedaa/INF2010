package tp3;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

public class AvlTree<ValueType extends Comparable<? super ValueType> > {

    private BinaryNode<ValueType> root;

    public AvlTree() {
    }

    /**
     * Adds value to the tree and keeps it as a balanced AVL Tree
     *
     * @param value value to add to the tree
     */
    public void insert(ValueType value) {
        if (root == null) {
            root = new BinaryNode<ValueType>(value, null);
        } else {
            insert(value, root);
        }
    }

    /**
     * Removes value from the tree and keeps it as a balanced AVL Tree
     *
     * @param value value to add to the tree
     */
    public void remove(ValueType value) {
        remove(value, root);
    }

    /**
     * Verifies if the tree contains value
     *
     * @param value value to verify
     * @return if value already exists in the tree
     */
    public boolean contains(ValueType value) {
        return contains(value, root);
    }

    /**
     * Returns the max level contained in our root tree
     *
     * @return Max level contained in our root tree
     */
    public int getHeight() {
        return getLevelCount(root) - 1;
    }

    /**
     * Returns the minimal value contained in our root tree
     *
     * @return Minimal value contained in our root tree
     */
    public ValueType findMin() {
        BinaryNode<ValueType> minimalNode = findMin(root);
        if (minimalNode == null) return null;
        return minimalNode.value;
    }

    /**
     * Returns all values contained in the root tree in ascending order
     *
     * @return Values contained in the root tree in ascending order
     */
    public List<ValueType> infixOrder() {
        List<ValueType> items = new LinkedList<ValueType>();
        infixOrder(root, items);
        return items;
    }

    /**
     * Returns all values contained in the root tree in level order from top to bottom
     *
     * @return Values contained in the root tree in level order from top to bottom
     */
    public List<ValueType> levelOrder() {
        List<ValueType> items = new LinkedList<ValueType>();

        ArrayDeque<BinaryNode<ValueType>> nodesToCheck = new ArrayDeque<BinaryNode<ValueType>>();

        if (root != null) {
            nodesToCheck.push(root);
            levelOrder(nodesToCheck, items);
        }

        return items;
    }

    /**
     * TODO O( log n )
     * Adds value to the tree and keeps it as a balanced AVL Tree
     * Should call balance only if insertion succeeds
     * AVL Trees do not contain duplicates
     *
     * @param value       value to add to the tree
     * @param currentNode Node currently being accessed in this recursive method
     * @return if parent node should balance
     */
    private boolean insert(ValueType value, BinaryNode<ValueType> currentNode) {
        int diffrence = value.compareTo(currentNode.value);
        boolean balance = false;

        if (diffrence == 0) {
            return false;
        } else if (diffrence < 0){
            if(currentNode.left != null){
                balance = insert(value, currentNode.left);
            }else{
                currentNode.left = new BinaryNode<ValueType>(value, currentNode);
                return true;
            }
        }else if(diffrence > 0){
            if(currentNode.right != null){
                balance = insert(value, currentNode.right);
            }else{
                currentNode.right = new BinaryNode<ValueType>(value, currentNode);
                return true;
            }
        }

        if(balance == true){
            if(getLevelCount(currentNode.left) - getLevelCount(currentNode.right) > 1 || getLevelCount(currentNode.right) - getLevelCount(currentNode.left) > 1){
                balance(currentNode);
            }
            return true;
        }
        return false;
    }

    /**
     * TODO O ( log n )
     * Removes value from the tree and keeps it as a balanced AVL Tree
     * Should call balance only if removal succeeds
     *
     * @param value       value to remove from the tree
     * @param currentNode Node currently being accessed in this recursive method
     * @return if parent node should balance
     */
    private boolean remove(ValueType value, BinaryNode<ValueType> currentNode) {
        BinaryNode<ValueType> parentNode = currentNode;
        if(root == null){
            return false;
        }

        if(currentNode != null){
            int diffrence = value.compareTo(currentNode.value);

            if(diffrence < 0){
                remove(value,currentNode.left);
            }else if(diffrence > 0) {
                remove(value, currentNode.right);
            }else if(currentNode.right != null ){
                currentNode.value = findMin(currentNode.right).value;
                remove(value, currentNode.right);
            }else{
                if(currentNode == root){
                    root = null;
                }else if(currentNode.parent.left == currentNode){
                    currentNode.parent.left = null;
                    currentNode.parent = null;
                }else if (currentNode.parent.right == currentNode){
                    currentNode.parent.right = null;
                    currentNode.parent = null;
                }
            }


            if(getLevelCount(currentNode.right) - getLevelCount(currentNode.left) > 1 || getLevelCount(currentNode.right) - getLevelCount(currentNode.left) < 1){
                balance(currentNode);
            }
        }


        return false;
    }

    /**
     * TODO O( n )
     * Balances the subTree
     *
     * @param subTree SubTree currently being accessed to verify if it respects the AVL balancing rule
     */
    private void balance(BinaryNode<ValueType> subTree) {
        if (getLevelCount(subTree.left) - getLevelCount(subTree.right) > 1) {
            if (getLevelCount(subTree.left.left) < getLevelCount(subTree.left.right))
                doubleRotateOnLeftChild(subTree);
            else
                rotateLeft(subTree);
        } else if (getLevelCount(subTree.right) - getLevelCount(subTree.left) > 1) {
            if (getLevelCount(subTree.right.left) > getLevelCount(subTree.right.right))
                doubleRotateOnRightChild(subTree);
            else
                rotateRight(subTree);
        }
    }

    /**
     * TODO O( 1 )
     * Single rotation to the left child, AVR Algorithm
     *
     * @param node1 Node to become child of its left child
     */
    private void rotateLeft(BinaryNode<ValueType> node1) {
        BinaryNode<ValueType> node = node1.left;

        if(node1.parent != null){
            if(node1.parent.left == node1){
                node.parent = node1.parent;
                node.parent.left = node;
            }else{
                node.parent = node1.parent;
                node1.parent.right = node;
            }
        }else{
            node.parent = null;
            root = node;
        }

        if(node.right != null)
            node.right.parent = node1;
        node1.left = node.right;
        node.right = node1;
        node1.parent = node;
    }

    /**
     * TODO O( 1 )
     * Single rotation to the right, AVR Algorithm
     *
     * @param node1 Node to become child of its right child
     */
    private void rotateRight(BinaryNode<ValueType> node1) {
        BinaryNode<ValueType> node = node1.right;

        if(node1.parent !=null){
            if(node1.parent.left == node1){
                node.parent = node1.parent;
                node.parent.left = node;
            }else{
                node.parent = node1.parent;
                node.parent.right = node;
            }
        }else{
            node.parent = null;
            root = node;
        }

        if(node.left != null)
            node.left.parent = node1;
        node1.right = node.left;
        node.left = node1;
        node1.parent = node;
    }

    /**
     * TODO O( 1 )
     * Double rotation on left child, AVR Algorithm
     *
     * @param node1 Node to become child of the right child of its left child
     */
    private void doubleRotateOnLeftChild(BinaryNode<ValueType> node1) {
        rotateRight(node1.left);
        rotateLeft(node1);
    }

    /**
     * TODO O( 1 )
     * Double rotation on right child, AVR Algorithm
     *
     * @param node1 Node to become child of the left child of its right child
     */
    private void doubleRotateOnRightChild(BinaryNode<ValueType> node1) {
        rotateLeft(node1.right);
        rotateRight(node1);
    }

    /**
     * TODO O( log n )
     * Verifies if the root tree contains value
     *
     * @param value       value to verify
     * @param currentNode Node currently being accessed in this recursive method
     * @return if value already exists in the root tree
     */
    private boolean contains(ValueType value, BinaryNode<ValueType> currentNode) {

        if (currentNode == null) {
            return false;
        } else {
            int diffrence = value.compareTo(currentNode.value);
            if (diffrence == 0) {
                return true;
            } else if (diffrence < 0 && currentNode.left == null) {
                return false;
            } else if (diffrence < 0) {
                return contains(value, currentNode.left);
            } else if (diffrence > 0 && currentNode.right == null) {
                return false;
            } else {
                return contains(value, currentNode.right);
            }
        }
    }

    /**
     * TODO O( n )
     * Returns the number of level contained in subTree including subTree node level
     *
     * @return Number of level contained in subTree including subTree node level
     */
    private int getLevelCount(BinaryNode<ValueType> subTree) {
        int maxLevelRight = 0, maxLevelLeft = 0;

        if (subTree == null) {
            return 0;
        }

        maxLevelLeft = getLevelCount(subTree.left) + 1;
        maxLevelRight = getLevelCount(subTree.right) + 1;

        return Math.max(maxLevelLeft, maxLevelRight);
    }

    /**
     * TODO O( log n )
     * Returns the node which has the minimal value contained in our root tree
     *
     * @return Node which has the minimal value contained in our root tree
     */
    private BinaryNode<ValueType> findMin(BinaryNode<ValueType> currentNode) {
        if (currentNode == null) {
            return null;
        } else if (currentNode.left == null) {
            return currentNode;
        }
        return findMin(currentNode.left);
    }

    /**
     * TODO O( n )
     * Builds items which should contain all values within the root tree in ascending order
     *
     * @param currentNode Node currently being accessed in this recursive method
     * @param items       List being modified to contain all values in the root tree in ascending order
     */
    private void infixOrder(BinaryNode<ValueType> currentNode, List<ValueType> items) {
        if (currentNode != null) {
            infixOrder(currentNode.left, items);
            items.add(currentNode.value);
            infixOrder(currentNode.right, items);
        }
    }

    /**
     * TODO O( n )
     * Builds items which should contain all values within the root tree in level order from top to bottom
     *
     * @param nodesToCheck Queue for non-recursive algorithm
     * @param items        List being modified to contain all values in the root tree in level order from top to bottom
     */
    private void levelOrder(ArrayDeque<BinaryNode<ValueType>> nodesToCheck, List<ValueType> items) {
        while (nodesToCheck.size() != 0) {
            items.add(nodesToCheck.getFirst().value);
            if (nodesToCheck.getFirst().left != null) {
                nodesToCheck.addLast(nodesToCheck.getFirst().left);
            }
            if (nodesToCheck.getFirst().right != null) {
                nodesToCheck.addLast(nodesToCheck.getFirst().right);
            }
            nodesToCheck.removeFirst();
        }

    }

    static class BinaryNode<ValueType> {
        ValueType value;

        BinaryNode<ValueType> parent; // Pointer to the node containing this node

        BinaryNode<ValueType> left = null; // Pointer to the node on the left which should contain a value below this.value
        BinaryNode<ValueType> right = null; // Pointer to the node on the right which should contain a value above this.value

        BinaryNode(ValueType value, BinaryNode<ValueType> parent) {
            this.value = value;
            this.parent = parent;
        }
    }
}