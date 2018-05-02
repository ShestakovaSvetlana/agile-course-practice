package ru.unn.agile.Vectors3d.view;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import ru.unn.agile.Vectors3d.viewmodel.VectorViewModel;

@SuppressWarnings("PMD.SingularField")
public class ResultVectorView {

    @FXML
    void initialize() {
    }

    public void setViewModel(final VectorViewModel viewModel) {
        this.viewModel = viewModel;
        xLabel.textProperty().bindBidirectional(this.viewModel.getXProperty());
        yLabel.textProperty().bindBidirectional(this.viewModel.getYProperty());
        zLabel.textProperty().bindBidirectional(this.viewModel.getZProperty());
    }

    @FXML
    private Label xLabel;

    @FXML
    private Label yLabel;

    @FXML
    private Label zLabel;

    private VectorViewModel viewModel = null;
}
