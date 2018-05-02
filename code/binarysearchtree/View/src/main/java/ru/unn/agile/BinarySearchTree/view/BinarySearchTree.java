package ru.unn.agile.BinarySearchTree.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.BinarySearchTree.Infrastructure.TxtLogger;
import ru.unn.agile.BinarySearchTree.ViewModel.ViewModel;
import ru.unn.agile.BinarySearchTree.ViewModel.Operation;

public class BinarySearchTree {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtKey;
    @FXML
    private TextField txtValue;
    @FXML
    private ComboBox<Operation> cbOperation;
    @FXML
    private Button btnExec;

    @FXML
    void initialize() {

        viewModel.setLogger(new TxtLogger("./TxtLogger.log"));

        final ChangeListener<Boolean> focusChangedListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        txtKey.textProperty().bindBidirectional(viewModel.keyProperty());
        txtKey.focusedProperty().addListener(focusChangedListener);

        txtValue.textProperty().bindBidirectional(viewModel.valueProperty());
        txtValue.focusedProperty().addListener(focusChangedListener);

        cbOperation.valueProperty().bindBidirectional(viewModel.operationProperty());
        cbOperation.valueProperty().addListener(new ChangeListener<Operation>() {
            @Override
            public void changed(final ObservableValue<? extends Operation> observable,
                                final Operation oldValue,
                                final Operation newValue) {
                viewModel.onOperationChanged(oldValue, newValue);
            }
        });

        btnExec.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.execute();
            }
        });
    }
}
