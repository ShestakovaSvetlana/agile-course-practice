package ru.unn.agile.BinarySearchTree.Model;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.ArrayList;

public class BinarySearchTree {

    public Node getRoot() {
        return this.root;
    }

    public void insert(final Node newNode) {
        this.insert(this.root, newNode);
    }

    public void delete(final int key) {
        if (this.root == null) {
            throw new InvalidParameterException("Can not be deleted because the tree is empty");
            //return;
        }
        Node node = this.find(this.root, key);
        if (node == null) {
            throw new InvalidParameterException("Can not be deleted because the node is null");
        }
        if (node != null) {
            this.deleteNode(node);
        }
    }

    public List<Node> findByValue(final Object value) {
        searchNodes = new ArrayList<Node>();
        recInOrder(this.root, value);
        return searchNodes;
    }

    private void insert(final Node root, final Node newNode) {
        if (root == null) {
            this.root = newNode;
        } else {
            if (root.getKey() == newNode.getKey()) {
                root.setValue(newNode.getValue());
            } else if (root.getKey() > newNode.getKey()) {
                if (root.getLeft() == null) {
                    root.setLeft(newNode);
                } else {
                    insert(root.getLeft(), newNode);
                }
            } else if (root.getKey() < newNode.getKey()) {
                if (root.getRight() == null) {
                    root.setRight(newNode);
                } else {
                    insert(root.getRight(), newNode);
                }
            }
        }
    }

    private void deleteNode(final Node node) {
        if (node.getLeft() == null && node.getRight() == null) {
            deleteChildByKey(this.root, null, node.getKey());
        } else if (node.getLeft() == null) {
            node.initBy(node.getRight());
        } else if (node.getRight() == null) {
            node.initBy(node.getLeft());
        } else {
            if (node.getRight().getLeft() == null) {
                node.getRight().setLeft(node.getLeft());
                node.initBy(node.getRight());
            } else {
                Node minNode = node.getRight().getLeft();
                while (minNode.getLeft() != null) {
                    minNode = minNode.getLeft();
                }
                this.deleteNode(minNode);
                node.setKey(minNode.getKey());
                node.setValue(minNode.getValue());

            }
        }
    }

    private void deleteChildByKey(final Node root, final Node parent, final int key) {
        if (root.getKey() == key) {
            if (root.getKey() < parent.getKey()) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
            return;
        }

        if (root.getKey() < key) {
            deleteChildByKey(root.getRight(), root, key);
        } else {
            deleteChildByKey(root.getLeft(), root, key);
        }
    }

    private Node find(final Node root, final int key) {
        if (root == null) {
            return null;
        }

        if (root.getKey() == key) {
            return root;
        }

        if (root.getKey() < key) {
            return find(root.getRight(), key);
        } else {
            return find(root.getLeft(), key);
        }
    }

    private void recInOrder(final Node root, final Object value) {
        if (root != null) {
            recInOrder(root.getLeft(), value);
            if (root.getValue().equals(value)) {
                searchNodes.add(root);
            }
            recInOrder(root.getRight(), value);
        }
    }

    private Node root;
    private List<Node> searchNodes = null;
}
