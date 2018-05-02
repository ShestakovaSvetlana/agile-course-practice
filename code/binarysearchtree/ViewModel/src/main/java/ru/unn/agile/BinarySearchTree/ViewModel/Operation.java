package ru.unn.agile.BinarySearchTree.ViewModel;

public enum Operation {
    INSERT("Insert"),
    DELETE("Delete"),
    FIND("Find"),
    GET_ROOT("Get root");

    private final String name;
    Operation(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
