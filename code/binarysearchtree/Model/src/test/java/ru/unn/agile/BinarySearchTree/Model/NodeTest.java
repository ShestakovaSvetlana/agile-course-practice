package ru.unn.agile.BinarySearchTree.Model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class NodeTest {

    @Test
    public void canCreateNodeByKey() {
        Node node = new Node(1);
        assertNotNull(node);
    }

    @Test
    public void canCreateNodeByZero() {
        Node node = new Node(0);
        assertNotNull(node);
    }

    @Test
    public void canCreateNodeByNegativeNumberKey() {
        Node node = new Node(-1);
        assertNotNull(node);
    }

    @Test public void canCreateNodeByKeyAndByValue() {
        Node node = new Node(1, "test");
        assertNotNull(node);
    }

    @Test
    public void canSetStringValueInNode() {
        Node node = new Node(1, "test");
        assertEquals("test", node.getValue());
    }

    @Test
    public void canSetIntValueInNode() {
        Node node = new Node(1, 1);
        assertEquals(1, node.getValue());
    }

    @Test
    public void canGetKey() {
        Node node = new Node(1);
        assertEquals(1, node.getKey());
    }

    @Test
    public void canSetKey() {
        Node node = new Node(1);
        node.setKey(2);
        assertEquals(2, node.getKey());
    }

    @Test
    public void canGetValue() {
        Node node = new Node(1, "test");
        assertEquals("test", node.getValue());
    }

    @Test
    public void canSetValue() {
        Node node = new Node(1, "test1");
        node.setValue("test2");
        assertEquals("test2", node.getValue());
    }

    @Test
    public void canSetLeftNode() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        node1.setLeft(node2);
        assertEquals(node2, node1.getLeft());
    }

    @Test
    public void canSetRightNode() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        node1.setRight(node2);
        assertEquals(node2, node1.getRight());
    }

    @Test
    public void canInitKeyByOtherNode() {
        Node node1 = new Node(1, "test1");
        Node node2 = new Node(2, "test2");
        node1.initBy(node2);
        assertEquals(node2.getKey(), node1.getKey());
    }

    @Test
    public void canInitValueByOtherNode() {
        Node node1 = new Node(1, "test1");
        Node node2 = new Node(2, "test2");
        node1.initBy(node2);
        assertEquals(node2.getValue(), node1.getValue());
    }

    @Test
    public void canInitLeftNodeByOtherNode() {
        Node node1 = new Node(1, "test1");
        node1.setLeft(new Node(3, "test3"));
        Node node2 = new Node(2, "test2");
        node2.setLeft(new Node(4, "test4"));
        node1.initBy(node2);
        assertEquals(node2.getLeft(), node1.getLeft());
    }

    @Test
    public void canInitRightNodeByOtherNode() {
        Node node1 = new Node(1, "test1");
        node1.setRight(new Node(3, "test3"));
        Node node2 = new Node(2, "test2");
        node2.setRight(new Node(4, "test4"));
        node1.initBy(node2);
        assertEquals(node2.getLeft(), node1.getLeft());
    }
}
