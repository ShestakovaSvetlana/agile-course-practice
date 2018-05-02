package ru.unn.agile.Vectors3d.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.TextFormatter;

import ru.unn.agile.Vectors3d.viewmodel.VectorViewModel;

public class VectorView {

    @FXML
    void initialize() {
        final List<TextField> textFields = new ArrayList<TextField>()  { {
                add(xTextField);
                add(yTextField);
                add(zTextField);
            } };

        Pattern pattern = Pattern.compile(INPUT_REGEX);

        for (TextField textField : textFields) {
            TextFormatter<String> formatter = new TextFormatter<>(change ->
                pattern.matcher(change.getControlNewText()).matches() ? change : null);
            textField.setTextFormatter(formatter);
        }

        normalizeButton.setOnAction(event -> viewModel.normalize());
    }

    public void setViewModel(final VectorViewModel viewModel) {
        this.viewModel = viewModel;
        xTextField.textProperty().bindBidirectional(this.viewModel.getXProperty());
        yTextField.textProperty().bindBidirectional(this.viewModel.getYProperty());
        zTextField.textProperty().bindBidirectional(this.viewModel.getZProperty());

        normalizeButton.disableProperty().bind(this.viewModel.normalizeDisabledProperty());
    }

    private static final String INPUT_REGEX =
            "[-+]?[0-9]{0,8}(\\.[0-9]{0,8})?([eE][-+]?[0-9]{0,2})?";

    @FXML
    private TextField xTextField;

    @FXML
    private TextField yTextField;

    @FXML
    private TextField zTextField;

    @FXML
    private Button normalizeButton;

    private VectorViewModel viewModel = new VectorViewModel();
}
