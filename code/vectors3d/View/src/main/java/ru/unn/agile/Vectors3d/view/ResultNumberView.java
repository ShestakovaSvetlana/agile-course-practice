package ru.unn.agile.Vectors3d.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ru.unn.agile.Vectors3d.viewmodel.ResultNumberViewModel;

@SuppressWarnings("PMD.SingularField")
public class ResultNumberView {

    @FXML
    void initialize() {
    }

    public void setViewModel(final ResultNumberViewModel viewModel) {
        this.viewModel = viewModel;
        resultValue.textProperty().bindBidirectional(this.viewModel.resultProperty());
    }

    @FXML
    private Label resultValue;

    private ResultNumberViewModel viewModel = null;
}
