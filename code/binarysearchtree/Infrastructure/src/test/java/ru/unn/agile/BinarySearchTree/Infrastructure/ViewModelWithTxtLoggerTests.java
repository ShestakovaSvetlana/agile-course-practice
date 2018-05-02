package ru.unn.agile.BinarySearchTree.Infrastructure;

import ru.unn.agile.BinarySearchTree.ViewModel.ViewModel;
import ru.unn.agile.BinarySearchTree.ViewModel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
