package ru.unn.agile.Vectors3d.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import ru.unn.agile.Vectors3d.viewmodel.Vectors3dViewModel;

public class Vectors3dView {

    @FXML
    void initialize() {

        firstVectorController.setViewModel(viewModel.getFirstVectorViewModel());
        secondVectorController.setViewModel(viewModel.getSecondVectorViewModel());

        dotProductButton.setOnAction(event -> {
            viewModel.calculateDotProduct();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ResultNumberView.fxml"));
            AnchorPane content = null;
            try {
                content = loader.load();
                ResultNumberView view = loader.getController();
                view.setViewModel(viewModel.getResultNumberViewModel());
            } catch (IOException e) {
                e.printStackTrace();
            }
            pushResult(content);
        });

        crossProductButton.setOnAction(event -> {
            viewModel.calculateCrossProduct();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ResultVectorView.fxml"));
            AnchorPane content = null;
            try {
                content = loader.load();
                ResultVectorView view = loader.getController();
                view.setViewModel(viewModel.getResultVectorViewModel());
            } catch (IOException e) {
                e.printStackTrace();
            }
            pushResult(content);
        });

        dotProductButton.disableProperty().bind(viewModel.calculationIsNotAvailableProperty());
        crossProductButton.disableProperty().bind(viewModel.calculationIsNotAvailableProperty());
    }

    private void pushResult(final AnchorPane content) {
        TitledPane resultPane = new TitledPane("Result", content);
        resultPane.setCollapsible(false);
        resultAccordion.getPanes().clear();
        resultAccordion.getPanes().add(resultPane);
        resultAccordion.setExpandedPane(resultPane);
    }

    @FXML
    private VectorView firstVectorController;

    @FXML
    private VectorView secondVectorController;

    @FXML
    private Accordion resultAccordion;

    @FXML
    private Button dotProductButton;

    @FXML
    private Button crossProductButton;

    @FXML
    private Vectors3dViewModel viewModel;
}

