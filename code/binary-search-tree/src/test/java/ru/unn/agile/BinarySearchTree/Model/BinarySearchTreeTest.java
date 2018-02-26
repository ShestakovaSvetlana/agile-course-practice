package ru.unn.agile.BinarySearchTree.Model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

public class BinarySearchTreeTest {
    @Test
    public void canCreateTree() {
        BinarySearchTree tree = new BinarySearchTree();
        assertNotNull(tree);
    }

    @Test
    public void nullGetRootInEmptyTree() {
        BinarySearchTree tree = new BinarySearchTree();
        assertNull(tree.getRoot());
    }

    @Test
    public void canGetRoot() {
        BinarySearchTree tree = new BinarySearchTree();
        Node node = new Node(1, "test");
        tree.insert(node);
        assertEquals(node, tree.getRoot());
    }

    @Test
    public void canGetRootInBigTree() {
        BinarySearchTree tree = createTestTree(false);
        assertEquals(3, tree.getRoot().getKey());
    }

    @Test
    public void canInsertNodeInRightSubTree() {
        BinarySearchTree tree = new BinarySearchTree();
        Node node1 = new Node(1, "test1");
        tree.insert(node1);
        Node node2 = new Node(2, "test2");
        tree.insert(node2);
        assertEquals(node2, tree.getRoot().getRight());
    }

    @Test
    public void canInsertNodeInLeftSubTree() {
        BinarySearchTree tree = new BinarySearchTree();
        Node node1 = new Node(2, "test2");
        tree.insert(node1);
        Node node2 = new Node(1, "test1");
        tree.insert(node2);
        assertEquals(node2, tree.getRoot().getLeft());
    }

    @Test
    public void canInsertNodeInBigTree() {
        BinarySearchTree tree = createTestTree(false);
        Node node = new Node(7, "test7");
        tree.insert(node);
        assertEquals(node, tree.getRoot().getRight().getRight());
    }

    @Test
    public void canDeleteNodeList() {
        BinarySearchTree tree = createTestTree(false);
        tree.delete(4);
        assertNull(tree.getRoot().getRight().getLeft().getLeft());
    }

    @Test
    public void canDeleteNodeWithOneChild() {
        BinarySearchTree tree = createTestTree(false);
        tree.delete(5);
        assertEquals(4, tree.getRoot().getRight().getLeft().getKey());
    }

    @Test
    public void canDeleteNodeWithTwoChilds() {
        BinarySearchTree tree = createTestTree(false);
        tree.delete(1);
        assertEquals(2, tree.getRoot().getLeft().getKey());
    }

    @Test
    public void canFindByValue() {
        BinarySearchTree tree = new BinarySearchTree();
        Node node1 = new Node(1, "test1");
        tree.insert(node1);
        List<Node> nodes = tree.findByValue("test1");
        assertEquals(node1, nodes.get(0));
    }

    @Test
    public void canFindByValueInBigTree() {
        BinarySearchTree tree = createTestTree(false);
        List<Node> nodes = tree.findByValue("test5");
        assertEquals(5, nodes.get(0).getKey());
    }

    @Test
    public void canFindByValueMoreThanOneNodes() {
        BinarySearchTree tree = createTestTree(true);
        List<Node> nodes = tree.findByValue("test");
        assertEquals(7, nodes.size());
    }

    private BinarySearchTree createTestTree(final boolean isSameValue) {
        BinarySearchTree tree = new BinarySearchTree();
        int[] treeKeys = {3, 1, 2, 0, 6, 5, 4};
        for (int i = 0; i < treeKeys.length; i++) {
            String value = isSameValue ? "test" : "test" + String.valueOf(treeKeys[i]);
            Node temp = new Node(treeKeys[i], value);
            tree.insert(temp);
        }
        return tree;
    }
}
