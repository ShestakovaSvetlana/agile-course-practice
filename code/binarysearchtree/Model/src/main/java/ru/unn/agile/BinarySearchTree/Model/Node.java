package ru.unn.agile.BinarySearchTree.Model;

public class Node {
    public int getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public void setKey(final int key) {
        this.key = key;
    }

    public void setLeft(final Node left) {
        this.left = left;
    }

    public void setRight(final Node right) {
        this.right = right;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public Node(final int key) {
        this.key = key;
    }

    public Node(final int key, final Object value) {
        this.key = key;
        this.value = value;
    }

    public void initBy(final Node newNode) {
        this.key = newNode.getKey();
        this.value = newNode.getValue();
        this.left = newNode.getLeft();
        this.right = newNode.getRight();
    }

    private int key;
    private Object value;
    private Node left;
    private Node right;

}
